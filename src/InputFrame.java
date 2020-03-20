import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputFrame implements Runnable{

    private MyQueue queue;

    InputFrame(MyQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        while(true){

            //System.out.println("Thread " + Thread.currentThread().getName() + " is running");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("[InputFrame] Enter message: ");
            String s = null;

            try {
                s = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            synchronized (this.queue){

                this.queue.put(s);

                if (queue.getSize() == queue.getLimit()) {
                    System.out.println("[InputFrame] Thread " + Thread.currentThread().getName() + " has left the queue full");
                }
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // thread.interrupt called, no handling needed
            }

        }
    }
}
