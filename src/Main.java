public class Main {

    public static void main(String[] args) {

        String address = "localhost";
        int port = 8080;

        Component2 cmp2 = new Component2(port);
        cmp2.start();

        MyQueue queue = new MyQueue(10);
        Component1 cmp1 = new Component1(queue, address, port);
        cmp1.start();
    }
}
