package uk.ac.warwick.java.mpi.op;



import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;

/**
 * Tests the Sum class.
 *
 * Created: 11/1/11
 *
 * @author David Beckingsale
 */
public class SumTest  {
  /**
   * Tests the summing of <code>int</code>s.
   */
  @Test public void testInt() {
    MpiOp sum = new Sum();

    ArrayList<int[]> list = new ArrayList<int[]>();
    list.add(new int[] {1,1,1,1,1});
    list.add(new int[] {1,1,1,1,1});

    int[] result = sum.run(list);

    assertArrayEquals(new int[] {2,2,2,2,2}, result);
  }

  /**
   * Tests the summing of <code>long</code>s.
   */
  @Test public void testLong() {
    MpiOp sum = new Sum();

    ArrayList<long[]> list = new ArrayList<long[]>();
    list.add(new long[] {10000L, 10000L, 10000L});
    list.add(new long[] {10000L, 10000L, 20000L});

    long[] result = sum.run(list);

    assertArrayEquals(new long[] {20000L, 20000L, 30000L}, result);
  }

  /**
   * Tests the summing of <code>float</code>s.
   */
  @Test public void testFloat() {
    MpiOp sum = new Sum();

    ArrayList<float[]> list = new ArrayList<float[]>();
    list.add(new float[] {1.1f, 1.1f, 1.1f, 1.1f, 1.1f});
    list.add(new float[] {1.1f, 1.1f, 1.1f, 1.1f, 1.1f});

    float[] result = sum.run(list);

    assertArrayEquals(new float[] {2.2f,2.2f,2.2f,2.2f,2.2f}, result, 0.1f);
  }

  /**
   * Tests the summing of <code>double</code>s.
   */
  @Test public void testDouble() {
    MpiOp sum = new Sum();

    ArrayList<double[]> list = new ArrayList<double[]>();
    list.add(new double[] {1.1, 1.1, 1.1, 1.1, 1.1});
    list.add(new double[] {1.1, 1.1, 1.1, 0.445, 0.2});

    double[] result = sum.run(list);

    assertArrayEquals(new double[] {2.2,2.2,2.2, 1.545, 1.3}, result, 0.1);
  }

  @Test public void testNegSum() {
    MpiOp sum = new Sum();

    ArrayList<int[]> list = new ArrayList<int[]>();
    list.add(new int[] {-1,-1,-1,-1});

    int result[] = sum.run(list);

    assertArrayEquals(new int[] {-1,-1,-1,-1}, result);
  }
}
