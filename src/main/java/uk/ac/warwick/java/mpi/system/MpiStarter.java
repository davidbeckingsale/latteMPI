package uk.ac.warwick.java.mpi.system;



import uk.ac.warwick.java.mpi.Mpi;
import uk.ac.warwick.java.mpi.system.util.Arguments;


import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Scanner;

import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;



/**
 * First class to be run, starts all the Mpi jobs on the remote
 * processes.
 *
 * @author David Beckingsale
 * @version 1
 * @since 2/2/2011
 */
public class MpiStarter {

  protected static String machinefile_ = "null";
  protected static ArrayList<String> hostList_;
  protected static int n_;
  protected static String class_;
  protected static String[] args_;

  private static ArrayList<MpiProcessSshExec> remoteThreads_;

  /**
   * Starts mpi jobs with n processes. Host are allocated in the order
   * they are read from the file, if n is greater than the number of
   * hosts then a round-robin alocation will be used. In some systems
   * (e.g. PBS) the content of the host file will mean a node will
   * become packed before the other nodes are used.
   *
   * @param args The array of command line arguments.
   */
  public static void main( String[] args ) {

    if( DEBUG_MODE ) {
      System.err.print( "MpiStarter: \tThe arguments are: " );
      for ( int i = 0; i < args.length; i++ ) {
        System.err.print( i + ": " + args[i] + ", " );
      }
      System.err.println();
    }

    // Read in the arguments with the parser defined by the arguments
    // class
    Arguments argumentsParser = new Arguments(args);

    // Strip out all the relevant arguments
    // These are:
    // machinefile - The file containing the host names
    // n - The number of processes desired
    // class - The java class to be loaded
    //
    if ( DEBUG_MODE ) {
      System.err.println( "MpiStarter: \tAssigning machinefile" );
    }
    machinefile_ = argumentsParser.getHostfile().getPath();

    if ( DEBUG_MODE ) {
      System.err.println( "MpiStarter: \tAssigning n" );
    }
    n_ = argumentsParser.getN();

    if ( DEBUG_MODE ) {
      System.err.println( "MpiStarter: \tAssigning class" );
    }
    class_ = argumentsParser.getClassname();

    if (DEBUG_MODE) {
      System.err.println("MpiStarter; \tAssigning user args");
    }
    args_ = argumentsParser.getArgs();

    // Debug block to check the arguments
    if ( DEBUG_MODE ) {
      System.err.println( "MpiStarter: \tArguments removed are: " );
      System.err.println( "\tn_ = " + n_ );
      System.err.println( "\tclass_ = " + class_ );
      System.err.println( "\tmachinefile_ = " + machinefile_ );
    }

    // If there is no machine file then all processes will be local, otherwise load the hosts from the file
    if (machinefile_.equals( "temp" )) {
      // Create n local processes
      hostList_ = new ArrayList<String>();
      hostList_.add("localhost");
    }
    else {
      if( DEBUG_MODE ) {
        System.err.println( "MpiStarter: \tReading from hostfile: " + machinefile_ );
      }

      hostList_ = new ArrayList<String>();

      // Read the hosts from the file
      try {
        Scanner scanner = new Scanner( new FileInputStream(machinefile_) );

        while ( scanner.hasNextLine() ){
          hostList_.add( scanner.nextLine() );
        }

        scanner.close();
      }
      catch ( java.io.FileNotFoundException fileNotFoundException ) {
        System.err.println("MpiStarter: \tCannot find file: " + machinefile_);
      }
      finally {
      }

      if( DEBUG_MODE ) {
        System.err.println( "MpiStarter: \tThe hosts read from file are: " );
        for (String host : hostList_) {
          System.err.println( "\t" + host );
        }
      }
    }

    remoteThreads_ = new ArrayList<MpiProcessSshExec>();

    // Hosts are now in hostList_
    // If n_ is greater than the number of hosts then allocate
    // processes in a round robin fashion, otherwise the first n_ hosts will be used
    for (int i = 0; i < n_; i++) {
      String host = hostList_.get(i % hostList_.size());
      remoteThreads_.add(new MpiProcessSshExec(i, n_, class_, host, machinefile_, args_, argumentsParser.getSshId()));
      (new Thread (remoteThreads_.get(i))).start();
    }

    // Add a hook to kill all remote threads when the local jvm is closed
    Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
          System.out.println("Stopping threads");
          for (MpiProcessSshExec t : remoteThreads_) {
            t.stopRemoteInstance();
          }
        }
      });

  } // Main

} // MpiStarter
