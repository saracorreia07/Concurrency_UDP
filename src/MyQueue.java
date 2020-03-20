import java.util.LinkedList;

public class MyQueue {

    final private int limit;

    /**
     * Container backing up the queue
     */
    final private LinkedList<String> list;

    /**
     * @param limit the queue size
     */
    public MyQueue(int limit) {
        this.limit = limit;
        list = new LinkedList<>();
    }

    public synchronized void put(String message) {

        // Make sure we block when the queue is full, even if interrupted
        while (list.size() == limit) {

            try {
                wait();
            } catch (InterruptedException e) {
                // thread.interrupt called, no handling needed
            }

        }

        // Add element to the queue
        list.add(message);
        //System.out.println("## ELEMENT ADDED, SIZE OF QUEUE IS NOW " + list.size() + " ##");

        // Notify other threads
        notifyAll();

    }

    public synchronized String poll() {

        // Make sure we block when the queue is empty, even if interrupted
        while (list.size() == 0) {

            try {
                wait();
            } catch (InterruptedException e) {
                // thread.interrupt called, no handling needed
            }
        }

        // Remove item from the queue
        String value = list.removeFirst();
        //System.out.println("## ELEMENT REMOVED, SIZE OF QUEUE IS NOW " + list.size() + " ##");

        // Notify other threads
        notifyAll();

        return value;
    }

    public synchronized int getSize() {
        return list.size();
    }

    public int getLimit() {
        return limit;
    }
}
