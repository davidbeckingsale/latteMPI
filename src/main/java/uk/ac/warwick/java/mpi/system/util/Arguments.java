package uk.ac.warwick.java.mpi.system.util;

import java.io.File;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

/**
 * Class to encapsulate the processing of arguments.
 *
 * Created: 11/10/10
 *
 * @author
 * @version
 * @since
 */
public class Arguments {
  private static OptionParser parser = new OptionParser();
  private static OptionSpec<File> hostsArg_;
  private static OptionSpec<Integer> nArg_;
  private static OptionSpec<String> userClassArg_;
  private static OptionSpec<Integer> rankArg_;
  private static OptionSpec<String> hostnameArg_;
  private static OptionSpec<String> argsArg_;
  private static OptionSpec<String> sshArg_;

  private File hostfile_;
  private int n_;
  private String class_;
  private int rank_;
  private String hostname_;
  private String[] args_;
  private String ssh_id_ = null;

  /**
   * Generate the parser statically
   */
  static {
    File tempFile = new File("temp");

    hostsArg_ =
      parser.accepts("hostfile").withRequiredArg().ofType(File.class).defaultsTo( tempFile );
    nArg_ =
      parser.accepts("n").withRequiredArg().ofType(Integer.class);
    userClassArg_ =
      parser.accepts("className").withRequiredArg().ofType(String.class);
    rankArg_ =
      parser.accepts("rank").withRequiredArg().ofType(Integer.class);
    hostnameArg_ =
      parser.accepts("hostname").withRequiredArg().ofType(String.class);
    sshArg_ =
      parser.accepts("ssh").withRequiredArg().ofType(String.class);
  }

  /**
   * Parse the given argument <code>string[]</code>.
   *
   * <p> This would be something like the <code>args</code> passed to
   * a Java program when it is run from the commandline.
   *
   * @param arguments The array of arguments to be parsed.
   */
  public Arguments(String[] arguments) {
    OptionSet options = parser.parse(arguments);

    hostfile_ = options.valueOf(hostsArg_);
    if (options.has(nArg_)) n_ = options.valueOf(nArg_);
    if (options.has(userClassArg_)) class_ = options.valueOf(userClassArg_);
    if (options.has(rankArg_)) rank_ = options.valueOf(rankArg_);
    if (options.has(hostnameArg_)) hostname_ = options.valueOf(hostnameArg_);
    args_  = options.nonOptionArguments().toArray(new String[0]);
    if (options.has(sshArg_)) ssh_id_ = options.valueOf(sshArg_);
  }

  /**
   * Get the name of the class parsed by the constructor.
   *
   * @return The name of the class.
   */
  public String getClassname() {
    return class_;
  }

  /**
   * Get the <code>File</code> containing the hostnames.
   *
   * @return The <code>File</code> containing hosts.
   */
  public File getHostfile() {
    return hostfile_;
  }

  /**
   * Get the hostname parsed by the constructor.
   *
   * @return The hostname.
   */
  public String getHostname() {
    return hostname_;
  }

  /**
   * Get the total number of processes, <code>n</code>.
   *
   * @return The total number of processes.
   */
  public int getN() {
    return n_;
  }

  /**
   * Get the value of the rank argument.
   *
   * @return The rank argument.
   */
  public int getRank() {
    return rank_;
  }

  /**
   * Get the remaining values not corresponding to a flag, these will
   * be the user program arguments.
   *
   * @return The remaining arguments.
   */
  public String[] getArgs() {
    return args_;
  }

  /**
   * Return the location of the file the user wishes to use as there
   * ssh id for authentication on remote nodes.
   *
   * @return The location of the ssh id file as a <code>String</code>.
   */
  public String getSshId() {
    return ssh_id_;
  }
} // Arguments
