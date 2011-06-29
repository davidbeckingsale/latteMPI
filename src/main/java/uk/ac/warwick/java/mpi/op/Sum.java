package uk.ac.warwick.java.mpi.op;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;

/**
 * Class containing the Sum operation.
 *
 * <p> The elements of the arrays are summed on an element by element
 * basis. For example, Summing {1,1,1} and {5,2,8} would return
 * {6,3,9}.
 *
 *
 * @author David Beckingsale
 */
public class Sum implements MpiOp {

  /**
   * Sums all the arrays in the array list <code>arrays</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays to add.
   * @return An array containing, at each position, the sum of that position.
   */
  public int[] run(ArrayList<int[]> arrays) {
   int length = arrays.get(0).length;

    int[] result = new int[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 0;
    }

    for (int[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] + array[i];
        }
      }

    return result;
  }

  /**
   * Sums all the arrays in the array list <code>arrays</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays to add.
   * @return An array containing, at each position, the sum of that position.
   */
  public long[] run(ArrayList<long[]> arrays) {
   int length = arrays.get(0).length;

    long[] result = new long[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 0;
    }

    for (long[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] + array[i];
        }
      }

    return result;
  }

  /**
   * Sums all the arrays in the array list <code>arrays</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays to add.
   * @return An array containing, at each position, the sum of that position.
   */
  public float[] run(ArrayList<float[]> arrays) {
   int length = arrays.get(0).length;

    float[] result = new float[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 0;
    }

    for (float[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] + array[i];
        }
      }

    return result;
  }

  /**
   * Sums all the arrays in the array list <code>arrays</code>.
   *
   * @param arrays The <code>ArrayList</code> of arrays to add.
   * @return An array containing, at each position, the sum of that position.
   */
  public double[] run(ArrayList<double[]> arrays) {
   int length = arrays.get(0).length;

    double[] result = new double[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 0;
    }

    for (double[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] + array[i];
        }
      }

    return result;
  }
} // Sum
