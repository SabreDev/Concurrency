public class Main {
    public static void main(String[] args) throws InterruptedException {
        Lock_ lockInstance = new Lock_();
        Thread t1 = new Thread(lockInstance.task1);
        Thread t2 = new Thread(lockInstance.task2);
        t1.start();
        t2.start();
    }
}