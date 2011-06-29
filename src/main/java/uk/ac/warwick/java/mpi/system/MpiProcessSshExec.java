package uk.ac.warwick.java.mpi.system;



import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelShell;


import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.File;



import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;



/**
 * Class used to establish connection to remote host and initiate the
 * MPI job
 */
public class MpiProcessSshExec extends MpiProcess
  implements Runnable {

  private volatile boolean isStopped_ = false;
  private Channel channel;
  private Session session;
  private String sshid;
  private final String JAVA_OPTS = "-Xmx900M";

  public  MpiProcessSshExec(int rank, int n, String className, String hostName,
                            String hostFile, String[] args, String sshid) {
    super(rank, n, className, hostName, hostFile, args);
    this.sshid = sshid;
  }

  /**
   * Connects to the provided hostName and starts the MpiProcess that
   * will set up the Mpi environment prior to running the user program.
   */
  public void run() {

    if ( DEBUG_MODE ) {
      System.err.println("MpiProcessSshExec: \tStarting run method");
      System.err.println("MpiProcessSshExec: \tThe params are - " + rank_ + hostName_ + hostFile_ + className_);
    }


    try {
      if ( DEBUG_MODE ) {
        System.err.println("MpiProcessSshExec: \tTrying to connect");
      }

      JSch jsch = new JSch();

      String dir = System.getProperty("user.dir");
      //System.out.println(dir);

      // Add ssh identity, use id_dsa by default, or use provided location
      if (sshid == null) {
        jsch.addIdentity(System.getProperty("user.home") + "/.ssh/id_dsa");
      } else {
        jsch.addIdentity(sshid);
      }

      String host = hostName_;
      String user = System.getProperty( "user.name" );

      session = jsch.getSession( user, host, 22 );


      //Ensure there isn't hostkey checking - this can cause problems
      java.util.Properties config = new java.util.Properties();
      config.put( "StrictHostKeyChecking", "no" );
      session.setConfig(config);
      session.setDaemonThread(false);

      // Ssh connects here
      session.connect();

       String command = "java " + JAVA_OPTS + " -classpath " + dir + "/latteMPI-1.0-SNAPSHOT.jar:" + dir + ":. " +
        "uk.ac.warwick.java.mpi.system.MpiRemoteProcess -hostfile " + hostFile_ + " -hostname "
        + hostName_ + " -rank " + rank_ + " -n " + n_ + " -className " + className_ + " -- " + arrayToList(args_);


      channel = session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);
      channel.setInputStream(null);
      ((ChannelExec)channel).setErrStream(System.err);
      // setPty lets remote jobs close properly!
      ((ChannelExec) channel).setPty(true);

      // This try block reads the output from the channel and sends it
      // to System.out, because of this, all process will output on
      // the root machine.
      try {
        InputStream in=channel.getInputStream();

        channel.connect();

        byte[] tmp=new byte[1024];

        while(!isStopped_){

          while(in.available()>0){
            int i=in.read(tmp, 0, 1024);
            //if(i<04)break;
            if (isStopped_) break;
            if (DEBUG_MODE) {
              System.out.println(rank_ + ": " + new String(tmp,0,i));
            }
            else {
              System.out.print(new String(tmp, 0, i));
            }
          }

          if(channel.isClosed()){
            // Grab the exit status when channel closes, this will be
            // used to signal errors in the remote processes
            System.out.println("exit-status: "+channel.getExitStatus());
          break;
          }
        }
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      //try{Thread.sleep(1000);}catch(Exception ee){}

      //channel.close();
      channel.disconnect();
      session.disconnect();
    }
    catch ( JSchException jschException) {
      System.err.println("MpiProcessSshExec: \tCannot load and connect to host");
      System.err.println("MpiProcessSshExec: \tHostname is: " + hostName_);
      jschException.printStackTrace();
    }
  }

  /**
   * Stops the remotely executing process and closes the ssh
   * connection.
   *
   * <p> This is essential during system clean up.
   */
  public void stopRemoteInstance() {
    if (DEBUG_MODE) {
      System.out.println("stopping jsch connection");
    }

    isStopped_ = true;

    try {
      ((ChannelExec) channel).sendSignal("KILL");
    }
    catch (Exception e) {
      System.out.println("Problem killing remote process!");
      e.printStackTrace();
    }
  }

  private String arrayToList(String[] a) {
    String argString = null;

    for (String s : a) {
      argString = argString + " " + s;
    }

    return argString;
  }
}
