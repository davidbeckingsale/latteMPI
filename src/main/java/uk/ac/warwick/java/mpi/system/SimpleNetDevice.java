package uk.ac.warwick.java.mpi.system;



import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import uk.ac.warwick.java.mpi.Mpi;

import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;



/**
 * Provides abstract network operations used by MPI, and implements
 * {@link NetDevice}.
 *
 * @author David Beckingsale
 * @version 1
 * @since 27/10/2010
 */
public class SimpleNetDevice
  implements NetDevice
{

  private SimpleServer server_;
  private int port_;
  private MessageQueue messagequeue_;

  public SimpleNetDevice(int port){
    port_ = port;
    messagequeue_ = new MessageQueue();
    server_ = new SimpleServer(port, this);
    server_.start();
  }



  class SimpleServer extends Thread {
    private NetDevice netDevice_;
    private ServerSocket socket_ = null;
    private boolean listening_ = true;
    //private int port_;

    public SimpleServer(int port, NetDevice device) {
      port_ = port;
      netDevice_ = device;
    }

    public void run() {

      try {
        socket_ = new ServerSocket(port_);
      } catch (IOException ioException) {
        System.err.println("SimpleNetDevice:SimpleServer: \tCouldn't listen on port 4444");
        ioException.printStackTrace();
      }

      while (listening_) {
        try {
          new SimpleServerThread(socket_.accept()).start();
        } catch (IOException ioexception) {
          System.err.println("SimpleNetDevice:SimpleServer: \tCouldn't accept connection");
          ioexception.printStackTrace();
        }
      }
    }

    public void stopListening() {
      listening_ = false;
    }

  } //SimpleServer



  class SimpleServerThread extends Thread{
    private Socket socket_;

    public SimpleServerThread(Socket socket) {
      socket_ = socket;
    }

    public void run(){
      try {
        if (DEBUG_MODE) {
          System.out.println("SimpleServerThread: \tReceiving data!");
        }
        InputStream in = socket_.getInputStream();
        DataInputStream dis = new DataInputStream(in);

        int len = dis.readInt();
        byte[] data = new byte[len];

        if (len > 0) {
          dis.readFully(data);
        }
        if (DEBUG_MODE) {
          for (int i = 0; i < data.length; i++){
            if (DEBUG_MODE) {
              System.err.println("SimpleServerThread: \tRecieved: " + data[i]);
            }
          }
          if (DEBUG_MODE) {
            System.err.println("SimpleServerThread: \tEND OF DATA");
          }
        }

        //dis.flush();
        dis.close();
        Message m = new Message(data);
        if (data != null) messagequeue_.add(m);
      }
      catch (IOException ioexception) {
        System.err.println("SimpleServerThread: \tIO Exception");
        ioexception.printStackTrace();
      }
    }
  }

  /**
   * Uses blocking network I/O and a Java
   * <code>DataOutputStream</code> to send the <code>byte[]</code>
   * across the network.
   *
   * @param message The <code>Message</code> to send.
   * @param host The hostname of the target <code>Processor</code>.
   * @param portNumber The portnumber the host is listening on.
   */
  public void Send(Message message, String host, int portNumber) {
    if (DEBUG_MODE) {
      System.out.println("SimpleNetDevice: \tSend called with host: " + host + " and port " + portNumber);
    }
    byte[] messageArray = message.toByteArray();

    boolean gotSocket = false;

    while (!gotSocket) {
      try {
        Socket socket = new Socket(host, portNumber);
        OutputStream out = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);
        if (socket != null) {
          gotSocket = true;
        }


        dos.writeInt(messageArray.length);

        if (messageArray.length > 0) {
          dos.write(messageArray, 0, messageArray.length);
        }

      }
      catch (UnknownHostException unknownhostexception) {
        if (DEBUG_MODE) {
          System.err.println("SimpleNetDevice: \tUnkown host exception: " + host + " RETRYING");
        }
        //unknownhostexception.printStackTrace();
      }
      catch (IOException ioexception) {
        if (DEBUG_MODE) {
          System.err.println("SimpleNetDevice: \tIOException with host " + host + " and port " + portNumber + " RETRYING");
        }
        //ioexception.printStackTrace();
      }
    }
  }


  public Message Receive(int source, int tag) {
    Message m = null;

    while (m == null) {
      m = messagequeue_.get(source,tag);
    }

    return m;
  }
}
