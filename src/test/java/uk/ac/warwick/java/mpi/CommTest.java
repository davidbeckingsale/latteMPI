package uk.ac.warwick.java.mpi;


import java.util.Arrays;
import junit.framework.TestSuite;
import org.easymock.EasyMock;
import org.junit.Test;
import uk.ac.warwick.java.mpi.Mpi;
import uk.ac.warwick.java.mpi.system.Message;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Unit tests for Comm class
 *
 * @author David Beckingsale
 * @version 1
 * @since 26/10/2010
 */
public class CommTest {

  /**
   * Tests the constructor initialises the method
   */
  @Test public void testConstructor() {
    Comm c = new Comm(10);
  }

  /**
   * Tests that groups are correctly assigned in the setter method.
   */
  @Test public void testGroupSize() {
    Group g = new Group(10);
    Comm c = new Comm(g);

    int commSize = c.Size();
    int groupSize = g.Size();

    assertEquals(commSize, groupSize);
  }

  /**
   * Checks that the Send method is called.
   */
  @Test public void testSendInt() {
    Group mockGroup = createMock(Group.class);

    int[] data = {1,2,3,4,5,6};

    mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Send(data,0,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  /**
   * Check that the Recv method correctly returns the correct
   * <code>Message</code>.
   */
  @Test public void testRecvInt() {
    Group mockGroup = createMock(Group.class);

    //    int[] data = {1,2,3,4,5,6};
    int[] data = new int[10000];

    for (int i = 1; i < 10000; i++) {
      data[i] = i;
    }

    int[] recv = new int[10000];

    System.arraycopy(data,0,recv,0,10000);

    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_INT);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);

    replay(mockGroup);
    Comm c = new Comm(mockGroup);

    int[] recvData = new int[10000];
    Status s = new Status();

    c.Recv(recvData,0,0,s);

    assertTrue(Arrays.equals(recvData, recv));

    assertEquals(s.getSource(),0);
    assertEquals(s.getTag(),0);
    assertEquals(s.getError(), Mpi.SUCCESS);

  }

  /**
   * Check that an MpiException is thrown when the receive array is
   * too small.
   */
  @Test public void testRecvIntException() {
    Group mockGroup = createMock(Group.class);

    int[] data = {1,2,3,4,5,6};
    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_INT);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    int[] recvData = new int[5];
    Status s = new Status();

