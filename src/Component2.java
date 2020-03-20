public class Component2 {

    Printer printer;

    public Component2(int port){
        this.printer = new Printer(port);
    }

    public void start(){
        Thread t1 = new Thread(this.printer);
        t1.setName("Printer");
        t1.start();
    }

}
