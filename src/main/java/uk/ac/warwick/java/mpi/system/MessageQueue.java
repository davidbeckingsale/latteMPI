package uk.ac.warwick.java.mpi.system;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import uk.ac.warwick.java.mpi.Mpi;

import static uk.ac.warwick.java.mpi.Mpi.DEBUG_MODE;

/**
 * Holds all the messages related to the communicator.
 *
 * <p> Only local messages are held, that is, ones sent to the local
 * {@link uk.ac.warwick.java.mpi.system.Processor}.
 *
 * Created: 11/07/10
 *
 * @author David Beckingsale
 * @version 1
 * @since 7/11/2010
 */
public class MessageQueue {

  protected LinkedHashMap<MessageKeyTuple, LinkedList<Message>>  messageMap_;

  /**
   * Create a new, empty <code>MessageQueue</code>.
   */
  public MessageQueue() {
    if (DEBUG_MODE) {
      System.err.println("MessageQueue: \tCreating new LinkedHashMap");
    }
    messageMap_ = new LinkedHashMap<MessageKeyTuple, LinkedList<Message>>();
  }

  /**
   * Add a {@link uk.ac.warwick.java.mpi.system.Message} to the queue.
   *
   * @param message The <code>Message</code> to be added.
   */
  public synchronized void add(Message message) {
    if (DEBUG_MODE) {
      System.err.println("MessageQueue: \tAdding new message with tag:" + message.getTag() + "and source: " + message.getSource() );
    }
    // If there is no queue already associated with this key then we need to create one
    if (messageMap_.get(new MessageKeyTuple(message.getSource(), message.getTag())) == null) {
      LinkedList<Message> l = new LinkedList<Message>();
      l.add(message);
      messageMap_.put(new MessageKeyTuple(message.getSource(), message.getTag()), l);
    }
    else {
      messageMap_.get(new MessageKeyTuple(message.getSource(), message.getTag())).add(message);
    }
  }

  /**
   * Return the <code>Message</code> with the specified source and tag.
   *
   * @return <code>Message</code> with <code>source</code> and <code>tag</code>.
   */
  public synchronized Message get(int source, int tag) {
    if (DEBUG_MODE) {
      System.err.println("MessageQueue: \tSearching for message with tag: " + tag + " and source: " + source);
    }

    if (source != Mpi.ANY_SOURCE && tag != Mpi.ANY_TAG) {
      if (messageMap_.get(new MessageKeyTuple(source,tag)) == null) return null;
      else return messageMap_.get(new MessageKeyTuple(source, tag)).poll();
    }
    else if (source == Mpi.ANY_SOURCE && tag != Mpi.ANY_TAG) {
      if (messageMap_.isEmpty()) return null;
      Collection<LinkedList<Message>> listCollection = messageMap_.values();
      Iterator<LinkedList<Message>> listIterator = listCollection.iterator();
      LinkedList<Message> messageList;

      while(listIterator.hasNext()) {
        messageList = listIterator.next();
        if (messageList.peek() == null) continue;

        if (messageList.peek().getTag() == tag) {
          return messageList.poll();
        }
      }
      return null;
    }
    else if (source != Mpi.ANY_SOURCE && tag == Mpi.ANY_TAG) {
      if (messageMap_.isEmpty()) return null;
      Collection<LinkedList<Message>> listCollection = messageMap_.values();
      Iterator<LinkedList<Message>> listIterator = listCollection.iterator();
      LinkedList<Message> messageList;

      while(listIterator.hasNext()) {
        messageList = listIterator.next();

        if (messageList.peek() == null) continue;

        if (messageList.peek().getSource() == source) {
          return messageList.poll();
        }
      }
      return null;
    }
    else if (source == Mpi.ANY_SOURCE && tag == Mpi.ANY_TAG) {
      if (messageMap_.isEmpty()) return null;
      Collection<LinkedList<Message>> listCollection = messageMap_.values();
      Iterator<LinkedList<Message>> listIterator = listCollection.iterator();
      LinkedList<Message> messageList;

      while(listIterator.hasNext()) {
        messageList = listIterator.next();
        if (messageList.peek() == null) continue;
        if (messageList.peek() != null) return messageList.poll();
      }
      return null;
    }
    return null;
  }

  /**
   * Class representing a (source,tag) tuple that is used as a key in
   * the hashmap of <code>Message</code>s.
   */
  class MessageKeyTuple {
    private int source_;
    private int tag_;

    public MessageKeyTuple(int source, int tag) {
      source_ = source;
      tag_ = tag;
    }

    /**
     * Get the source of this tuple.
     *
     * @return The <code>int</code> source of this tuple.
     */
    public int getSource() {
      return source_;
    }

    /**
     * Get the tag of this tuple.
     *
     * @return The <code>int</code> tag of this tuple.
     */
    public int getTag() {
      return tag_;
    }

    /**
     * Check whether two objects are equal.
     *
     * <p> Two <code>MessageKeyTuple</code>s are equal if they have the same
     * source and the same tag.
     *
     * @param o The <code>Object</code> to check.
     * @return True is the <code>Objects</code> are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
      if (!(o instanceof MessageKeyTuple)) {
        return false;
      }
      MessageKeyTuple m = (MessageKeyTuple) o;
      return m.getTag() == tag_ && m.getSource() == source_;
    }


    @Override
    public int hashCode() {
      int result = 17;
      result = 31 * result + tag_;
      result = 31 * result + source_;
      return result;
    }
  }
} // MessageQueue
