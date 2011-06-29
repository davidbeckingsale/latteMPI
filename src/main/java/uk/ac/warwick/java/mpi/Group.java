package uk.ac.warwick.java.mpi;



import java.util.ArrayList;

import uk.ac.warwick.java.mpi.system.Processor;
import uk.ac.warwick.java.mpi.system.Message;
import uk.ac.warwick.java.mpi.system.MessageQueue;

import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;



/**
 * Class that holds all the information related to the Group
 * concept, and provides the methods for manipulating groups of
 * {@Processor}s.
 */
public class Group {
  /** All the processors in the current group. */
  private ArrayList<Processor> processors_;

  /** The <code>Processor</code> object corresponding to the local processor. */
  private Processor localProcessor_;

  /**
   * Create a group of n processes.
   *
   * @param n The number of processes.
   */
  public Group ( int n ) {

    if (DEBUG_MODE) {
      System.err.println( "In group constructor!" );
    }

    processors_ = new ArrayList<Processor>( n );
  }

  /**
   * Creates a new <code>Group</code>.
   *
   * @param processors The ArrayList of all the {@link #uk.ac.warwick.java.mpi.system.Processor}s in the
   * <code>Group</code>.
   * @param localProcessor The <code>Processor</code> that represents this
   * (local) process.
   */
  public Group(ArrayList<Processor> processors, Processor localProcessor) {
    processors_ = processors;
    localProcessor_ = localProcessor;
  }

  /**
   *
   * @return The size of the group.
   */
  public int Size() {
    return processors_.size();
  }

  /**
   *
   * @return The rank of the local processor in this group.
   */
  public int Rank() {
    return processors_.indexOf( localProcessor_ );
  }

  protected void Send(Message m, int dest) {
    //TODO
    localProcessor_.getNetDevice().Send(m,getProcessorAt(dest).getHost(), getProcessorAt(dest).getPort());
  }

  protected Message Recv(int source, int tag) {
    return localProcessor_.getNetDevice().Receive(source,tag);
  }

  protected Processor getLocalProcessor() {
    return localProcessor_;
  }

  protected Processor getProcessorAt(int i) {
    return processors_.get(i);
  }
}
