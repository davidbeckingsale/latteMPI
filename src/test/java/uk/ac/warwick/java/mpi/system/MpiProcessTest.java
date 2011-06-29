package uk.ac.warwick.java.mpi.system;

import org.junit.Test;
import static org.junit.Assert.*;

import uk.ac.warwick.java.mpi.Mpi;
import uk.ac.warwick.java.mpi.Comm;

/**
 *
 *
 *
 * @author David Beckingsale
 * @version 1
 * @since 24/10/2010
 */
public class MpiProcessTest  {
  /**
   * Creates a new MpiProcess and checks that the parameters get
   * correctly initialised during construction
   */
  @Test public void testConstructor() {
    String[] args = { "one", "two" };

    MpiProcess process = new MpiProcess(0,1,"classname","hostname","hostfile", args);

    assertEquals(process.rank_, 0);
    assertEquals(process.n_, 1);
    assertTrue(java.util.Arrays.equals(process.args_, args));
    assertEquals(process.className_,"classname");
    assertEquals(process.hostName_, "hostname");
    assertEquals(process.hostFile_, "hostfile");
  }


    @Test public void testBadConstructor() {
    String[] args = { "one", "two" };

    MpiProcess process = new MpiProcess(0,1,"classname","hostname","hostfile", args);

    args = new String[1];
    args[0] = "three";

    assertFalse(process.rank_ == -1);
    assertFalse(process.n_ == 4);
    assertFalse(process.args_ ==  args);
    assertFalse(process.className_ == "class");
    assertFalse(process.hostName_ == "hostnam");
    assertFalse(process.hostFile_ ==  "hostfil");
  }

  /**
   * Need to sort out the location of the hostlist.text file
   */
  public void CreateCommWorld() {
    String[] args = { "one", "two" };

    MpiProcess process = new MpiProcess(0,3,"classname","hostname","hostlist.text", args);

    process.createCommWorld();

    /** Tests rank is correctly set in the COMM_WORLD group */
    assertEquals(Mpi.COMM_WORLD.Rank(), 0);
  }
} // MpiProcessTest
