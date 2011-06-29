package uk.ac.warwick.java.mpi.system;



import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import uk.ac.warwick.java.mpi.Comm;
import uk.ac.warwick.java.mpi.Group;
import uk.ac.warwick.java.mpi.Mpi;

import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;



/**
 * Base class for a process.
 *
 *
 * @author David Beckingsale
 * @version 1
 * @since 15/10/2010
 */
public class MpiProcess {
  /** Process rank. */
  protected int rank_;
  /** Class name of user program. */
  protected String className_;
  /** Hostname of this machine. */
  protected String hostName_;
  /** Location and name of file containing other hosts, e.g
   * $PBS_NODEFILE. */
  protected String hostFile_;
  /** Arguments for the user program. */
  protected String[] args_;
  /** The total number of processes */
  protected int n_;


  /**
   * Constructor to initialise all the fields.
   *
   * @param rank The rank of the process.
   * @param className The name of the user program to be run.
   * @param hostName The hostname of the machine.
   * @param hostFile The location of the file containing all hosts.
   * @param args The array of arguments to be passed to the user program.
   */
  protected MpiProcess(int rank, int n,  String className, String hostName, String hostFile, String[] args) {
    rank_ = rank;
    className_ = className;
    args_ = args;
    hostName_ = hostName;
    hostFile_ = hostFile;
    n_ = n;
  }

  /**
   * Loads the main method of the user program, which is stored in <code>className_</code>.
   */
  protected void loadUserClass() throws ClassNotFoundException, NoSuchMethodException,
                                     IllegalAccessException, InvocationTargetException {

    Class<?> mpiClass = Class.forName(className_);


    Class[] argTypes = new Class[] { String[].class };
    Method main = mpiClass.getDeclaredMethod("main", argTypes);
    String[] mainArgs = Arrays.copyOfRange(args_, 1, args_.length);

    if (DEBUG_MODE) {
      System.out.format("MpiProcess: invoking %s.main()%n\n", mpiClass.getName());
    }

    main.invoke(null, (Object)mainArgs);

  }

  /**
   * Creates the {@link uk.ac.warwick.java.mpi.Comm} object to be assigned to COMM_WORLD.
   */
  protected void createCommWorld() {

    if(DEBUG_MODE) {
      System.err.println("MpiProcess: \tEntered createCommWorld()");
      System.err.println("MpiProcess: \tReading from hostfile: " + hostFile_);
    }

    ArrayList<String> hostList_ = new ArrayList<String>();

    // Read the hosts from the file
    try {
      Scanner scanner = new Scanner( new FileInputStream(hostFile_) );

      while ( scanner.hasNextLine() ){
        hostList_.add( scanner.nextLine() );
      }

      scanner.close();
    }
    catch ( java.io.FileNotFoundException fileNotFoundException ) {
      System.err.println("MpiProcess: \tCannot find file: " + hostFile_);
      System.exit(1);
    }

    if (DEBUG_MODE) {
      System.err.println("MpiProcess: \tHosts read from file");
    }

    ArrayList<Processor> processor_group = new ArrayList<Processor>(n_);
    Processor localProcessor;

    if (DEBUG_MODE) {
      System.err.println("MpiProcess: \tCreating the group for COMM_WORLD");
    }

    for (int i = 1; i <= n_; i++) {
      // Assign correct port number
      int port;
      if (i <= hostList_.size()) {
        port = 4444;
      } else {
        port = 4444 + (i - hostList_.size());
      }

      if (DEBUG_MODE) {
        System.err.println("MpiProcess: \tPort = " + port);
      }
      if (DEBUG_MODE) {
        System.err.println("MpiProcess: \tCreating processor with host " + hostList_.get((i-1) % hostList_.size()) + " and port: " + port);
      }
      processor_group.add(new Processor(hostList_.get((i-1) % hostList_.size()), port));
    }

    localProcessor = processor_group.get(rank_);

    if (DEBUG_MODE) {
      System.err.println("MpiProcess: \tI am rank " + rank_ + " on port " + localProcessor.getPort());
    }

    localProcessor.setNetDevice(new SimpleNetDevice(localProcessor.getPort()));

    Group tempCommWorldGroup = new Group(processor_group, localProcessor);

    if (DEBUG_MODE) {
      System.err.println("MpiProcess: \tAssigning Processor group to COMM_WORLD");
    }

    Mpi.COMM_WORLD = new Comm(tempCommWorldGroup);
  }
}
