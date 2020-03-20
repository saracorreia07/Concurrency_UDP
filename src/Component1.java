public class Component1 {

    private InputFrame inputFrame;
    private SenderConverter senderConverter;

    public Component1(MyQueue queue, String address, int port){
        this.inputFrame = new InputFrame(queue);
        this.senderConverter = new SenderConverter(queue, address, port);
    }

    public void start(){

        Thread t1 = new Thread(this.inputFrame);
        t1.setName("InputFrame");

        Thread t2 = new Thread(this.senderConverter);
        t2.setName("SenderConverter");

        t2.start();
        t1.start();


    }
}
