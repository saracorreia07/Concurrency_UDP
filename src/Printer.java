import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Printer implements Runnable {

    private int port;

    public Printer(int port){
        this.port = port;
    }

    @Override
    public void run() {

        try {

            DatagramSocket socket = new DatagramSocket(port);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer = null;

            //READ MESSAGE THROUGH UDP SOCKET
            while (true){

                //System.out.println("Thread " + Thread.currentThread().getName() + " is running");

                //System.out.println("Waiting for datagram packet");
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                InetAddress ipAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println("[Printer] Received hexa-message: " + message);

                String msg =  hexaToStringConverter(message);
                System.out.println("[Printer] Message converted to string: " + msg);

                sendBuffer = msg.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, ipAddress, clientPort);
                socket.send(sendPacket);

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hexaToStringConverter(String hex){

        hex = hex.replaceAll("^(00)+", "");
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
        }
        return new String(bytes);
    }
}
