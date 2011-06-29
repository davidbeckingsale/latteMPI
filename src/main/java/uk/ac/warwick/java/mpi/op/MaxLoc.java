package uk.ac.warwick.java.mpi.op;

import uk.ac.warwick.java.mpi.MpiOp;

import java.util.ArrayList;

/**
 * Class containing the maxloc operation as defined in the MPI
 * standard. The <code>run()</code> works in the same way as the max
 * operation, but will update the location array to contain the
 * location of the maximum.
 *
 * @author David Beckingsale
 * @version 1
 * @since 2/2/2011
 */
public class MaxLoc implements MpiOp {
  /** Stores the location of max after run() has been called */
  private static int[] location;

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of integer arrays from which to find the maximum value.
   * @return An <code>int</code> array containing one element, the maximum value.
   */
  public int[] run(ArrayList<int[]> arrays) {
    int length = arrays.get(0).length;
    location = new int[length];

    int[] result = new int[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Integer.MIN_VALUE;
    }

    for (int[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
        }
      }
    }

    return result;
  }

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of long arrays from which to find the maximum value.
   * @return An <code>long</code> array containing one element, the maximum value.
   */
  public long[] run(ArrayList<long[]> arrays) {
    int length = arrays.get(0).length;
    location = new int[length];

    long[] result = new long[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Long.MIN_VALUE;
    }

    for (long[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
        }
      }
    }

    return result;
  }

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of float arrays from which to find the maximum value.
   * @return An <code>float</code> array containing one element, the maximum value.
   */
  public float[] run(ArrayList<float[]> arrays) {
    int length = arrays.get(0).length;
    location = new int[length];

    float[] result = new float[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Float.NEGATIVE_INFINITY;
    }

    for (float[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
        }
      }
    }

    return result;
  }

  /**
   * Finds the maximum value on an element by element basis in the
   * arrays in the <code>ArrayList</code>.
   *
   * @param arrays An <code>ArrayList</code> of double arrays from which to find the maximum value.
   * @return An <code>double</code> array containing one element, the maximum value.
   */
  public double[] run(ArrayList<double[]> arrays) {
    int length = arrays.get(0).length;
    location = new int[length];

    double[] result = new double[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Double.NEGATIVE_INFINITY;
    }

    for (double[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] > result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
        }
      }
    }

    return result;
  }

  /**
   * Returns the array of locations for the maximums. This is only
   * valid AFTER the Reduce or Allreduce has been used, on the process
   * used as "root" for that operation.
   *
   * <p> The two arrays correspond such that location[i] is the rank
   * where result[i] can be found.
   *
   * @return An array of ranks where the maximums are found.
   */
  public int[] getLocArray() {
    return location;
  }
} // MaxLoc
