package uk.ac.warwick.java.mpi.system;



import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

import uk.ac.warwick.java.mpi.Mpi;


/**
 * Tests the Message class
 *
 * Created: 11/03/10
 *
 * @author
 * @version
 * @since
 */
public class MessageTest  {
  /**
   * Check that a message of type <code>Integer</code> can be
   * constructed, and the type inferred correctly.
   */
  @Test public void testConstructor() {
    int[] testData = {1,2,3,4,5};

    Message m = new Message(Message.dataToByteArray(testData), 1, 1, Message.TYPE_INT);

    byte[] messageArray = m.toByteArray();

    Message mp = new Message(messageArray);
  }

  /**
   * Check that the <code>int 1024</code> gets correctly converted.
   */
  @Test public void testIntToByte1024() {
    int test = 1024;
    byte[] testByte = Message.makeByte4FromInt(test);
    int testByteInt = Message.makeIntFromByte4(testByte);

    assertEquals(1024, testByteInt);
  }

  /**
   * Check <code>0</code> gets correctly converted
   */
  @Test public void testIntToByte0() {
    int test = 0;
    byte[] testByte = Message.makeByte4FromInt(test);
    int testByteInt = Message.makeIntFromByte4(testByte);

    assertEquals(0, testByteInt);
  }

  /**
   * Check <code>Integer.MAX_VALUE</code> gets correctly converted.
   */
  @Test public void testIntToByteMax() {
    int test =  Integer.MAX_VALUE;
    byte[] testByte = Message.makeByte4FromInt(test);
    int testByteInt = Message.makeIntFromByte4(testByte);

    assertEquals(Integer.MAX_VALUE, testByteInt);
  }

  /**
   * Tests that a <code>byte[4]</code> containg <code>{0,0,4,0} (1024)</code> gets correctly
   * converted.
   */
  @Test public void testIntFromByte() {
    byte[] test = new byte[]{0,0,4,0};
    int testInt = Message.makeIntFromByte4(test);
    assertEquals(1024, testInt);
  }


  /**
   * Checks <code>262144L</code> gets correctly converted.
   */
  @Test public void testLongToByte262144() {
    long test = 262144L;
    byte[] testByte = Message.makeByte8FromLong(test);
    long testByteLong = Message.makeLongFromByte8(testByte);

    assertEquals(262144L, testByteLong);
  }

  /**
   * Checks <code>0L</code> gets correctly converted.
   */
  @Test public void testLongToByte0() {
    long test = 0L;
    byte[] testByte = Message.makeByte8FromLong(test);
    long testByteToLong = Message.makeLongFromByte8(testByte);

    assertEquals(testByteToLong, 0L);
  }

  /**
   * Tests that the value <code>Long.MAX_VALUE</code> gets correctly
   * converted to a <code>byte[]</code> and back.
   */
  @Test public void testLongFromByteMax() {
    byte[] test = Message.makeByte8FromLong(Long.MAX_VALUE);
    long testLong = Message.makeLongFromByte8(test);

    assertEquals(Long.MAX_VALUE, testLong);
  }

  /**
   * Checks <code>float</code> value <code>3.14159</code> gets
   * correctly converted to a <code>byte[]</code> and back.
   */
  @Test public void testFloatToByte() {
    float floatTest = 3.14159F;
    byte[] test = Message.makeByte4FromFloat(floatTest);
    float testFloat = Message.makeFloatFromByte4(test);

    assertEquals(testFloat, 3.14159F, 0.1);
  }

  /**
   * Checks <code>float</code> value <code>Float.MAX_VALUE</code> gets
   * correctly converted to a <code>byte[]</code> and back.
   */
  @Test public void testFloatToByteMax() {
    float floatTest = Float.MAX_VALUE;
    byte[] test = Message.makeByte4FromFloat(floatTest);
    float testFloat = Message.makeFloatFromByte4(test);

    assertEquals(testFloat, Float.MAX_VALUE, 0.1);
  }

