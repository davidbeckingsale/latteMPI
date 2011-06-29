package uk.ac.warwick.java.mpi;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Unit tests for Mpi class
 *
 * @author David Beckingsale
 * @version 1
 * @since 06/10/2010
 */
public class MpiTest {

   /**
    * Checks that <code>Initialized()</code> returns false at the
    * beginning of execution.
    */
   @Test
   public void testInitilializedFalse() {
     assertFalse( Mpi.Initialized() );
   }

   /**
    * Checks <code>Initialized()</code> returns true after
    * <code>Init()</code> has been called.
    */
   @Test public void testInitialized() {
     String[] args = {"hello"};
     args = Mpi.Init( args );
     assertTrue( Mpi.Initialized() );
   }

  @Test public void testFinalized() {
    Mpi.Finalize();
    assertEquals(Mpi.COMM_WORLD, null);
  }
  /**
   * Code to test <code>Wtick</code>
   *
   * <p> That is, the resolution of the clock
   */
  @Test public void testWtick() {
    Mpi.Wtick();
  }

   /**
    * Checks <code>Wtime()</code> works correctly.
    *
    * <p>This is done by storing the start time, sleeping the thread
    * for 100 milliseconds and then checking that the time difference
    * between the two <code>Wtime()</code> calls indicates that approx
    * 100ms has passed.
    */
   @Test public void testWtime()
     throws InterruptedException {
     double startTime = Mpi.Wtime();

     Thread.sleep(100);
     double takenTime = ( Mpi.Wtime() - startTime );

     assertEquals( "Time", 0.100, takenTime, 0.01 );
     }
 }
