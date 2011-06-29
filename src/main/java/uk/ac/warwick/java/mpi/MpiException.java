package uk.ac.warwick.java.mpi;

/**
 * The MpiException is thrown, rather than returning error codes from
 * functions. If no error is thrown then a method can be assumed to
 * have run successfully.
 *
 * <p> All MpiExceptions will be created with an error message detailing
 * what caused the error to enable debugging.
 *
 * <p> The MpiException extends RuntimeException, so it is not
 * essential to catch these Exceptions unless writing fault tolerant
 * programs.
 *
 * @author David Beckingsale
 * @version 1
 * @since 2/2/2011
 */
public class MpiException extends RuntimeException  {
  public MpiException() {
    super();
  }

  public MpiException(String message) {
    super(message);
  }
} // MpiException
