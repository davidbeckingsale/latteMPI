package uk.ac.warwick.java.mpi;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import static org.easymock.EasyMock.*;

import uk.ac.warwick.java.mpi.system.Processor;
import uk.ac.warwick.java.mpi.system.SimpleNetDevice;
import uk.ac.warwick.java.mpi.system.Message;

/**
 * Unit tests for Mpi class
 *
 * @author David Beckingsale
 * @version 1
 * @since 06/10/2010
 */
public class GroupTest{

  /**
   * Test the constructor runs properly
   */
  @Test public void testConstructor() {
    Group g = new Group(1);
  }

  /**
   * Check that Group.Size correctly reports the size of the Processor
   * arraylist.
   */
  public void NotestSize() {
    ArrayList<Processor> processors = new ArrayList<Processor>(20);

    for (int i = 0; i < 20; i++) {
      processors.add(new Processor("localhost" + i, 1, new SimpleNetDevice(1)));
    }

    Group testGroup = new Group(processors, new Processor("test", 1, new SimpleNetDevice(1)));

    assertEquals(testGroup.Size(), 20);
  }

  /**
   * Ensure that the processor passed as the local processor has the
   * correct rank within the group.
   */
  @Test public void testRank() {
    SimpleNetDevice mockDevice = createMock(SimpleNetDevice.class);

    // Create a new arraylist with capacity 10
    ArrayList<Processor> processors = new ArrayList<Processor>(10);

    // Add nine processors, these have ranks 0-8
    for (int i = 0; i < 9; i++) {
      processors.add(new Processor("localhost" + i, 1, mockDevice));
    }

    // Add a tenth processor with rank 9
    Processor p = new Processor("thishost", 1, mockDevice);
    processors.add(p);

    //Create the group
    Group g = new Group(processors, p);

    //Check that p has rank 9 in the group
    assertEquals(g.Rank(), 9);
    assertEquals(g.Size(), 10);
    assertEquals(g.getLocalProcessor(),p);
  }

  /**
   * Test that the group correctly sends a message.
   */
  @Test public void testSend() {
    SimpleNetDevice mockDevice = createMock(SimpleNetDevice.class);
    Message testMessage = new Message(Message.dataToByteArray(new double[] {0.1,0.2,0.3}), 0,0, Message.TYPE_DOUBLE);
    mockDevice.Send(testMessage, "localhost0", 1);
    replay(mockDevice);

    // Create a new arraylist with capacity 10
    ArrayList<Processor> processors = new ArrayList<Processor>(10);

    // Add nine processors, these have ranks 0-8
    for (int i = 0; i < 9; i++) {
      processors.add(new Processor("localhost" + i, 1, mockDevice));
    }

    // Add a tenth processor with rank 9
    Processor p = new Processor("thishost", 1, mockDevice);
    processors.add(p);

    //Create the group
    Group g = new Group(processors, p);

    g.Send(testMessage, 0);

    verify(mockDevice);
  }

  /**
   * Test that the group correctly receives a message.
   */
  @Test public void testRecv() {
    SimpleNetDevice mockDevice = createMock(SimpleNetDevice.class);
    Message testMessage = new Message(Message.dataToByteArray(new double[] {0.1,0.2,0.3}), 0,0, Message.TYPE_DOUBLE);
    expect(mockDevice.Receive(0,0)).andReturn(testMessage);
    replay(mockDevice);

    // Create a new arraylist with capacity 10
    ArrayList<Processor> processors = new ArrayList<Processor>(10);

    // Add nine processors, these have ranks 0-8
    for (int i = 0; i < 9; i++) {
      processors.add(new Processor("localhost" + i, 1, mockDevice));
    }

    // Add a tenth processor with rank 9
    Processor p = new Processor("thishost", 1, mockDevice);
    processors.add(p);

    //Create the group
    Group g = new Group(processors, p);

    Message m = g.Recv(0, 0);

    assertEquals(m, testMessage);

    verify(mockDevice);
  }
}