  /**
   * Checks <code>double</code> value <code> 34321541432.43215</code>
   * gets correctly converted to a <code>byte[]</code> and back.
   */
  @Test public void testDoubleToByte() {
    double doubleTest = 34321541432.43215;
    byte[] test = Message.makeByte8FromDouble(doubleTest);
    double testDouble = Message.makeDoubleFromByte8(test);

    assertEquals(doubleTest, testDouble, 0.1);
  }

  /**
   * Checks <code>double</code> value <code>Double.MAX_VALUE</code> gets
   * correctly converted to a <code>byte[]</code> and back.
   */
  @Test public void testDoubleMax() {
    byte[] test = Message.makeByte8FromDouble(Double.MAX_VALUE);
    double testDouble = Message.makeDoubleFromByte8(test);

    assertEquals(Double.MAX_VALUE, testDouble, 0.1);
  }

  /**
   * Tests concatenation of 4 arrays.
   */
  @Test public void testConcat() {
    byte[] testArray1 = {1,2,3,4,5,6,7,8};
    byte[] testArray2 = {1,2,3,4,5,6,7,8};
    byte[] testArray3 = {1,2,3,4,5,6,7,8};
    byte[] testArray4 = {1,2,3,4,5,6,7,8};

    byte[] concatArrays = Message.concat(testArray1, testArray2, testArray3, testArray4);

    assertTrue(Arrays.equals(concatArrays, new byte[] {1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,8}));
  }

  /**
   * Tests concatenation of 4 mixed length arrays.
   */
  @Test public void testConcatMixed() {
    byte[] testArray1 = {1,2,3,4,5,6,7,8};
    byte[] testArray2 = {1,2,3,4,5,6,7};
    byte[] testArray3 = {1,2,3,4,5,6};
    byte[] testArray4 = {1,2,3,4,5};

    byte[] concatArrays = Message.concat(testArray1, testArray2, testArray3, testArray4);

    assertTrue(Arrays.equals(concatArrays, new byte[] {1,2,3,4,5,6,7,8,1,2,3,4,5,6,7,1,2,3,4,5,6,1,2,3,4,5}));
  }

  /**
   * Tests concatenation when an empty array is found.
   */
  @Test public void testConcatEmpty() {
    byte[] testArray1 = {1,2,3,4,5};
    byte[] testArray2 = {};
    byte[] testArray3 = {1,2,3,4,5};

    byte[] concat = Message.concat(testArray1,testArray2,testArray3);

    assertTrue(Arrays.equals(concat, new byte[] {1,2,3,4,5,1,2,3,4,5}));
  }

  /**
   * Check that an <code>int[]</code> is converted to the correct
   * <code>byte[]</code>.
   */
  @Test public void testDataToByteArrayInt() {
    int[] test = {1,2,3,4,5};

    byte[] testByte = Message.dataToByteArray(test);

    assertEquals(testByte.length, 20);

  }

  /**
   * Check that an int array is correctly converted to and from a
   * <code>byte[]</code>.
   */
  @Test public void testDataToPrimitive() {
    int[] test = {1,2,3,4,5,6,7,8};

    byte[] testByte = Message.dataToByteArray(test);

    Message m = new Message(testByte,1,1,Message.TYPE_INT);

    int[] back = m.dataToPrimitiveInt();

    assertTrue(Arrays.equals(test, back));
  }

  /**
   * Test that a <code>long[]</code> can be correctly converted to a
   * <code>byte[]</code>.
   */
  @Test public void testLongDataToByteArrayLong() {
    long[] test = {1000000000,200000000,300000000,400000000,50000000};

    byte[] testByte = Message.dataToByteArray(test);

    assertEquals(testByte.length, 40);

  }