    try {
      c.Recv(recvData,0,0,s);
      fail("Should have thrown a MpiException, lengths don't match ");
    } catch (MpiException e) {
      assertEquals(e.getMessage(), "Message lengths do not match!"); // This is expected
    }
  }


  /**
   * Checks that the Send method is called.
   */
  @Test public void testSendLong() {
    Group mockGroup = createMock(Group.class);

    long[] data = {100l,2l,3l,4l,5l,6l};

    mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Send(data,0,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }


  /**
   * Check that the Recv method correctly returns the correct
   * <code>Message</code>.
   */
  @Test public void testRecvLong() {
    Group mockGroup = createMock(Group.class);

    long[] data = {1l,2l,3l,4l,5l,6l};
    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_LONG);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    long[] recvData = new long[6];
    Status s = new Status();

    c.Recv(recvData,0,0,s);

    assertTrue(Arrays.equals(recvData, new long[] {1l,2l,3l,4l,5l,6l}));

    assertEquals(s.getSource(),0);
    assertEquals(s.getTag(),0);
    assertEquals(s.getError(), Mpi.SUCCESS);

  }


  /**
   * Check that an MpiException is thrown when the receive array is
   * too small.
   */
  @Test public void testRecvLongException() {
    Group mockGroup = createMock(Group.class);

    long[] data = {1l,2l,3l,4l,5l,6l};
    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_LONG);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    long[] recvData = new long[5];
    Status s = new Status();

    try {
      c.Recv(recvData,0,0,s);
      fail("Should have thrown a MpiException, lengths don't match ");
    } catch (MpiException e) {
      assertEquals(e.getMessage(), "Message lengths do not match!"); // This is expected
    }
  }


  /**
   * Checks that the Send method is called.
   */
  @Test public void testSendFloat() {
    Group mockGroup = createMock(Group.class);

    float[] data = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f};

    mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Send(data,0,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }


  /**
   * Check that the Recv method correctly returns the correct
   * <code>Message</code>.
   */
  @Test public void testRecvFloat() {
    Group mockGroup = createMock(Group.class);

    float[] data = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f};

    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_FLOAT);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    float[] recvData = new float[6];
    Status s = new Status();

    c.Recv(recvData,0,0,s);

    assertTrue(Arrays.equals(recvData, new float[] {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f}));

    assertEquals(s.getSource(),0);
    assertEquals(s.getTag(),0);
    assertEquals(s.getError(), Mpi.SUCCESS);

  }


  /**
   * Check that an MpiException is thrown when the receive array is
   * too small.
   */
  @Test public void testRecvFloatException() {
    Group mockGroup = createMock(Group.class);

    float[] data = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f};
    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_FLOAT);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    float[] recvData = new float[5];
    Status s = new Status();

    try {
      c.Recv(recvData,0,0,s);
      fail("Should have thrown a MpiException, lengths don't match ");
    } catch (MpiException e) {
      assertEquals(e.getMessage(), "Message lengths do not match!"); // This is expected
    }
  }


  /**
   * Checks that the Send method is called.
   */
  @Test public void testSendDouble() {
    Group mockGroup = createMock(Group.class);

    double[] data = {0.1,0.2,0.3,0.54,0.5,0.6};

    mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Send(data,0,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }


  /**
   * Check that the Recv method correctly returns the correct
   * <code>Message</code>.
   */
  @Test public void testRecvDouble() {
    Group mockGroup = createMock(Group.class);

    double[] data = {0.1,0.2,0.3,0.4,0.5,0.6};
    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_DOUBLE);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    double[] recvData = new double[6];
    Status s = new Status();

    c.Recv(recvData,0,0,s);

    assertTrue(Arrays.equals(recvData, new double[] {0.1,0.2,0.3,0.4,0.5,0.6}));

    assertEquals(s.getSource(),0);
    assertEquals(s.getTag(),0);
    assertEquals(s.getError(), Mpi.SUCCESS);

  }

  /**
   * Check that an MpiException is thrown when the receive array is
   * too small.
   */
  @Test public void testRecvDoubleException() {
    Group mockGroup = createMock(Group.class);

    double[] data = {0.1,0.2,0.3,0.4,0.5,0.6};
    Message testMessage = new Message(Message.dataToByteArray(data), 0,0, Message.TYPE_DOUBLE);


    expect(mockGroup.Recv(0,0)).andReturn(testMessage);
    replay(mockGroup);

    Comm c = new Comm(mockGroup);

    double[] recvData = new double[5];
    Status s = new Status();

    try {
      c.Recv(recvData,0,0,s);
      fail("Should have thrown a MpiException, lengths don't match ");
    } catch (MpiException e) {
      //assertEquals(e.getMessage(), "Message lengths do not match!"); // This is expected
    }
  }

  @Test public void testBcastInt0() {
    Group mockGroup = createMock(Group.class);

    int[] data = { 1,2,3,4,5,6,7,8,9 };

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(1);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastIntRecv() {
    Group mockGroup = createMock(Group.class);

    int[] data = { 1,2,3,4,5,6,7,8,9 };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_INT);

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Recv(0,Comm.BROADCAST_TAG)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastIntSend() {
    Group mockGroup = createMock(Group.class);

    int[] data = { 1,2,3,4,5,6,7,8,9 };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_INT);

    mockGroup.Send(anyObject(Message.class),eq(1));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  /**
   * Check that we can broadcast from an arbitrary root.
   */
  @Test public void testBcastIntSendNewRoot() {
    Group mockGroup = createMock(Group.class);

    int[] data = { 1,2,3,4,5,6,7,8,9 };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_INT);

    mockGroup.Send(anyObject(Message.class),eq(0));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,1);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }



  @Test public void testRankConversion() {
    int root = 7;
    int rank = 1;
    int size = 8;

    //System.out.println(Comm.originalRank(root, Comm.relativeRank(root,rank,size), size));
    //System.out.println(Comm.relativeRank(root, rank, size));
    assertEquals(rank, Comm.originalRank(root, Comm.relativeRank(root,rank,size), size));

  }



  @Test public void testBcastLong0() {
    Group mockGroup = createMock(Group.class);

    long[] data = { 10000L };

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(1);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastLongRecv() {
    Group mockGroup = createMock(Group.class);

    long[] data = { 60000000L, 10L };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_LONG);

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Recv(0,Comm.BROADCAST_TAG)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastLongSend() {
    Group mockGroup = createMock(Group.class);

    long[] data = { 103201312L, 321L };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_LONG);

    mockGroup.Send(anyObject(Message.class),eq(1));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  /**
   * Check that we can broadcast from an arbitrary root.
   */
  @Test public void testBcastLongSendNewRoot() {
    Group mockGroup = createMock(Group.class);

    long[] data = { 321312L, 21321L };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_LONG);

    mockGroup.Send(anyObject(Message.class),eq(0));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,1);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }



  @Test public void testBcastFloat0() {
    Group mockGroup = createMock(Group.class);

    float[] data = { 0.23f, 0.3213f, 4.31f};

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(1);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastFloatRecv() {
    Group mockGroup = createMock(Group.class);

    float[] data = { 0.54f, 1.432f, 1.4343f };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_FLOAT);

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Recv(0,Comm.BROADCAST_TAG)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastFloatSend() {
    Group mockGroup = createMock(Group.class);

    float[] data = { 3.14159f, 0.999f };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_FLOAT);

    mockGroup.Send(anyObject(Message.class),eq(1));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  /**
   * Check that we can broadcast from an arbitrary root.
   */
  @Test public void testBcastFloatSendNewRoot() {
    Group mockGroup = createMock(Group.class);

    float[] data = { 0.22f, 9.123f, 213.5655f };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_FLOAT);

    mockGroup.Send(anyObject(Message.class),eq(0));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,1);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }



  @Test public void testBcastDouble0() {
    Group mockGroup = createMock(Group.class);

    double[] data = { 41342.6432, 342.4321, 45345.3241};

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(1);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastDoubleRecv() {
    Group mockGroup = createMock(Group.class);

    double[] data = { 342134.6345,432.32 };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_DOUBLE);

    //mockGroup.Send(anyObject(Message.class),eq(0));
    expect(mockGroup.Recv(0,Comm.BROADCAST_TAG)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  @Test public void testBcastDoubleSend() {
    Group mockGroup = createMock(Group.class);

    double[] data = { 321.543,32.543,21312.23 };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_DOUBLE);

    mockGroup.Send(anyObject(Message.class),eq(1));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(0);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,0);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }

  /**
   * Check that we can broadcast from an arbitrary root.
   */
  @Test public void testBcastDoubleSendNewRoot() {
    Group mockGroup = createMock(Group.class);

    double[] data = { 1.3333,453.21 };
    Message test = new Message(Message.dataToByteArray(data),0,3,Message.TYPE_DOUBLE);

    mockGroup.Send(anyObject(Message.class),eq(0));
    //expect(mockGroup.Recv(0,3)).andReturn(test);
    expect(mockGroup.Rank()).andReturn(1);
    expect(mockGroup.Size()).andReturn(2);

    replay(mockGroup);

    Comm c = new Comm(mockGroup);
    c.Bcast(data,1);

    /** Check that the Send method of the group was called correctly */
    verify(mockGroup);
  }



  // TESTS FOR REDUCE
  @Test  public void testIntReduceRoot() {
    Group mockGroup = createMock( Group.class );

    int[] dataRoot = { 1232, 3123};
    int[] dataNotRoot = { 123, 432 };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_INT);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);

    replay(mockGroup);

    int[] out = new int[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataRoot, out, Mpi.SUM, 0);

    verify(mockGroup);
    assertArrayEquals(out, new int[] {1355, 3555});
  }

  @Test  public void testIntReduceOther() {
    Group mockGroup = createMock( Group.class );

    int[] dataRoot = { 1232, 3123};
    int[] dataNotRoot = { 123, 432 };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_INT);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));

    replay(mockGroup);

    int[] out = new int[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataNotRoot, out, Mpi.SUM, 0);

    verify(mockGroup);

           //    assertArrayEquals(out, new int[] {1355, 3555});
  }


  @Test  public void testLongReduceRoot() {
    Group mockGroup = createMock( Group.class );

    long[] dataRoot = { 1232L, 3123L};
    long[] dataNotRoot = { 123L, 432L };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_LONG);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);

    replay(mockGroup);

    long[] out = new long[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataRoot, out, Mpi.SUM, 0);

    verify(mockGroup);
    assertArrayEquals(out, new long[] {1355L, 3555L});
  }

  @Test  public void testLongReduceOther() {
    Group mockGroup = createMock( Group.class );

    long[] dataRoot = { 1232L, 3123L};
    long[] dataNotRoot = { 123L, 432L };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_LONG);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));

    replay(mockGroup);

    long[] out = new long[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataNotRoot, out, Mpi.SUM, 0);

    verify(mockGroup);

  }


  @Test  public void testFloatReduceRoot() {
    Group mockGroup = createMock( Group.class );

    float[] dataRoot = { 1.1f, 2.2f};
    float[] dataNotRoot = { 12.3f, 43.2f };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_FLOAT);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);

    replay(mockGroup);

    float[] out = new float[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataRoot, out, Mpi.SUM, 0);

    verify(mockGroup);
    assertArrayEquals(out, new float[] {13.4f, 45.4f}, 0.01f);
  }

  @Test  public void testFloatReduceOther() {
    Group mockGroup = createMock( Group.class );

    float[] dataRoot = { 12.32f, 312.3f};
    float[] dataNotRoot = { 123.0f, 432.1f };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_FLOAT);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));

    replay(mockGroup);

    float[] out = new float[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataNotRoot, out, Mpi.SUM, 0);

    verify(mockGroup);
  }

  @Test  public void testDoubleReduceRoot() {
    Group mockGroup = createMock( Group.class );

    double[] dataRoot = { 12.32, 312.3};
    double[] dataNotRoot = { 12.3, 43.2 };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_DOUBLE);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);

    replay(mockGroup);

    double[] out = new double[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataRoot, out, Mpi.SUM, 0);

    verify(mockGroup);
    assertArrayEquals(out, new double[] {24.62, 355.5}, 0.01);
  }

  @Test  public void testDoubleReduceOther() {
    Group mockGroup = createMock( Group.class );

    double[] dataRoot = { 1232, 3123};
    double[] dataNotRoot = { 123, 432 };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_DOUBLE);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));

    replay(mockGroup);

    double[] out = new double[2];

    Comm c = new Comm(mockGroup);

    c.Reduce(dataNotRoot, out, Mpi.SUM, 0);

    verify(mockGroup);

  }

  // START OF ALLREDUCE TESTS
  @Test  public void testIntAllReduceRoot() {
    Group mockGroup = createMock( Group.class );

    int[] dataRoot = { 1232, 3123};
    int[] dataNotRoot = { 123, 432 };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_INT);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);
    mockGroup.Send(anyObject(Message.class), eq(1));

    replay(mockGroup);

    int[] out = new int[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new int[] {1355, 3555});
  }

  @Test  public void testIntAllReduceOther() {
    Group mockGroup = createMock( Group.class );

    int[] dataRoot = { 1232, 3123};
    int[] dataNotRoot = { 123, 432 };
    Message test = new Message(Message.dataToByteArray(dataRoot), 1, Comm.REDUCE_TAG, Message.TYPE_INT);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));
    expect(mockGroup.Recv(0, Comm.BROADCAST_TAG))
      .andReturn(new Message(Message.dataToByteArray(new int[] {1355, 3555}), 0, Comm.BROADCAST_TAG, Message.TYPE_INT));

    replay(mockGroup);

    int[] out = new int[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataNotRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new int[] {1355, 3555});
  }


  @Test  public void testLongAllReduceRoot() {
    Group mockGroup = createMock( Group.class );

    long[] dataRoot = { 1232L, 3123L};
    long[] dataNotRoot = { 123L, 432L };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_LONG);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);
    mockGroup.Send(anyObject(Message.class), eq(1));

    replay(mockGroup);

    long[] out = new long[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new long[] {1355L, 3555L});
  }

  @Test  public void testLongAllReduceOther() {
    Group mockGroup = createMock( Group.class );

    long[] dataRoot = { 1232L, 3123L};
    long[] dataNotRoot = { 123L, 432L };
    Message test = new Message(Message.dataToByteArray(dataRoot), 1, Comm.REDUCE_TAG, Message.TYPE_LONG);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));
    expect(mockGroup.Recv(0, Comm.BROADCAST_TAG))
      .andReturn(new Message(Message.dataToByteArray(new long[] {1355, 3555}), 0, Comm.BROADCAST_TAG, Message.TYPE_LONG));

    replay(mockGroup);

    long[] out = new long[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataNotRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new long[] {1355L, 3555L});
  }

  @Test  public void testFloatAllReduceRoot() {
    Group mockGroup = createMock( Group.class );

    float[] dataRoot = { 1.232f, 31.23f};
    float[] dataNotRoot = { 1.23f, 4.32f };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_FLOAT);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);
    mockGroup.Send(anyObject(Message.class), eq(1));

    replay(mockGroup);

    float[] out = new float[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new float[] {2.462f, 35.55f}, 0.01f);
  }

  @Test  public void testFloatAllReduceOther() {
    Group mockGroup = createMock( Group.class );

    float[] dataRoot = { 1.232f, 31.23f};
    float[] dataNotRoot = { 1.23f, 4.32f};
    Message test = new Message(Message.dataToByteArray(dataRoot), 1, Comm.REDUCE_TAG, Message.TYPE_FLOAT);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));
    expect(mockGroup.Recv(0, Comm.BROADCAST_TAG))
      .andReturn(new Message(Message.dataToByteArray(new float[] {2.462f, 35.55f}), 0, Comm.BROADCAST_TAG, Message.TYPE_FLOAT));

    replay(mockGroup);

    float[] out = new float[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataNotRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new float[] {2.462f, 35.55f}, 0.01f);
  }


  @Test  public void testDoubleAllReduceRoot() {
    Group mockGroup = createMock( Group.class );

    double[] dataRoot = { 12.32, 312.3};
    double[] dataNotRoot = { 1.23, 43.2 };
    Message test = new Message(Message.dataToByteArray(dataNotRoot), 1, Comm.REDUCE_TAG, Message.TYPE_DOUBLE);

    expect(mockGroup.Rank()).andReturn(0).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    expect(mockGroup.Recv(1, Comm.REDUCE_TAG)).andReturn(test);
    mockGroup.Send(anyObject(Message.class), eq(1));

    replay(mockGroup);

    double[] out = new double[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new double[] {13.55, 355.5}, 0.01);
  }

  @Test  public void testDoubleAllReduceOther() {
    Group mockGroup = createMock( Group.class );

    double[] dataRoot = { 12.32, 31.23};
    double[] dataNotRoot = { 1.23, 4.32 };
    Message test = new Message(Message.dataToByteArray(dataRoot), 1, Comm.REDUCE_TAG, Message.TYPE_DOUBLE);

    expect(mockGroup.Rank()).andReturn(1).anyTimes();
    expect(mockGroup.Size()).andReturn(2).anyTimes();
    mockGroup.Send(anyObject(Message.class), eq(0));
    expect(mockGroup.Recv(0, Comm.BROADCAST_TAG))
      .andReturn(new Message(Message.dataToByteArray(new double[] {13.55, 35.55}), 0, Comm.BROADCAST_TAG, Message.TYPE_DOUBLE));

    replay(mockGroup);

    double[] out = new double[2];

    Comm c = new Comm(mockGroup);

    c.AllReduce(dataNotRoot, out, Mpi.SUM);

    verify(mockGroup);

    assertArrayEquals(out, new double[] {13.55, 35.55}, 0.01);
  }

}
