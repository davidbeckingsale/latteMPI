
package uk.ac.warwick.java.mpi;

import java.util.ArrayList;


/**
 * Abstract parent of the Mpi operations used with the
 * <code>Reduce</code> and <code>AllReduce</code> methods. Uses the
 * functor design pattern and can be run with the <code>run()</code>
 * method.
 *
 *
 * @author David Beckingsale
 */
public interface MpiOp {
  /**
   * Return the result of appling this operation to an
   * <code>ArrayList</code> of <code>int</code> arrays.
   *
   * @param arrays The <code>ArrayList</code> of operands.
   *
   * @return The result.
   */
  public int[] run(ArrayList<int[]> arrays);

  /**
   * Return the result of appling this operation to an
   * <code>ArrayList</code> of <code>long</code> arrays.
   *
   * @param arrays The <code>ArrayList</code> of operands.
   *
   * @return The result.
   */
  public long[] run(ArrayList<long[]> arrays);

  /**
   * Return the result of appling this operation to an
   * <code>ArrayList</code> of <code>float</code> arrays.
   *
   * @param arrays The <code>ArrayList</code> of operands.
   *
   * @return The result.
   */
  public float[] run(ArrayList<float[]> arrays);

  /**
   * Return the result of appling this operation to an
   * <code>ArrayList</code> of <code>double</code> arrays.
   *
   * @param arrays The <code>ArrayList</code> of operands.
   *
   * @return The result.
   */
  public double[] run(ArrayList<double[]> arrays);

} // MpiOp
