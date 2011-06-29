package uk.ac.warwick.java.mpi.op;



import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;

/**
 * Tests the Min class.
 *
 * Created: 11/1/11
 *
 * @author David Beckingsale
 */
public class MinTest  {
  /**
   * Tests the max of <code>int</code>s.
   */
  @Test public void testInt() {
    MpiOp min = new Min();

    ArrayList<int[]> list = new ArrayList<int[]>();
    list.add(new int[] {1,2,3,500});
    list.add(new int[] {500,23000,56,-90});

    int[] result = min.run(list);

    ArrayList<int[]> negList = new ArrayList<int[]>();
    negList.add(new int[] {-100, -1, -54000});
    negList.add(new int[] {-23,-50, -100});

    int[] negresult = min.run(negList);

    assertArrayEquals(new int[] {1,2,3,-90},result);
    assertArrayEquals(new int[] {-100, -50, -54000}, negresult);
  }

  /**
   * Tests the min of <code>long</code>s.
   */
  @Test public void testLong() {
    MpiOp min = new Min();

    ArrayList<long[]> list = new ArrayList<long[]>();
    list.add(new long[] {10L, 500000L});
    list.add(new long[] {1000L, Long.MIN_VALUE});

    long[] result = min.run(list);

    assertArrayEquals(new long[] {10L, Long.MIN_VALUE}, result);
  }

  /**
   * Tests the summing of <code>float</code>s.
   */
  @Test public void testFloat() {
    MpiOp min = new Min();

    ArrayList<float[]> list = new ArrayList<float[]>();
    list.add(new float[] {3.14f, -0.4545f, 1.1f});

    float[] result = min.run(list);

    assertArrayEquals(new float[] {3.14f, -0.4545f, 1.1f}, result, 0.1f);
  }

  /**
   * Tests the summing of <code>double</code>s.
   */
  @Test public void testDouble() {
    MpiOp min = new Min();

    ArrayList<double[]> list = new ArrayList<double[]>();
    list.add(new double[] {3.14, -0.4545, 1.1});

    double[] result = min.run(list);

    assertArrayEquals(new double[] {3.14, -0.4545, 1.1}, result, 0.1);
  }

}
