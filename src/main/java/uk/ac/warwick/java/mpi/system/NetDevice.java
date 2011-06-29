package uk.ac.warwick.java.mpi.system;

/**
 * Interface to represent the most abstract versions of the methods
 * needed by MPI to perform basic network operations.
 *
 * @author David Beckingsale
 * @since 27/10/2010
 */
public interface NetDevice {
  /** Send a message to the correct {@link
   * uk.ac.warwick.java.mpi.system.Processor}. The
   * <code>Processor</code> will be listening on a particular
   * (host,port) tuple defined by the strings <code>host</code> and
   * <code>portNumber</code>.
   *
   * @param message The message to be sent
   * @param host The hostname of the machine to be sent the message
   * @param portNumber The port that the host is listening on
   */
  public abstract void Send(Message message, String host, int portNumber);

  // /**
  //  * Execute a non-blocking send. Semantics are identical to the
  //  * blocking Send.
  //  *
  //  * @param messageArray The message to be sent
  //  * @param host The hostname of the machine to be sent the message
  //  * @param portNumber The port that the host is listening on
  //  */
  // public void AsynchronousSend(byte[] messageArray, String host, int portNumber);

  /**
   * Recieve a message from the <code>host</code> on port
   * <code>portNumber<code>.
   *
   * <p> A {@link uk.ac.warwick.java.mpi.system.Message} will then be
   * created and put in the message buffer.
   *
   * @param source The id of the source process.
   * @param tag The message tag.
   *
   * @return The matching <code>Message</code>.
   */
  public Message Receive(int source, int tag);

  //public void AsynchronousReceive(byte[] messageArray, String host, int portNumber);
}
