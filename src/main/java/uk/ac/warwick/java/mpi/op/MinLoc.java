package uk.ac.warwick.java.mpi.op;

import uk.ac.warwick.java.mpi.MpiOp;

import java.util.ArrayList;

/**
 * CLass providing the minloc operation defined in the MPI
 * standard. The </code>run()</code> method performs the same function
 * as the min operation, but also updates the location array to
 * contain the correct location of each minimum.
 *
 *
 * @author David Beckingsale
 */
public class MinLoc implements MpiOp {
  /** Stores the location of min after run() has been called */
  private static int[] location;

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
    location = new int[length];

    int[] result = new int[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Integer.MAX_VALUE;
    }

    for (int[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
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
    location = new int[length];

    long[] result = new long[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Long.MAX_VALUE;
    }

    for (long[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
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
    location = new int[length];

    float[] result = new float[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Float.POSITIVE_INFINITY;
    }

    for (float[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
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
    location = new int[length];

    double[] result = new double[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = Double.POSITIVE_INFINITY;
    }

    for (double[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
        if (array[i] < result[i]) {
          result[i] = array[i];
          location[i] = arrays.indexOf(array);
        }
      }
    }

    return result;
  }

  /**
   * Returns the array of locations for the maximums. This method is
   * only valid AFTER Reduce/AllReduce has been called, and only on
   * the process of rank "root" for that operation.
   *
   * <p> The two arrays correspond such that location[i] is the rank
   * where result[i] can be found.
   *
   * @return An array of ranks where the maximums are found.
   */
  public int[] getLocArray() {
    return location;
  }

} // MinLoc
