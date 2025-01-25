import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock_ {
    private final Lock lock = new ReentrantLock();
    private final Condition conditionMet = lock.newCondition();
    public Lock_(){}
    Runnable task1 = () -> {
        lock.lock();
        try{
            System.out.println("Running Task1 on thread: " + Thread.currentThread());
            System.out.println("Need to wait for Task2 to execute");
            try {
                conditionMet.await(); //releases the lock, re-acquires on signal
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Task1 processing is done");
        } finally {
            lock.unlock();
        }
    };

    Runnable task2 = () -> {
        lock.lock();
        try{
            System.out.println("Running Task2 on thread: " + Thread.currentThread());
            System.out.println("Signalling...");
            conditionMet.signal();
            System.out.println("Task2 processing is done");
        } finally {
            lock.unlock();
        }
    };

    public Runnable getTask1() {
        return task1;
    }

    public Runnable getTask2() {
        return task2;
    }
}
