import java.util.concurrent.*;


class CustomRejectionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task " + r.toString() + " rejected");
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        System.out.println("Running on thread: "+ Thread.currentThread().getName());
    }
}
public class CustomExecService {
    public static void main(String[] args){
        //To use policies do new ThreadPoolExecutor.CallerRunsPolicy() or others.
        ExecutorService customExecutorService =
                new ThreadPoolExecutor
                        (2,
                                10,
                                60, TimeUnit.SECONDS,
                                new ArrayBlockingQueue<>(10),
                                new CustomRejectionHandler()
                        );
        for (int i = 0; i < 500; i++) {
            customExecutorService.execute(new Task());
        }
        customExecutorService.shutdown();

    }
}
