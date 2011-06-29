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
public class MaxTest  {
  /**
   * Tests the max of <code>int</code>s.
   */
  @Test public void testInt() {
    MpiOp max = new Max();

    ArrayList<int[]> list = new ArrayList<int[]>();
    list.add(new int[] {1,2,3,500});
    list.add(new int[] {500,23000,56,-90});

    int[] result = max.run(list);

    ArrayList<int[]> negList = new ArrayList<int[]>();
    negList.add(new int[] {-100, -1, -54000});
    negList.add(new int[] {-23,-50, -100});

    int[] negresult = max.run(negList);

    assertArrayEquals(new int[] {500, 23000,56,500}, result);
    assertArrayEquals(new int[] {-23,-1,-100}, negresult);;
  }

  /**
   * Tests the max of <code>long</code>s.
   */
  @Test public void testLong() {
    MpiOp max = new Max();

    ArrayList<long[]> list = new ArrayList<long[]>();
    list.add(new long[] {10L, 500000L});
    list.add(new long[] {1000L, Long.MAX_VALUE});

    long[] result = max.run(list);

    assertArrayEquals(new long[] {1000L, Long.MAX_VALUE}, result);
  }

  /**
   * Tests the summing of <code>float</code>s.
   */
  @Test public void testFloat() {
    MpiOp max = new Max();

    ArrayList<float[]> list = new ArrayList<float[]>();
    list.add(new float[] {3.14f, -0.4545f, 1.1f});

    float[] result = max.run(list);

    assertArrayEquals("Checking float arrays", new float[] {3.14f,-0.4545f, 1.1f}, result, 0.1f);
  }

  /**
   * Tests the summing of <code>double</code>s.
   */
  @Test public void testDouble() {
    MpiOp max = new Max();

    ArrayList<double[]> list = new ArrayList<double[]>();
    list.add(new double[] {3.14, -0.4545, 1.1});
    list.add(new double[] {54.67, -10.2, 0.2333});

    double[] result = max.run(list);

    assertArrayEquals("Checking double arrays", new double[] {54.67, -0.4545, 1.1}, result, 0.1);
  }

}
