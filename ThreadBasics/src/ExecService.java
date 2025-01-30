import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecService {
    public static void main(String[] args){
        //Tasks execute in blocking queue which is thread safe queue.
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i=0;i<100;++i){
            executorService.execute(new Task());
        }
        ScheduledExecutorService executorService1 = Executors.newScheduledThreadPool(10);
        //Runs a one-off task after 10 seconds.
        executorService1.schedule(new Task(), 10, java.util.concurrent.TimeUnit.SECONDS);
        //Runs a task every 10 seconds with an initial delay of 15 seconds.
        executorService1.scheduleAtFixedRate(new Task(), 15, 10, TimeUnit.SECONDS);
        //Runs a task every 10 seconds after the previous task completes with an initial delay of 15 seconds.
        executorService1.scheduleWithFixedDelay(new Task(), 15, 10, TimeUnit.SECONDS);
    }
    static class Task implements Runnable {
        public void run() {
            System.out.println("Running on: "+ Thread.currentThread());
        }
    }
}