  /**
   * Check that a <code>long[]</code> can be converted to a
   * <code>byte[]</code> and back.
   */
  @Test public void testLongDataToPrimitive() {
    long[] test = {1l,2l,3l,4l,5l,6l,7l,8l};

    byte[] testByte = Message.dataToByteArray(test);

    Message m = new Message(testByte,1,1,Message.TYPE_LONG);

    long[] back = m.dataToPrimitiveLong();
    assertTrue(Arrays.equals(test, back));
  }

  /**
   * Check that a <code>float[]</code> can be correctly converted to a
   * <code>byte[]</code>.
   */
  @Test public void testFloatDataToByteArrayFloat() {
    float[] test = {3.14f,2.11f,0,2.45f,0.55f};

    byte[] testByte = Message.dataToByteArray(test);

    assertEquals(testByte.length, 20);
  }

  /**
   * Check that a <code>float[]</code> can be correctly converted to a
   * <code>byte[]</code> and back.
   */
  @Test public void testFloatDataToPrimitive() {
    float[] test = {0.1f,0.2f,0.3f,0.4f,0.5f,0.6f,0.7f,0.8f};

    byte[] testByte = Message.dataToByteArray(test);

    Message m = new Message(testByte,1,1,Message.TYPE_FLOAT);

    float[] back = m.dataToPrimitiveFloat();

    assertTrue(Arrays.equals(test, back));
  }

  /**
   * Check that a <code>double[]</code> can be correctly converted to
   * a <code>byte[]</code>.
   */
  @Test public void testDoubleDataToByteArrayDouble() {
    double[] test = {1000000000.12,2000.45,300000000.22,400000000.14159,50000000.8};

    byte[] testByte = Message.dataToByteArray(test);

    assertEquals(testByte.length, 40);

  }

  /**
   * Check that a <code>double[]</code> can be correctly converted to
   * a <code>byte[]</code> and back.
   */
  @Test public void testDoubleDataToPrimitive() {
    double[] test = {1.5478151,2.67435,3.7533,4.4321,5.43124,6.646342,7.3215622,8000.12345};

    byte[] testByte = Message.dataToByteArray(test);

    Message m = new Message(testByte,1,1,Message.TYPE_DOUBLE);

    double[] back = m.dataToPrimitiveDouble();

    assertTrue(Arrays.equals(test, back));
  }

  /**
   * Tests that the message is correctly converted to a <code>String</code>.
   */
  @Test public void testToString() {
    Message m = new Message(Message.dataToByteArray(new int[]  {1,2,3}),1,1,Message.TYPE_INT);

    assertEquals(m.toString(),"Message: tag = 1, type = 1, data: [0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3]");
  }

  /**
   * Tests the type of the <code>Message</code> is correctly set.
   */
  @Test public void testGetType() {
    Message mi = new Message(Message.dataToByteArray(new int[]  {1,2,3}),1,1,Message.TYPE_INT);
    Message ml = new Message(Message.dataToByteArray(new long[]  {1l,2l,3l}),1,1,Message.TYPE_LONG);
    Message mf = new Message(Message.dataToByteArray(new float[]  {1f,2f,3.14159f}),1,1,Message.TYPE_FLOAT);
    Message md = new Message(Message.dataToByteArray(new double[]  {1.00,2.232,3.11}),1,1,Message.TYPE_DOUBLE);

    assertEquals(mi.getType(), Message.TYPE_INT);
    assertEquals(ml.getType(), Message.TYPE_LONG);
    assertEquals(mf.getType(), Message.TYPE_FLOAT);
    assertEquals(md.getType(), Message.TYPE_DOUBLE);
  }

  @Test public void timeConvert() {
    for (int i = 8; i < 16777217; i = i*2) {
      double[] test = new double[i];

      for (int j = 0; j < i; j++) {
        test[j] = j;
      }

      double start = Mpi.Wtime();
      byte[] test_byte = Message.dataToByteArray(test);
      double end = Mpi.Wtime();

      System.out.printf("Size: %d, time: %f\n", i, end - start);
    }
  }
}// MessageTest
