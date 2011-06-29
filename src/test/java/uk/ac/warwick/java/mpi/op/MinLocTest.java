package uk.ac.warwick.java.mpi.op;



import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;



/**
 * Tests the MinLoc class.
 *
 * Created: 14/1/11
 *
 * @author David Beckingsale
 */
public class MinLocTest  {
  /**
   * Tests the max of <code>int</code>s.
   */
  @Test public void testInt() {
    MinLoc minloc = new MinLoc();

    ArrayList<int[]> list = new ArrayList<int[]>();
    list.add(new int[] { 1 , 2, 8, 200030 });
    list.add(new int[] {1,5,4,-8});

    int[] result = minloc.run(list);

    assertArrayEquals(new int[] {1, 2, 4,-8}, result);
    assertArrayEquals(new int[] {0,0,1,1}, minloc.getLocArray());
  }

  /**
   * Tests the max of <code>long</code>s.
   */
  @Test public void testLong() {
    MinLoc minloc = new MinLoc();

    ArrayList<long[]> list = new ArrayList<long[]>();
    list.add(new long[] { 100000L , 232213213213L, 8L, 200030L });
    list.add(new long[] { 1000L,1L, 23252628L, 21L});

    long[] result = minloc.run(list);

    assertArrayEquals(new long[] {1000L, 1L, 8L, 21L}, result);
    assertArrayEquals(new int[] {1,1,0,1}, minloc.getLocArray());
  }

  /**
   * Tests the summing of <code>float</code>s.
   */
  @Test public void testFloat() {
    MinLoc minloc = new MinLoc();

    ArrayList<float[]> list = new ArrayList<float[]>();
    list.add(new float[] { 1.0f, 20.7f, 200.8f, 200.8989f });
    list.add(new float[] { 1.1f, 5.8f, 4.9f, 6.2f});

    float[] result = minloc.run(list);

    assertArrayEquals(new float[] {1.0f, 5.8f, 4.9f, 6.2f}, result, 0.001f);
    assertArrayEquals(new int[] {0,1,1,1}, minloc.getLocArray());
  }

  /**
   * Tests the summing of <code>double</code>s.
   */
  @Test public void testDouble() {
    MinLoc minloc = new MinLoc();

    ArrayList<double[]> list = new ArrayList<double[]>();
    list.add(new double[] { 1.29292 , 20.2, 8.112, 20.6 });
    list.add(new double[] {1.0001, 5.1, 4.222 ,-8.49494});

    double[] result = minloc.run(list);

    assertArrayEquals(new double[] {1.0001, 5.1, 4.222, -8.49494}, result, 0.001);
    assertArrayEquals(new int[] {1,1,1,1}, minloc.getLocArray());
  }

}
