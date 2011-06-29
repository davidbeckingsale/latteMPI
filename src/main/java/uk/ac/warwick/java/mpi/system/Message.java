package uk.ac.warwick.java.mpi.system;


import java.util.Arrays;


/**
 * Represents a locally stored message. For transmission across the
 * network, it will be converted to a byte array.
 *
 *
 * @author David Beckingsale
 * @version 1
 * @since 04/11/2010
 */
public class Message{

  /** Constant representing a message type of <code>Integer</code>. */
  public static final int TYPE_INT = 1;
  /** Constant representing a message type of <code>Long</code>. */
  public static final int TYPE_LONG = 2;
  /** Constant representing a message type of <code>Float</code>. */
  public static final int TYPE_FLOAT = 4;
  /** Constant representing a message type of <code>Double</code>. */
  public static final int TYPE_DOUBLE = 8;

  private int source_;
  private int tag_;
  private int type_;
  private byte[] messageData_;

  /**
   * Creates a message from the Object array, the source and the
   * message tag.
   */
  public Message(byte[] message, int source, int tag, int type) {

    messageData_ = message;
    source_ = source;
    tag_ = tag;
    type_ = type;
  }

  /**
   * Method that reads in the formatted byte array representing the
   * message, and constructs a message object from it.
   *
   * @param messageArray The byte array containing the message.
   */
  public Message(byte[] messageArray) {
    type_ = makeIntFromByte4(new byte[] {messageArray[0], messageArray[1], messageArray[2], messageArray[3]});
    tag_ = makeIntFromByte4(new byte[] {messageArray[4], messageArray[5], messageArray[6], messageArray[7]});
    source_ = makeIntFromByte4(new byte[] {messageArray[8], messageArray[9], messageArray[10], messageArray[11]});

    messageData_ = new byte[messageArray.length - 12];

    System.arraycopy(messageArray, 12, messageData_, 0, messageArray.length-12);

  }

  /**
   * Returns the type of this <code>message</code>.
   *
   * @return The <code>int</code> representing the message type.
   *
   * @see Message#TYPE_INT
   * @see Message#TYPE_LONG
   * @see Message#TYPE_FLOAT
   * @see Message#TYPE_DOUBLE
   */
  public int getType() {
    return type_;
  }

  /**
   * Return the tag of this <code>Message</code>.
   *
   * @return The <code>int</code> tag of the message.
   *
   * @see
   */
  public int getTag() {
    return tag_;
  }

  /**
   * Returns the rank of the source of this <code>Message</code>.
   *
   * @return The <code>int</code> source of the message.
   */
  public int getSource() {
    return source_;
  }

  /**
   * Update the source of a <code>Message</code>.
   *
   * @param s The rank of the source.
   */
  public void setSource(int s) {
    source_ = s;
  }

  /**
   * Outputs the <code>Message</code> as a byte array
   *
   * @return The contents of this message as a formatted byte array.
   */
  public byte[] toByteArray() {
    byte[] typeByte = makeByte4FromInt(type_);
    byte[] tagByte = makeByte4FromInt(tag_);
    byte[] sourceByte = makeByte4FromInt(source_);

    return concat(typeByte,tagByte,sourceByte,messageData_);

  }

  /**
   * Converts a <code>byte[4]</code> into an <code>int</code>.
   *
   * @param b The array to convert.
   *
   * @return The <code>int</code> represented by the array.
   */
	protected static int makeIntFromByte4(byte[] b) {
		return (b[0]&0xff)<<24 | (b[1]&0xff)<<16 | (b[2]&0xff)<<8 | (b[3]&0xff);
	}

  /**
   * Convert an <code>int</code> into a <code>byte[4]</code>.
   *
   * @param i The <code>int</code> to be converted.
   *
   * @return The <code>int</code> as a <code>byte[4]</code>.
   */
	protected static byte[] makeByte4FromInt(int i) {
		return new byte[] { (byte)(i>>24), (byte)(i>>16), (byte)(i>>8), (byte)i };
	}

  /**
   * Convert a <code>byte[8]</code> to a <code>long</code>.
   *
   * @param b The array to convert.
   *
   * @return The <code>long</code> represented by the array.
   */
  protected static long makeLongFromByte8(byte[] b) {
    return (long)b[0]<<56 | (long)(b[1]&0xff)<<48 | (long)(b[2]&0xff)<<40 |
      (long)(b[3]&0xff)<<32 | (long)(b[4]&0xff)<<24 | (long)(b[5]&0xff)<<16 |
      (long)(b[6]&0xff)<<8 | (long)(b[7]&0xff);
  }

  /**
   * Convert a <code>long</code> into a <code>byte[8]</code>.
   *
   * @param l The <code>long</code> to be converted.
   *
   * @return The <code>long</code> as a <code>byte[8]</code>.
   */
  protected static byte[] makeByte8FromLong(long l) {
    return new byte[] { (byte)(l>>56), (byte)(l>>48), (byte)(l>>40), (byte)(l>>32),
                        (byte)(l>>24), (byte)(l>>16), (byte)(l>>8), (byte)(l) };
  }

  /**
   * Convert a <code>byte[8]</code> to a <code>double</code>.
   *
   * @param b The array to convert.
   *
   * @return The <code>double</code> represented by the array.
   */
  protected static double makeDoubleFromByte8(byte[] b) {
    return Double.longBitsToDouble(makeLongFromByte8(b));
  }

  /**
   * Convert a <code>double</code> into a <code>byte[8]</code>.
   *
   * @param d The <code>double</code> to be converted.
   *
   * @return The <code>double</code> as a <code>byte[8]</code>.
   */
  protected static byte[] makeByte8FromDouble(double d) {
    long doubleAsLong = Double.doubleToLongBits(d);
    return makeByte8FromLong(doubleAsLong);
  }

