package uk.ac.warwick.java.mpi;



import java.io.File;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import uk.ac.warwick.java.mpi.op.*;


/**
 * Main class providing all MPI operations.
 *
 * @author David Beckingsale
 * @version 1
 * @since 06/10/2010
 */
public class Mpi {

  /** Global debugging constant. */
  public static final boolean DEBUG_MODE = false;
  public static final boolean TREE = true;

  /** MPI constant COMM_WORLD. */
  public static Comm COMM_WORLD;
  /** MPI constant COMM_NULL. */
  public static final Comm COMM_NULL = new Comm(0);
  /** MPI constant ANY_TAG */
  public static final int ANY_TAG = -1;
  /** MPI constant ANY_SOURCE */
  public static final int ANY_SOURCE = -2;
  // Return values
  public static final int SUCCESS = 1;

  /** Holds the SUM operator */
  public static final Sum SUM = new Sum();
  /** Holds the PROD operator */
  public static final Prod PROD = new Prod();
  /** Holds the MAX operator */
  public static final Max MAX = new Max();
  /** Holds the MIN operator. */
  public static final Min MIN = new Min();
  /** Holds the MINLOC operator */
  public static final MinLoc MINLOC = new MinLoc();
  /** Holds the MAXLOC operator */
  public static final MaxLoc MAXLOC = new MaxLoc();

  // Private constants
  /** True if the Init method has been called. */
  private static boolean initialized_ = false;
  /** Holds the <code>nanoTime()</code> value from when the Init
   * method was called. */
  private static long initTime_;
  /** Divide a nano second time by this number to convert to seconds */
  private static final double TO_SECONDS = 1000000000;


  private Mpi() {
    throw new AssertionError();
  }

  /**
   * Initialise the MPI environment.
   *
   * @param args The array of program arguments
   */
  public static String[] Init( String[] args ) {
    if (DEBUG_MODE) {
      System.err.println("Mpi: \tInit called");
    }

    initTime_ = System.nanoTime();

    initialized_ = true;

    return args;

  } //Init


  /**
   * Finalises the MPI environment. This should be the LAST statement in
   * your program!
   */
  public static void Finalize() {

    if ( DEBUG_MODE ) {
      System.err.println("Mpi: \tFinalize called");
    }

    // null comm_world
    COMM_WORLD = null;

  } // Finalize



  /**
   * Checks whether <code>Init()</code> has been called.
   *
   * @return A boolean that is true if <code>Init()</code> has been called, false
   * otherwise.
   */
  public static boolean Initialized() {
    return initialized_;
  }

  /**
   * Checks the resolution of the <code>Wtime</code> method.
   * @return The resolution of <code>Wtime</code> in seconds.
   */
  public static double Wtick(){
    return (double)( Math.abs(System.nanoTime() - System.nanoTime()) ) / TO_SECONDS;
  }

  /**
   * Returns the wall clock time since some arbitray point in the past.
   *
   * <p> Implemented using Javas nanoTime method.
   *
   * @return The wall-clock time since the arbitrary point in seconds.
   */
  public static double Wtime(){
    return ((double)( System.nanoTime() ) / TO_SECONDS);
  }

  /**
   * Utility method to return the name of this processor, returns the
   * hostname of the local machine.
   *
   * @returns The name of the processor.
   */
  public static String getProcessorName(){
    try {
      return java.net.InetAddress.getLocalHost().getHostName().toString();
    } catch (Exception e) {
      System.err.println("Mpi: \tCouldn't get local hostname");
      e.printStackTrace();
    }

    return null;
  }
}
