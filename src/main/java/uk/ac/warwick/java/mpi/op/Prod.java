package uk.ac.warwick.java.mpi.op;

import java.util.ArrayList;
import uk.ac.warwick.java.mpi.MpiOp;

/**
 * Class containing the Prod operation. Works on an element by element
 * basis, not typical vector multiplication.
 *
 * <p> The product is the product of all the values in every array, on
 * an element by element basis. It is important to note that the
 * values returned by prod will grow very quickly and may overflow the
 * datatype.
 *
 * <p> The result of calling Prod on {1,2,3} and {2,2,2} would be
 * {2,4,6}.
 *
 *
 * @author David Beckingsale
 */
public class Prod implements MpiOp {

  /**
   * Finds the product of all the values in every array in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> containing the arrays to
   * be multiplied.
   * @return Array containing the results of element by element multiplication.
   */
  public int[] run(ArrayList<int[]> arrays) {
   int length = arrays.get(0).length;

    int[] result = new int[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 1;
    }

    for (int[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] * array[i];
        }
      }

    return result;
  }

  /**
   * Finds the product of all the values in every array in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> containing the arrays to
   * be multiplied.
   * @return Array containing the results of element by element multiplication.
   */
  public long[] run(ArrayList<long[]> arrays) {
   int length = arrays.get(0).length;

    long[] result = new long[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 1;
    }

    for (long[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] * array[i];
        }
      }

    return result;
  }

  /**
   * Finds the product of all the values in every array in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> containing the arrays to
   * be multiplied.
   * @return Array containing the results of element by element multiplication.
   */
  public float[] run(ArrayList<float[]> arrays) {
   int length = arrays.get(0).length;

    float[] result = new float[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 1;
    }

    for (float[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] * array[i];
        }
      }

    return result;
  }

  /**
   * Finds the product of all the values in every array in the
   * <code>ArrayList</code>.
   *
   * @param arrays The <code>ArrayList</code> containing the arrays to
   * be multiplied.
   * @return Array containing the results of element by element multiplication.
   */
  public double[] run(ArrayList<double[]> arrays) {
   int length = arrays.get(0).length;

    double[] result = new double[arrays.get(0).length];
    for (int i = 0; i < length; i++) {
      result[i] = 1;
    }

    for (double[] array : arrays) {
      for (int i = 0; i < array.length; i++) {
          result[i] = result[i] * array[i];
        }
      }

    return result;
  }
} // Prod