  /**
   * Convert a <code>byte[4]</code> to a <code>float</code>.
   *
   * @param b The array to convert.
   *
   * @return The <code>float</code> represented by the array.
   */
  protected static float makeFloatFromByte4(byte[] b) {
    return Float.intBitsToFloat(makeIntFromByte4(b));
  }

  /**
   * Convert a <code>float</code> into a <code>byte[4]</code>.
   *
   * @param f The <code>float</code> to be converted.
   *
   * @return The <code>float</code> as a <code>byte[4]</code>.
   */
  protected static byte[] makeByte4FromFloat(float f) {
    int floatAsInt = Float.floatToIntBits(f);
    return makeByte4FromInt(floatAsInt);
  }

  /**
   * Concatenates <code>n</code> <code>byte[]</code> arrays into one
   * longer array.
   *
   * @param arrays The arrays to be converted.
   *
   *
   * @return The <code>byte[]</code> representing all the arrays concatenated.
   */
  protected static byte[] concat(byte[]...arrays) {

    int size = 0;
    for (byte[] b : arrays) size += b.length;
    byte[] result = new byte[size];
    int pos = 0;

    for (byte[] b : arrays) {
      System.arraycopy(b, 0, result, pos, b.length);
      pos += b.length;
    }

    return result;
  }

  /**
   * Converts the <code>int</code> array to a <code>byte</code> array.
   *
   * @param data The array to convert.
   */
  public static byte[] dataToByteArray(int[] data){
    byte[] result = new byte[4*data.length];

    for (int i = 0; i< data.length; i++) {
      System.arraycopy(makeByte4FromInt(data[i]), 0, result, 4*i, 4);
    }

    return result;
  }

  /**
   * Converts the <code>long</code> array to a <code>byte</code> array.
   *
   * @param data The array to convert.
   */
  public static byte[] dataToByteArray(long[] data) {
    byte[] result = new byte[8*data.length];

    for (int i = 0; i< data.length; i++) {
      System.arraycopy(makeByte8FromLong(data[i]), 0, result, 8*i, 8);
    }

    return result;
  }

  /**
   * Converts the <code>float</code> array to a <code>byte</code> array.
   *
   * @param data The array to convert.
   */
  public static byte[] dataToByteArray(float[] data) {
    byte[] result = new byte[4*data.length];

    for (int i = 0; i< data.length; i++) {
      System.arraycopy(makeByte4FromFloat(data[i]), 0, result, 4*i, 4);
    }

    return result;
  }

  /**
   * Converts the <code>double</code> array to a <code>byte</code> array.
   *
   * @param data The array to convert.
   */
  public static byte[] dataToByteArray(double[] data) {
    byte[] result = new byte[8*data.length];

    for (int i = 0; i< data.length; i++) {
      System.arraycopy(makeByte8FromDouble(data[i]), 0, result, 8*i, 8);
    }

    return result;
  }

  /**
   * Converts the data held as a byte array in this Message back into
   * its original form.
   *
   * @return The data array as an <code>int</code> array.
   */
  public int[] dataToPrimitiveInt() {
    int[] result = new int[messageData_.length/4];

    for (int i = 0; i < messageData_.length; i=i+4){
      result[i/4] = makeIntFromByte4(new byte[] {messageData_[i],messageData_[i+1], messageData_[i+2], messageData_[i+3]});
    }

    return result;
  }

  /**
   * Converts the data held as a byte array in this Message back into
   * its original form.
   *
   * @return The data array as an <code>long</code> array.
   */
  public long[] dataToPrimitiveLong() {
    long[] result = new long[messageData_.length/8];

    for (int i = 0; i < messageData_.length; i=i+8){
      result[i/8] = makeLongFromByte8(new byte[] {messageData_[i],messageData_[i+1], messageData_[i+2], messageData_[i+3],
                                                  messageData_[i+4], messageData_[i+5], messageData_[i+6],messageData_[i+7]});
    }

    return result;
  }

  /**
   * Converts the data held as a byte array in this Message back into
   * its original form.
   *
   * @return The data array as an <code>float</code> array.
   */
  public float[] dataToPrimitiveFloat() {
    float[] result = new float[messageData_.length/4];

    for (int i = 0; i < messageData_.length; i=i+4){
      result[i/4] = makeFloatFromByte4(new byte[] {messageData_[i],messageData_[i+1], messageData_[i+2], messageData_[i+3]});
    }

    return result;
  }

  /**
   * Converts the data held as a byte array in this Message back into
   * its original form.
   *
   * @return The data array as an <code>double</code> array.
   */
  public double[] dataToPrimitiveDouble() {
    double[] result = new double[messageData_.length/8];

    for (int i = 0; i < messageData_.length; i=i+8){
      result[i/8] = makeDoubleFromByte8(new byte[] {messageData_[i],messageData_[i+1], messageData_[i+2], messageData_[i+3],
                                                  messageData_[i+4], messageData_[i+5], messageData_[i+6],messageData_[i+7]});
    }

    return result;
  }

  /**
   * Return a human readble <code>String</code> representing this
   * <code>Message</code>.
   *
   * <p> Outputs the tag, type and the data contained in this
   * <code>Message</code>.
   *
   * @return The <code>String</code> representing this
   * <code>Message</code>.
   */
  @Override
  public String toString() {
    return "Message: tag = " + tag_ + ", type = " + type_ + ", data: " + Arrays.toString(messageData_);
  }
} // Message
