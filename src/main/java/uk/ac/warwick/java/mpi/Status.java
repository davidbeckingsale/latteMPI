package uk.ac.warwick.java.mpi;



/**
 * Class representing a status object
 *
 * Created: 10/11/10
 *
 * @author David Beckingsale
 * @version 1
 * @since 10/11/2010
 */
public class Status {
  private int source_;
  private int tag_;
  private int error_;

  public Status(int source, int tag, int error) {
    source_ = source;
    tag_ = tag;
    error_ = error;
  }

  public Status() { }

  public int getSource() {
    return source_;
  }

  public int getTag() {
    return tag_;
  }

  public int getError() {
    return error_;
  }

  protected void setSource(int source) {
    source_ = source;
  }

  protected void setTag(int tag) {
    tag_ = tag;
  }

  protected void setError(int error) {
    error_ = error;
  }
} // Status
