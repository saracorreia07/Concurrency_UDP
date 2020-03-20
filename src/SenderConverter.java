import java.io.IOException;
import java.net.*;

public class SenderConverter implements Runnable {

    private MyQueue queue;
    private String address;
    private int port;

    public SenderConverter(MyQueue queue, String address, int port) {
        this.queue = queue;
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {

        while(true){

            //System.out.println("Thread " + Thread.currentThread().getName() + " is running");

            String element = null;

            synchronized (this.queue){

                // Block until element is removed from queue
                element = queue.poll();

                //System.out.println("[SenderConverter] Thread " + Thread.currentThread().getName() + " has consumed element '" + element + "' from the queue");

                if (queue.getSize() == 0) {
                    System.out.println("[SenderConverter] Thread "+ Thread.currentThread().getName() + " has left the queue empty");
                }
            }

            //Send message
            if(element!= null){
                sendMessage(element);
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // thread.interrupt called, no handling needed
            }

        }
    }

    private void sendMessage(String msg) {

        //Send message through socket (UDP)
        try{

            // gets the IP address of a given hostname
            InetAddress ipAddress = InetAddress.getByName(address);

            // Create the socket object for carrying the data
            DatagramSocket socket = new DatagramSocket();
            byte[] sendBuffer = null;
            byte[] receiveBuffer = new byte[1024];

            //Convert message to hexa
            String hexaMsg = hexaConverter(msg);
            sendBuffer = hexaMsg.getBytes();

            //Send packet
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ipAddress, port);
            socket.send(sendPacket);
            System.out.println("[SenderConverter] Sent message to [Printer]: " + hexaMsg);

            //System.out.println("Waiting for return packet");

            //Receive packet
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            //get data from received packet
            String responseMessage = new String(receivePacket.getData(),0, receivePacket.getLength());
            System.out.println("[SenderConverter] Received confirmed message from [Printer]: " + responseMessage);
            socket.close();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hexaConverter(String elem){

        StringBuffer sb = new StringBuffer();

        char ch[] = elem.toCharArray();
        for(int i = 0; i < ch.length; i++) {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }
        return sb.toString();
    }
}
