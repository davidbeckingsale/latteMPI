package uk.ac.warwick.java.mpi.system;



/**
 * Represents the concept of a <code>Processor</code> in the MPI environment.
 *
 *
 * @author David Beckingsale
 * @version 1
 * @since 13/10/2010
 */
public class Processor {
  private String hostname_;
  private NetDevice net_;
  private int port_;

  /**
   * Create a new <code>Processor</code> that represents a remote
   * process.
   *
   * <p> The host and port variables hold information on how to contact this remote <code>Processor</code>.
   *
   *@param host The hostname of the machine running the remote process.
   * @param port The port number the remote host is listening on.
   */
  public Processor(String host, int port) {
    hostname_ = host;
    port_ = port;
  }

  /**
   * Create a new <code>Processor</code>, this represents the local process.
   *
   * <p> The <code>NetDevice</code> is the object responsible for all
   * network communication, and is used for sending and receiving
   * messages.
   *
   * @param host The hostname of the local machine.
   * @param port The port number to listen on.
   * @param net The <code>NetDevice</code> object responsible for network
   * communications.
   */
  public Processor(String host, int port, NetDevice net) {
    hostname_ = host;
    net_ = net;
    port_ = port;
  }

  /**
   * Assign a <code>NetDevice</code> object to this <code>Processor</code>.
   */
  public void setNetDevice(NetDevice net) {
    net_ = net;
  }

  /**
   * Get the hostname of this <code>Processor</code>.
   *
   * @return The hostname for this <code>Processor</code>.
   */
  public String getHost(){
    return hostname_;
  }

  /**
   * Get the <code>NetDevice</code> object assigned to this <code>Processor</code>.
   *
   * @return The <code>NetDevice</code> object used by this <code>Processor</code>.
   */
  public NetDevice getNetDevice(){
    return net_;
  }

  /**
   * Get the port that this <code>Processor</code> is listening on.
   *
   * @return The port this <code>Processor</code> is listening on.
   */
  public int getPort() {
    return port_;
  }
} // Processor
