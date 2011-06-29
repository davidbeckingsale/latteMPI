package uk.ac.warwick.java.mpi.system;

import org.junit.Test;
import static org.junit.Assert.*;


import uk.ac.warwick.java.mpi.Mpi;

/**
 * Unit tests for MessageQueue class
 *
 * @author David Beckingsale
 * @version 1
 * @since 26/10/2010
 */
public class MessageQueueTest{
  /**
   * Tests a simple insertion of a message, and a corresponding get.
   */
  @Test public void testInsert() {
    MessageQueue queue = new MessageQueue();

    Message m = new Message(Message.dataToByteArray(new int[] {1,2,3,4,5,6,7,8}), 0, 0, Message.TYPE_INT);

    queue.add(m);

    assertEquals(queue.get(0,0), m);
  }

  /**
   * Tests a get using <code>ANY_SOURCE</code>.
   */
  @Test public void testGet() {
    MessageQueue q = new MessageQueue();

    Message m = new Message(Message.dataToByteArray(new int[] {1,2,3,4,5,6,7,8}), 0, 0, Message.TYPE_INT);

    q.add(m);

    assertEquals(q.get(Mpi.ANY_SOURCE,0), m);

  }

  /**
   * Tests a get using both <code>ANY_SOURCE</code> and <code>ANY_TAG</code>.
   */
  @Test public void testGetAny() {
    MessageQueue q = new MessageQueue();

    Message m = new Message(Message.dataToByteArray(new int[] {1,2,3,4,5,6,7,8}), 0, 0, Message.TYPE_INT);

    q.add(m);
    assertEquals(q.get(Mpi.ANY_SOURCE, Mpi.ANY_TAG), m);
  }

  /**
   * Tests a get using <code>ANY_TAG</code>.
   */
  @Test public void testGetAnyTag() {
    MessageQueue q = new MessageQueue();

    Message m = new Message(Message.dataToByteArray(new int[] {1,2,3,4,5,6,7,8}), 0, 0, Message.TYPE_INT);

    q.add(m);
    assertEquals(q.get(0,Mpi.ANY_TAG), m);
  }

  /**
   * Tests that <code>null</code> is returned when trying to get a message that isn't there.
   */
  @Test public void testNullReturn() {
    MessageQueue q = new MessageQueue();

    Message m = new Message(Message.dataToByteArray(new int[] {1,2,3,4,5,6,7,8}), 0, 0, Message.TYPE_INT);

    q.add(m);
    assertEquals(q.get(1,Mpi.ANY_TAG), null);
    assertEquals(q.get(Mpi.ANY_SOURCE,1), null);
  }

  /**
   * Tests the <code>equals</code> method of the <code>MessageKeyTuple</code> object.
   *
   *<p> The equals method matches the source and the tag if the object
   *is a <code>MessageKeyTuple</code>, otherwise it returns false.
   */
  @Test public void testTupleEquals() {
    MessageQueue q = new MessageQueue();

    MessageQueue.MessageKeyTuple tuple = q.new MessageKeyTuple(0,1);
    assertFalse(tuple.equals(new int[] {5,4,3}));
    assertFalse(tuple.equals(q.new MessageKeyTuple(1,1)));
    assertFalse(tuple.equals(q.new MessageKeyTuple(0,0)));
    assertFalse(tuple.equals(q.new MessageKeyTuple(1,0)));

    assertTrue(tuple.equals(q.new MessageKeyTuple(0,1)));
  }
}
