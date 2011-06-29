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
public class ProdTest  {
  /**
   * Tests the prod of <code>int</code>s.
   */
  @Test public void testInt() {
    MpiOp prod = new Prod();

    ArrayList<int[]> list = new ArrayList<int[]>();
    list.add(new int[] {1,2,3,5});
    list.add(new int[] {5,23,56,-9});

    int[] result = prod.run(list);

    assertArrayEquals(new int[] {5,46,168,-45}, result);
  }

  /**
   * Tests the prod of <code>long</code>s.
   */
  @Test public void testLong() {
    MpiOp prod = new Prod();

    ArrayList<long[]> list = new ArrayList<long[]>();
    list.add(new long[] {10L, 5000L, 60000L});
    list.add(new long[] {1000L, 23L, 3L});

    long[] result = prod.run(list);

    assertArrayEquals(new long[] {10000L, 115000L, 180000L}, result);
  }

  /**
   * Tests the summing of <code>float</code>s.
   */
  @Test public void testFloat() {
    MpiOp prod = new Prod();

    ArrayList<float[]> list = new ArrayList<float[]>();
    list.add(new float[] {3.14f, -0.4545f, 1.1f});

    float[] result = prod.run(list);

    assertArrayEquals(new float[] {3.14f, -0.4545f, 1.1f}, result, 0.1f);
  }

  /**
   * Tests the summing of <code>double</code>s.
   */
  @Test public void testDouble() {
    MpiOp prod = new Prod();

    ArrayList<double[]> list = new ArrayList<double[]>();
    list.add(new double[] {3.14, -0.4545, 1.1, 0.0003});
    list.add(new double[] {1.23, 1.1, 3.2, 9.8});

    double[] result = prod.run(list);

    assertArrayEquals(new double[] {3.8622, -0.49995, 3.52, 0.000294}, result, 0.1);
  }

}
