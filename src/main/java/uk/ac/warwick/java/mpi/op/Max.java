package uk.ac.warwick.java.mpi.op;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;

/**
 * Finds the maximum value on an element by element basis
 *
 * Created: 01/12/11
 *
 * @author David Beckingsale
 */
public class Max implements MpiOp {

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of integer arrays from which to find the maximum values.
   * @return An <code>int</code> array containing the maximum element in each position.
   */
  public int[] run(ArrayList<int[]> arrays) {
    int length = arrays.get(0).length;

    int[] result = new int[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Integer.MIN_VALUE;
    }

    for (int[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of long arrays from which to find the maximum values.
   * @return A <code>long</code> array containing the maximum element at each position.
   */
  public long[] run(ArrayList<long[]> arrays) {
    int length = arrays.get(0).length;

    long[] result = new long[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Long.MIN_VALUE;
    }

    for (long[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of float arrays from which to find the maximum values.
   * @return A <code>float</code> array containing the maximum element at each position.
   */
  public float[] run(ArrayList<float[]> arrays) {
    int length = arrays.get(0).length;

    float[] result = new float[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Float.NEGATIVE_INFINITY;
    }

    for (float[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of double arrays from which to find the maximum values.
   * @return A <code>double</code> array containing the maximum element at each position.
   */
  public double[] run(ArrayList<double[]> arrays) {
    int length = arrays.get(0).length;

    double[] result = new double[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Double.NEGATIVE_INFINITY;
    }

    for (double[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
        }
      }
    }

    return result;
  }
} // Max
