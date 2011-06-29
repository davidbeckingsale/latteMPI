package uk.ac.warwick.java.mpi.op;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;

/**
 * Class containing the Min operation.
 *
 * <p>Finds the minimum value at each position in the array. If a[] =
 * {5,3} and b[] = {1,4}, then the result of min on these arrays would
 * be {5,4}.
 *
 * Created: 01/12/11
 *
 * @author David Beckingsale
 */
public class Min implements MpiOp {

  /**
   * Finds the minimum value on an element by element basis in the arrays in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays from which to
   * find the minimum values.
   * @return An array containing the minimum value at each position.
   */
  public int[] run(ArrayList<int[]> arrays) {
    int length = arrays.get(0).length;

    int[] result = new int[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Integer.MAX_VALUE;
    }

    for (int[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }

  /**
   * Finds the minimum value on an element by element basis in the arrays in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays from which to
   * find the minimum values.
   * @return An array containing the minimum value at each position.
   */
  public long[] run(ArrayList<long[]> arrays) {
    int length = arrays.get(0).length;

    long[] result = new long[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Long.MAX_VALUE;
    }

    for (long[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }

  /**
   * Finds the minimum value on an element by element basis in the arrays in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays from which to
   * find the minimum values.
   * @return An array containing the minimum value at each position.
   */
  public float[] run(ArrayList<float[]> arrays) {
    int length = arrays.get(0).length;

    float[] result = new float[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Float.POSITIVE_INFINITY;
    }

    for (float[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }

  /**
   * Finds the minimum value on an element by element basis in the arrays in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays from which to
   * find the minimum values.
   * @return An array containing the minimum value at each position.
   */
  public double[] run(ArrayList<double[]> arrays) {
    int length = arrays.get(0).length;

    double[] result = new double[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Double.POSITIVE_INFINITY;
    }

    for (double[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }
} // Min
