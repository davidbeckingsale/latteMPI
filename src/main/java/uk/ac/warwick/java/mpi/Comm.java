package uk.ac.warwick.java.mpi;



import java.io.File;
import java.lang.Math;
import java.util.ArrayList;

import uk.ac.warwick.java.mpi.system.*;

// Import the DEBUG_MODE constant from the Mpi class
import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;
import static uk.ac.warwick.java.mpi.Mpi.TREE;


/**
 * Holds data about MPI Communicators
 *
 * @author David Beckingsale
 * @version 1
 * @since 08/10/2010
 */
public class Comm {

  private Group group_;

  public int id;

  protected static int REDUCE_TAG = -5;
  protected static int BROADCAST_TAG = -10000;

  /**
   * Constructs a new Communicator without using a host file, all
   * processes will be run on the local machine.
   */
  public Comm( int n ) {
    if( DEBUG_MODE ) {
      if (n > 0) {
        System.err.println( "Comm: \tCreating Comm of size " + n );
      }
      else {
        System.err.println("Comm: \tCreating Comm of size 0, this should be COMM_NULL");
      }
    }
    group_ = new Group( n );
  }

  /**
   * Constructs a new communicator with the given {@link Group} as
   * it's group.
   *
   * @param group The group that describes this communicator
   */
  public Comm(Group group) {
    group_ = group;
  }

  /**
   * Returns the size of the Communicator
   *
   * @return The number of Processors in this Communicator.
   */
  public int Size() {
    return group_.Size();
  }

/**
 * Returns the rank of the local processor within the Group associated
 * with this Communicator.
 *
 * @return The rank of the local processor.
 */
  public int Rank() {
    return group_.Rank();
  }

