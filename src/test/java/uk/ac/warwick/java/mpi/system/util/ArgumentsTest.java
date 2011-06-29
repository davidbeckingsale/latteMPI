package uk.ac.warwick.java.mpi.system.util;



import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;



/**
 * Tests the Message class
 *
 * Created: 11/03/10
 *
 * @author
 * @version
 * @since
 */
public class ArgumentsTest  {
  @Test public void testConstructor() {
    String[] arguments = {"-hostfile","test.txt","-n","8","-className","myApp","-rank","0","-hostname","localhost"};
    Arguments parser = new Arguments(arguments);

    assertEquals(new File("test.txt"), parser.getHostfile());
    assertEquals(8, parser.getN());
    assertEquals("myApp", parser.getClassname());
    assertEquals(0,parser.getRank());
    assertEquals("localhost", parser.getHostname());
  }

  @Test public void testDefaultValues() {
    String[] arguments = {"-hostfile","test.txt","-n","8","-className","myApp"};
    Arguments parser = new Arguments(arguments);


    assertEquals(new File("test.txt"), parser.getHostfile());
    assertEquals(8, parser.getN());
    assertEquals("myApp", parser.getClassname());
  }

  @Test public void testDefaultFile() {
    String[] arguments =  {"-n","8","-className","myApp","-rank","0","-hostname","localhost"};
    Arguments parser = new Arguments(arguments);

    assertEquals(new File("temp"), parser.getHostfile());
  }

  @Test public void testSshId() {
    String[] arguments = {"-ssh", "my_id"};
    Arguments parser  = new Arguments(arguments);

    assertEquals("my_id", parser.getSshId());
  }

  @Test public void testArgs() {
    String[] arguments = {"--", "one", "two", "three"};
    Arguments parser = new Arguments(arguments);

    //System.out.println(java.util.Arrays.toString(parser.getArgs()));

    assertTrue(java.util.Arrays.equals(parser.getArgs(), new String[] {"one","two", "three"}));
  }
}// ArgumentsTest
