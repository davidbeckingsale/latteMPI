package uk.ac.warwick.java.mpi.system;



import java.lang.reflect.InvocationTargetException;

import uk.ac.warwick.java.mpi.Mpi;
import uk.ac.warwick.java.mpi.system.util.Arguments;

import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;



/**
 * The class that is executed on remote machines that will set-up the
 * MPI environment and then load the main method of the user program.
 *
 * @author David Beckingsale
 * @version 1
 * @since 24/10/2010
 */
public class MpiRemoteProcess extends MpiProcess {

  public  MpiRemoteProcess(int rank, int n,  String className, String hostName, String hostFile, String[] args) {
    super(rank, n, className, hostName, hostFile, args);
  }

  /**
   * Method initialises the mpi environment and responds to the
   * MpiStarter process, signalling either success or failure in
   * setting up the runtime environment
   *
   * @param args The array of arguments passed from the command line.
   */
  public static void main(String[] args) {

    Arguments argumentsParser = new Arguments(args);

    // Create a new RemoteProcess, this is so we can access non-static
    // fields from this static main method
    MpiRemoteProcess thisProcess = new MpiRemoteProcess(argumentsParser.getRank(),
                                                        argumentsParser.getN(),
                                                        argumentsParser.getClassname(),
                                                        argumentsParser.getHostname(),
                                                        argumentsParser.getHostfile().getPath(),
                                                        argumentsParser.getArgs());


    // Create the Group and Comm that will be assigned to COMM_WORLD
    thisProcess.createCommWorld();

    try {

      // Load the users class and run the main method
      thisProcess.loadUserClass();

    } catch (InvocationTargetException invocationTargetException) {

      Throwable cause = invocationTargetException.getCause();
      System.err.println("MpiRemoteProcess: \tFailing invocation, params are: " + argumentsParser.getRank()
                         + argumentsParser.getN() + argumentsParser.getClassname());
      System.err.format("MpiRemoteProcess: \tinvocation of %s failed: %s%n",
                         "main", cause.getMessage());
      invocationTargetException.printStackTrace();

    } catch (ClassNotFoundException classNotFoundException) {

      System.err.println("MpiRemoteProcess: \tCannot find class: " + thisProcess.className_);

    } catch (IllegalAccessException illegalAccessException) {

      System.err.println("MpiRemoteProcess: \tCannot access this class: " + thisProcess.className_);

    } catch (NoSuchMethodException noSuchMethodException) {

      System.err.println("MpiRemoteProcess: \tNo such method: main");

    }

    System.exit(0);
  }
}