  /**
   * Blocking send of the values in the array <code>data</code> to the
   * process of rank <code>dest</code>.
   *
   * @param data The array of values to be sent.
   * @param dest The rank of the destination process.
   * @param tag The tag for the message.
   */
  public void  Send(int[] data, int dest, int tag) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Send(int[]) method");
    }
    Message message = new Message(Message.dataToByteArray(data), Rank(), tag, Message.TYPE_INT);
    if (DEBUG_MODE) {
      System.err.println("Comm: \tSending...");
      System.err.println(message);
    }
    group_.Send(message, dest);
  }

  /**
   * Blocking send of the values in the array <code>data</code> to the
   * process of rank <code>dest</code>.
   *
   * @param data The array of values to be sent.
   * @param dest The rank of the destination process.
   * @param tag The tag for the message.
   */
  public void Send(long[] data, int dest, int tag) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Send(int[]) method");
    }
    Message message = new Message(Message.dataToByteArray(data), Rank(), tag, Message.TYPE_LONG);
    if (DEBUG_MODE) {
      System.err.println("Comm: \tSending...");
      System.err.println(message);
    }
    group_.Send(message, dest);
  }

  /**
   * Blocking send of the values in the array <code>data</code> to the
   * process of rank <code>dest</code>.
   *
   * @param data The array of values to be sent.
   * @param dest The rank of the destination process.
   * @param tag The tag for the message.
   */
  public void Send(float[] data, int dest, int tag) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Send(int[]) method");
    }
    Message message = new Message(Message.dataToByteArray(data), Rank(), tag, Message.TYPE_FLOAT);
    if (DEBUG_MODE) {
      System.err.println("Comm: \tSending...");
      System.err.println(message);
    }
    group_.Send(message, dest);
  }

  /**
   * Blocking send of the values in the array <code>data</code> to the
   * process of rank <code>dest</code>.
   *
   * @param data The array of values to be sent.
   * @param dest The rank of the destination process.
   * @param tag The tag for the message.
   */
  public void Send(double[] data, int dest, int tag) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Send(int[]) method");
    }
    Message message = new Message(Message.dataToByteArray(data), Rank(), tag, Message.TYPE_DOUBLE);
    if (DEBUG_MODE) {
      System.err.println("Comm: \tSending...");
      System.err.println(message);
    }
    group_.Send(message, dest);
  }

  /**
   * Blocking receive of a message from process
   * <code>source</code>. The data will be copied into the array
   * <code>data</code>.
   *
   * @param data The array to copy the data into.
   * @param source The rank of the source process, or <code>Mpi.ANY_SOURCE</code>.
   * @param tag The tag of the message, or <code>Mpi.ANY_TAG</code>.
   * @param status The object holding the status of this operation.
   */
  public void Recv(int[] data, int source, int tag, Status status) {
    Message message = group_.Recv(source, tag);
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered receive method, group_.Recv complete");
    }

    if (message.dataToPrimitiveInt().length != data.length) {
      throw new MpiException("Message lengths do not match!");
    }
    else if (message.getType() != Message.TYPE_INT) {
      throw new MpiException("Message types do not match, please check your tag usage");
    }
    else {
      if (DEBUG_MODE) {
        System.err.println("Comm: \tCopying message data to user buffer");
      }

      System.arraycopy(message.dataToPrimitiveInt(),0,data,0, data.length);
    }

    status.setSource(message.getSource());
    status.setTag(message.getTag());
    status.setError(Mpi.SUCCESS);
  }


  /**
   * Blocking receive of a message from process
   * <code>source</code>. The data will be copied into the array
   * <code>data</code>.
   *
   * @param data The array to copy the data into.
   * @param source The rank of the source process, or <code>Mpi.ANY_SOURCE</code>.
   * @param tag The tag of the message, or <code>Mpi.ANY_TAG</code>.
   * @param status The object holding the status of this operation.
   */
  public void Recv(long[] data, int source, int tag, Status status) {
    Message message = group_.Recv(source, tag);

    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered receive method, group_.Recv complete");
    }

    if (message.dataToPrimitiveLong().length != data.length) {
      throw new MpiException("Message lengths do not match!");
    }
    else if (message.getType() != Message.TYPE_LONG) {
      throw new MpiException("Message types do not match, please check your tag usage");
    }
    else {
      if (DEBUG_MODE) {
        System.err.println("Comm: \tCopying message data to user buffer");
      }

      System.arraycopy(message.dataToPrimitiveLong(),0,data,0, data.length);
    }

    status.setSource(message.getSource());
    status.setTag(message.getTag());
    status.setError(Mpi.SUCCESS);
  }


  /**
   * Blocking receive of a message from process
   * <code>source</code>. The data will be copied into the array
   * <code>data</code>.
   *
   * @param data The array to copy the data into.
   * @param source The rank of the source process, or <code>Mpi.ANY_SOURCE</code>.
   * @param tag The tag of the message, or <code>Mpi.ANY_TAG</code>.
   * @param status The object holding the status of this operation.
   */
  public void Recv(float[] data, int source, int tag, Status status) {
    Message message = group_.Recv(source, tag);

    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered receive method, group_.Recv complete");
    }

    if (message.dataToPrimitiveFloat().length != data.length) {
      throw new MpiException("Message lengths do not match!");
    }
    else if (message.getType() != Message.TYPE_FLOAT) {
      throw new MpiException("Message types do not match, please check your tag usage");
    }
    else {
      if (DEBUG_MODE) {
        System.err.println("Comm: \tCopying message data to user buffer");
      }

      System.arraycopy(message.dataToPrimitiveFloat(),0,data,0, data.length);
    }

    status.setSource(message.getSource());
    status.setTag(message.getTag());
    status.setError(Mpi.SUCCESS);
  }


  /**
   * Blocking receive of a message from process
   * <code>source</code>. The data will be copied into the array
   * <code>data</code>.
   *
   * @param data The array to copy the data into.
   * @param source The rank of the source process, or <code>Mpi.ANY_SOURCE</code>.
   * @param tag The tag of the message, or <code>Mpi.ANY_TAG</code>.
   * @param status The object holding the status of this operation.
   */
  public void Recv(double[] data, int source, int tag, Status status) {
    Message message = group_.Recv(source, tag);

    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered receive method, group_.Recv complete");
    }

    if (message.dataToPrimitiveDouble().length != data.length) {
      throw new MpiException("Message lengths do not match! Got " + message.dataToPrimitiveDouble().length + " and was expecting " + data.length);
    }
    else if (message.getType() != Message.TYPE_DOUBLE) {
      throw new MpiException("Message types do not match, please check your tag usage");
    }
    else {
      if (DEBUG_MODE) {
        System.err.println("Comm: \tCopying message data to user buffer");
      }

      System.arraycopy(message.dataToPrimitiveDouble(),0,data,0, data.length);
    }

    status.setSource(message.getSource());
    status.setTag(message.getTag());
    status.setError(Mpi.SUCCESS);
  }

  /**
   * Broadcasts the data in the <code>data</code> array from the
   * process with rank <code>root</code> to all other processes.
   *
   * @param data The array containing the data (at root) and where the
   * data will be stored at the other processes.
   * @param root The rank of the processes to be used as the root.
   */
  public void Bcast(int[] data, int root) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Bcast int[]");
    }

    int size = group_.Size();
    int old_rank = Rank();
    int rank = relativeRank(root,old_rank,size);

    int dest;
    int src;

    boolean received = false;

    Message send;
    Message recv = null;

    /*
     * Error checking
     */

    /*
     * Binary-tree broadcast
     */
    for (int stage = 0; stage < (int)Math.ceil(Math.log(size)/Math.log(2)); stage++) {
      if (DEBUG_MODE) {
      System.err.println("Comm: \tRank: " + old_rank + ", Relative rank: " + rank  + " Stage: " + stage);
      }

      // Receive stage
      if (rank < Math.pow(2,stage+1) && rank >= Math.pow(2,stage)) {

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is receiving from " + (rank - (Math.pow(2,stage))));
        }

        // Copy the received message into recv.
        recv = group_.Recv(originalRank(root, (int)(rank-Math.pow(2,stage)), size), BROADCAST_TAG);
        // Set received to true
        received = true;

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " received OK");
          System.err.println("Array has length: " + recv.dataToPrimitiveInt().length);
          System.err.println("Data has length: " + data.length);
        }

        if (recv.getType() != Message.TYPE_INT) {
          throw new MpiException("Message types do not match, please check your tag usage");
        }
        else {
          System.arraycopy(recv.dataToPrimitiveInt(), 0, data, 0, recv.dataToPrimitiveInt().length);
        }
      }

      // Send stage
      else if (rank < Math.pow(2,stage) && (rank+Math.pow(2,stage)) < size) {
        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is sending to " + (int)(rank + Math.pow(2,stage)));
        }

        // If a message has been received then recycle this and update its source before sending it on.
        if (received) {
          send = recv;
          send.setSource(originalRank(root,rank,size));
        }
        // Otherwise read in the values from the given data (this should only happen at the "root")
        else {
          send = new Message(Message.dataToByteArray(data), originalRank(root,rank,size), BROADCAST_TAG, Message.TYPE_INT);
          recv = send;
          received = true;
        }

        // Send the message
        group_.Send(send, originalRank(root, (int)(rank+Math.pow(2,stage)), size));
      }
    }

    BROADCAST_TAG--;
  }

  /**
   * Broadcasts the data in the <code>data</code> array from the
   * process with rank <code>root</code> to all other processes.
   *
   * @param data The array containing the data (at root) and where the
   * data will be stored at the other processes.
   * @param root The rank of the processes to be used as the root.
   */
  public void Bcast(long[] data, int root) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Bcast long[]");
    }

    int size = group_.Size();
    int old_rank = Rank();
    int rank = relativeRank(root,old_rank,size);

    int dest;
    int src;

    boolean received = false;

    Message send;
    Message recv = null;

    /*
     * Error checking
     */

    /*
     * Binary-tree broadcast
     */
    for (int stage = 0; stage < (int)Math.ceil(Math.log(size)/Math.log(2)); stage++) {
      if (DEBUG_MODE) {
      System.err.println("Comm: \tRank: " + old_rank + ", Relative rank: " + rank  + " Stage: " + stage);
      }

      // Receive stage
      if (rank < Math.pow(2,stage+1) && rank >= Math.pow(2,stage)) {

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is receiving from " + (rank - (Math.pow(2,stage))));
        }

        // Copy the received message into recv.
        recv = group_.Recv(originalRank(root, (int)(rank-Math.pow(2,stage)), size), BROADCAST_TAG);
        // Set received to true
        received = true;

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " received OK");
        }

        if (recv.getType() != Message.TYPE_LONG) {
          throw new MpiException("Message types do not match, please check your tag usage");
        }
        else {
          System.arraycopy(recv.dataToPrimitiveLong(), 0, data, 0, recv.dataToPrimitiveLong().length);
        }
      }

      // Send stage
      else if (rank < Math.pow(2,stage) && (rank+Math.pow(2,stage)) < size) {
        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is sending to " + (int)(rank + Math.pow(2,stage)));
        }

        // If a message has been received then recycle this and update its source before sending it on.
        if (received) {
          send = recv;
          send.setSource(originalRank(root,rank,size));
        }
        // Otherwise read in the values from the given data (this should only happen at the "root")
        else {
          send = new Message(Message.dataToByteArray(data), originalRank(root,rank,size), BROADCAST_TAG, Message.TYPE_LONG);
          recv = send;
          received = true;
        }

        // Send the message
        group_.Send(send, originalRank(root, (int)(rank+Math.pow(2,stage)), size));
      }
    }

    BROADCAST_TAG--;
  }

  /**
   * Broadcasts the data in the <code>data</code> array from the
   * process with rank <code>root</code> to all other processes.
   *
   * @param data The array containing the data (at root) and where the
   * data will be stored at the other processes.
   * @param root The rank of the processes to be used as the root.
   */
  public void Bcast(float[] data, int root) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Bcast float[]");
    }

    int size = group_.Size();
    int old_rank = Rank();
    int rank = relativeRank(root,old_rank,size);

    int dest;
    int src;

    boolean received = false;

    Message send;
    Message recv = null;

    /*
     * Error checking
     */

    /*
     * Binary-tree broadcast
     */
    for (int stage = 0; stage < (int)Math.ceil(Math.log(size)/Math.log(2)); stage++) {
      if (DEBUG_MODE) {
      System.err.println("Comm: \tRank: " + old_rank + ", Relative rank: " + rank  + " Stage: " + stage);
      }

      // Receive stage
      if (rank < Math.pow(2,stage+1) && rank >= Math.pow(2,stage)) {

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is receiving from " + (rank - (Math.pow(2,stage))));
        }

        // Copy the received message into recv.
        recv = group_.Recv(originalRank(root, (int)(rank-Math.pow(2,stage)), size), BROADCAST_TAG);
        // Set received to true
        received = true;

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " received OK");
        }

        if (recv.getType() != Message.TYPE_FLOAT) {
          throw new MpiException("Message types do not match, please check your tag usage");
        }
        else {
          System.arraycopy(recv.dataToPrimitiveFloat(), 0, data, 0, recv.dataToPrimitiveFloat().length);
        }
      }

      // Send stage
      else if (rank < Math.pow(2,stage) && (rank+Math.pow(2,stage)) < size) {
        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is sending to " + (int)(rank + Math.pow(2,stage)));
        }

        // If a message has been received then recycle this and update its source before sending it on.
        if (received) {
          send = recv;
          send.setSource(originalRank(root,rank,size));
        }
        // Otherwise read in the values from the given data (this should only happen at the "root")
        else {
          send = new Message(Message.dataToByteArray(data), originalRank(root,rank,size), BROADCAST_TAG, Message.TYPE_FLOAT);
          recv = send;
          received = true;
        }

        // Send the message
        group_.Send(send, originalRank(root, (int)(rank+Math.pow(2,stage)), size));
      }
    }

    BROADCAST_TAG--;
  }

  /**
   * Broadcasts the data in the <code>data</code> array from the
   * process with rank <code>root</code> to all other processes.
   *
   * @param data The array containing the data (at root) and where the
   * data will be stored at the other processes.
   * @param root The rank of the processes to be used as the root.
   */
  public void Bcast(double[] data, int root) {
    if (DEBUG_MODE) {
      System.err.println("Comm: \tEntered Bcast double[]");
    }

    int size = group_.Size();
    int old_rank = Rank();
    int rank = relativeRank(root,old_rank,size);

    int dest;
    int src;

    boolean received = false;

    Message send;
    Message recv = null;

    /*
     * Error checking
     */

    /*
     * Binary-tree broadcast
     */
    for (int stage = 0; stage < (int)Math.ceil(Math.log(size)/Math.log(2)); stage++) {
      if (DEBUG_MODE) {
      System.err.println("Comm: \tRank: " + old_rank + ", Relative rank: " + rank  + " Stage: " + stage);
      }

      // Receive stage
      if (rank < Math.pow(2,stage+1) && rank >= Math.pow(2,stage)) {

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is receiving from " + (rank - (Math.pow(2,stage))));
        }

        // Copy the received message into recv.
        recv = group_.Recv(originalRank(root, (int)(rank-Math.pow(2,stage)), size), BROADCAST_TAG);
        // Set received to true
        received = true;

        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " received OK");
        }

        if (recv.getType() != Message.TYPE_DOUBLE) {
          throw new MpiException("Message types do not match, please check your tag usage");
        }
        else {
          System.arraycopy(recv.dataToPrimitiveDouble(), 0, data, 0, recv.dataToPrimitiveDouble().length);
        }
      }

      // Send stage
      else if (rank < Math.pow(2,stage) && (rank+Math.pow(2,stage)) < size) {
        if (DEBUG_MODE) {
          System.err.println("Comm: \t" + rank + " is sending to " + (int)(rank + Math.pow(2,stage)));
        }

        // If a message has been received then recycle this and update its source before sending it on.
        if (received) {
          send = recv;
          send.setSource(originalRank(root,rank,size));
        }
        // Otherwise read in the values from the given data (this should only happen at the "root")
        else {
          send = new Message(Message.dataToByteArray(data), originalRank(root,rank,size), BROADCAST_TAG, Message.TYPE_DOUBLE);
          recv = send;
          received = true;
        }

        // Send the message
        group_.Send(send, originalRank(root, (int)(rank+Math.pow(2,stage)), size));
      }
    }

    BROADCAST_TAG--;
  }

  protected static int relativeRank(int root, int currentRank, int size) {
    return (currentRank >= root) ? currentRank-root: (currentRank - root) + size;
  }

  protected static int originalRank(int root, int relativeRank, int size) {
    return relativeRank(relativeRank(root,0,size), relativeRank, size);
  }

  /**
   * Provides a global synchronization point. It is guaranteed that no
   * process will complete <code>Barrier</code> until all other
   * processes have entered.
   */
  public void Barrier() {
    int[] bcastData = {0};
    int[] outdata = new int[1];

    AllReduce(bcastData,outdata,Mpi.SUM);
  }

  /**
   * Reduction operation, collects the data and performs the operation
   * described by <code>op</code> on it. After completion, the result will reside
   * on the process <code>root</code>.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed on process <code>root</code>.
   * @param op The operation to perform on the data.
   * @param root The process where the result will be placed.
   */
  public void Reduce(int[] inData, int[] outData, MpiOp op, int root) {

    if (TREE) {
      tree_reduce(inData, outData, op, root);
    } else {
      naive_reduce(inData, outData, op, root);
    }

    REDUCE_TAG--;
  }

  private void tree_reduce(int[] inData, int[] outData, MpiOp op, int root) {

    int oldrank = Rank();
    int orig_size = Size();
    int size = orig_size;
    int rank = relativeRank(root, oldrank, size);
    Message recv;
    int[] current = inData;

    /*
     * Binary tree reduction
     */
    for (int stage = (int) Math.ceil(Math.log(size)/Math.log(2)); stage > 0; stage--) {

      //System.out.println("Stage = " + stage);

       if (rank < Math.pow(2,stage) && rank >= Math.pow(2,stage-1)) {

         //System.out.println(rank + " sending to... " + originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));

         group_.Send(new Message(Message.dataToByteArray(current), originalRank(root,rank,orig_size), REDUCE_TAG, Message.TYPE_INT), originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));
       }
       else if (rank < Math.pow(2,stage-1) && (rank + Math.pow(2,stage-1)) < size ) {

         //System.out.println(rank + " receiving from... " + originalRank(root, (int)Math.pow(2,stage-1) + rank, orig_size));
         recv = group_.Recv(originalRank(root, (int) Math.pow(2,stage-1) + rank, orig_size), REDUCE_TAG);

         ArrayList<int[]> list = new ArrayList<int[]>();
         list.add(current);
         list.add(recv.dataToPrimitiveInt());
         current = op.run(list);
         //System.out.println("Rank: " + rank + " tot: " + current[0]);
       }

     }

    if (oldrank == root) {
      System.arraycopy(current, 0 , outData, 0,current.length);
    }
}

  private void naive_reduce(int[] inData, int[] outData, MpiOp op, int root) {

     if (Rank() != root) {
       group_.Send(new Message(Message.dataToByteArray(inData), Rank(), REDUCE_TAG, Message.TYPE_INT), root);
     }
     else if (Rank() == root) {
       ArrayList<int[]> list = new ArrayList<int[]>();

       for (int i = 0; i < Size(); i++) {
         if (Rank() == i) {
           list.add(inData);
         }
         else {
           list.add(group_.Recv(i, REDUCE_TAG).dataToPrimitiveInt());
         }
       }

       int[] result = op.run(list);

       System.arraycopy(result, 0, outData, 0, result.length);
     }
  }

  /**
   * Reduction operation, collects the data and performs the operation
   * described by <code>op</code> on it. After completion, the result will reside
   * on the process <code>root</code>.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed on process <code>root</code>.
   * @param op The operation to perform on the data.
   * @param root The process where the result will be placed.
   */
  public void Reduce(long[] inData, long[] outData, MpiOp op, int root) {

    if (TREE) {
      tree_reduce(inData, outData, op, root);
    } else {
      naive_reduce(inData, outData, op, root);
    }

    REDUCE_TAG--;
  }

  private void tree_reduce(long[] inData, long[] outData, MpiOp op, int root) {
    int oldrank = Rank();
    int orig_size = Size();
    int size = orig_size;
    int rank = relativeRank(root, oldrank, size);
    Message recv;
    long[] current = inData;

    /*
     * Binary tree reduction
     */
    for (int stage = (int) Math.ceil(Math.log(size)/Math.log(2)); stage > 0; stage--) {

      //System.out.println("Stage = " + stage);

       if (rank < Math.pow(2,stage) && rank >= Math.pow(2,stage-1)) {

         //System.out.println(rank + " sending to... " + originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));

         group_.Send(new Message(Message.dataToByteArray(current), originalRank(root,rank,orig_size), REDUCE_TAG, Message.TYPE_LONG), originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));
       }
       else if (rank < Math.pow(2,stage-1) && (rank + Math.pow(2,stage-1)) < size ) {

         //System.out.println(rank + " receiving from... " + originalRank(root, (int)Math.pow(2,stage-1) + rank, orig_size));
         recv = group_.Recv(originalRank(root, (int) Math.pow(2,stage-1) + rank, orig_size), REDUCE_TAG);

         ArrayList<long[]> list = new ArrayList<long[]>();
         list.add(current);
         list.add(recv.dataToPrimitiveLong());
         current = op.run(list);
         //System.out.println("Rank: " + rank + " tot: " + current[0]);
       }

     }

    if (oldrank == root) {
      System.arraycopy(current, 0 , outData, 0,current.length);
    }
  }

  private void naive_reduce(long[] inData, long[] outData, MpiOp op, int root) {
     if (Rank() != root) {
       group_.Send(new Message(Message.dataToByteArray(inData), Rank(), REDUCE_TAG, Message.TYPE_LONG), root);
     }
     else if (Rank() == root) {
       ArrayList<long[]> list = new ArrayList<long[]>();
         for (int i = 0; i < Size(); i++) {
           if (Rank() == i) {
           list.add(inData);
         }
         else {
           list.add(group_.Recv(i, REDUCE_TAG).dataToPrimitiveLong());
         }
       }

       long[] result = op.run(list);

       System.arraycopy(result, 0, outData, 0, result.length);
     }
  }

  /**
   * Reduction operation, collects the data and performs the operation
   * described by <code>op</code> on it. After completion, the result will reside
   * on the process <code>root</code>.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed on process <code>root</code>.
   * @param op The operation to perform on the data.
   * @param root The process where the result will be placed.
   */
  public void Reduce(float[] inData, float[] outData, MpiOp op, int root) {

    if (TREE) {
      tree_reduce(inData, outData, op, root);
    } else {
      naive_reduce(inData, outData, op, root);
    }

    REDUCE_TAG--;
  }

  private void tree_reduce(float[] inData, float[] outData, MpiOp op, int root) {
    int oldrank = Rank();
    int orig_size = Size();
    int size = orig_size;
    int rank = relativeRank(root, oldrank, size);
    Message recv;
    float[] current = inData;

    /*
     * Binary tree reduction
     */
    for (int stage = (int) Math.ceil(Math.log(size)/Math.log(2)); stage > 0; stage--) {

      //System.out.println("Stage = " + stage);

       if (rank < Math.pow(2,stage) && rank >= Math.pow(2,stage-1)) {

         //System.out.println(rank + " sending to... " + originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));

         group_.Send(new Message(Message.dataToByteArray(current), originalRank(root,rank,orig_size), REDUCE_TAG, Message.TYPE_FLOAT), originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));
       }
       else if (rank < Math.pow(2,stage-1) && (rank + Math.pow(2,stage-1)) < size ) {

         //System.out.println(rank + " receiving from... " + originalRank(root, (int)Math.pow(2,stage-1) + rank, orig_size));
         recv = group_.Recv(originalRank(root, (int) Math.pow(2,stage-1) + rank, orig_size), REDUCE_TAG);

         ArrayList<float[]> list = new ArrayList<float[]>();
         list.add(current);
         list.add(recv.dataToPrimitiveFloat());
         current = op.run(list);
         // System.out.println("Rank: " + rank + " tot: " + current[0]);
       }

     }

    if (oldrank == root) {
      System.arraycopy(current, 0 , outData, 0,current.length);
    }
  }

  private void naive_reduce(float[] inData, float[] outData, MpiOp op, int root) {
     if (Rank() != root) {
       group_.Send(new Message(Message.dataToByteArray(inData), Rank(), REDUCE_TAG, Message.TYPE_FLOAT), root);
     }
     else if (Rank() == root) {
       ArrayList<float[]> list = new ArrayList<float[]>();

       for (int i = 0; i < Size(); i++) {
         if (Rank() == i) {
           list.add(inData);
         }
         else {
           list.add(group_.Recv(i, REDUCE_TAG).dataToPrimitiveFloat());
         }
       }

       float[] result = op.run(list);

       System.arraycopy(result, 0, outData, 0, result.length);
     }

  }

  /**
   * Reduction operation, collects the data and performs the operation
   * described by <code>op</code> on it. After completion, the result will reside
   * on the process <code>root</code>.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed on process <code>root</code>.
   * @param op The operation to perform on the data.
   * @param root The process where the result will be placed.
   */
  public void Reduce(double[] inData, double[] outData, MpiOp op, int root) {

    if (TREE) {
      tree_reduce(inData, outData, op, root);
    } else {
      naive_reduce(inData, outData, op, root);
    }

    REDUCE_TAG--;
  }

  private void tree_reduce(double[] inData, double[] outData, MpiOp op, int root) {

    int oldrank = Rank();
    int orig_size = Size();
    int size = orig_size;
    int rank = relativeRank(root, oldrank, size);
    Message recv;
    double[] current = inData;

    /*
     * Binary tree reduction
     */
    for (int stage = (int) Math.ceil(Math.log(size)/Math.log(2)); stage > 0; stage--) {

      //System.out.println("Stage = " + stage);

       if (rank < Math.pow(2,stage) && rank >= Math.pow(2,stage-1)) {

         //System.out.println(rank + " sending to... " + originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));

         group_.Send(new Message(Message.dataToByteArray(current), originalRank(root,rank,orig_size), REDUCE_TAG, Message.TYPE_DOUBLE), originalRank(root, rank - (int) Math.pow(2,stage-1), orig_size));
       }
       else if (rank < Math.pow(2,stage-1) && (rank + Math.pow(2,stage-1)) < size ) {

         //System.out.println(rank + " receiving from... " + originalRank(root, (int)Math.pow(2,stage-1) + rank, orig_size));
         recv = group_.Recv(originalRank(root, (int) Math.pow(2,stage-1) + rank, orig_size), REDUCE_TAG);

         ArrayList<double[]> list = new ArrayList<double[]>();
         list.add(current);
         list.add(recv.dataToPrimitiveDouble());
         current = op.run(list);
         //System.out.println("Rank: " + rank + " tot: " + current[0]);
       }

     }

    if (oldrank == root) {
      System.arraycopy(current, 0 , outData, 0,current.length);
    }
  }

  private void naive_reduce(double[] inData, double[] outData, MpiOp op, int root) {
    if (Rank() != root) {
       group_.Send(new Message(Message.dataToByteArray(inData), Rank(), REDUCE_TAG, Message.TYPE_DOUBLE), root);
     }
     else if (Rank() == root) {
       ArrayList<double[]> list = new ArrayList<double[]>();

       for (int i = 0; i < Size(); i++) {
         if (Rank() == i) {
           list.add(inData);
         }
         else {
           list.add(group_.Recv(i, REDUCE_TAG).dataToPrimitiveDouble());
         }
       }

       double[] result = op.run(list);

       System.arraycopy(result, 0, outData, 0, result.length);
     }
  }

  /**
   * All-to-all reduction operation, collects the data and performs
   * the operation described by <code>op</code> on it. After
   * completion, the result will reside in <code>outData</code> on all processes.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed.
   * @param op The operation to perform on the data.
   */
  public void AllReduce(int[] inData, int[] outData, MpiOp op) {

    int[] result = new int[inData.length];


    Reduce(inData, result, op, 0);

    //REDUCE_TAG--;

    Bcast(result, 0);

    System.arraycopy(result, 0, outData, 0, result.length);
  }

  /**
   * All-to-all reduction operation, collects the data and performs
   * the operation described by <code>op</code> on it. After
   * completion, the result will reside in <code>outData</code> on all processes.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed.
   * @param op The operation to perform on the data.
   */
  public void AllReduce(long[] inData, long[] outData, MpiOp op) {

    long[] result = new long[inData.length];



    Reduce(inData, result, op, 0);

    //REDUCE_TAG--;

    Bcast(result, 0);

    System.arraycopy(result, 0, outData, 0, result.length);
  }

  /**
   * All-to-all reduction operation, collects the data and performs
   * the operation described by <code>op</code> on it. After
   * completion, the result will reside in <code>outData</code> on all processes.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed.
   * @param op The operation to perform on the data.
   */
  public void AllReduce(float[] inData, float[] outData, MpiOp op) {

    float[] result = new float[inData.length];


    Reduce(inData, result, op, 0);

    //REDUCE_TAG--;

    Bcast(result, 0);

    System.arraycopy(result, 0, outData, 0, result.length);
  }

  /**
   * All-to-all reduction operation, collects the data and performs
   * the operation described by <code>op</code> on it. After
   * completion, the result will reside in <code>outData</code> on all processes.
   *
   * @param inData The input data or operand.
   * @param outData The array where the result will be placed.
   * @param op The operation to perform on the data.
   */
  public void AllReduce(double[] inData, double[] outData, MpiOp op) {

    double[] result = new double[inData.length];



    Reduce(inData, result, op, 0);

    //REDUCE_TAG--;

    Bcast(result, 0);

    System.arraycopy(result, 0, outData, 0, result.length);
  }
}
