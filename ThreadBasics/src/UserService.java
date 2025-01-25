import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadSafeFormatter {
    public static ThreadLocal<SimpleDateFormat> df = new ThreadLocal<>() {
        @Override
        protected SimpleDateFormat initialValue() {
            //Called once for each thread.
            return new SimpleDateFormat("yyyy-MM-dd");
        }

        @Override
        public SimpleDateFormat get() {
            //1st call -> calls initial value.
            //Subsequent calls returns initialized value.
            return super.get();
        }
    };
    //Another way to initialize since Java 8.
    //public static ThreadLocal<SimpleDateFormat> df = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
}
public class UserService {
    public static void main(String[] args) throws InterruptedException {
        //Have 10 threads and internally all create their own objects. Not scalable
        new UserService().runBrute();

        //Cannot have global Instant object as its not thread safe and if we try to synchronize it will lead to slowness.

        //So we want each threads in executor service to each have their own objects.
        //Each thread has its own object so thread-safe and memory efficient.

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for(int i=0;i<1000;++i){
            threadPool.submit(() -> {
                String bd = new UserService().curDateSafe();
                System.out.println(bd);
            });
        }
        Thread.sleep(1000);

    }
    public void runBrute(){
        for(int i=0;i<10;++i){
            new Thread(() -> {
                String curEpoch = new UserService().curDate();
                System.out.println(curEpoch);
            }).start();
        }
    }
    public String curDate(){
        Date bd = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(bd);
    }
    public String curDateSafe(){
        Date bd = new Date();
        //Each thread gets its own copy.
        SimpleDateFormat df = ThreadSafeFormatter.df.get();
        return df.format(bd);
    }

}
