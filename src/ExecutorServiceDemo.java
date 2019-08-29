import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo{
    private static int index=1;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world");
        ExecutorService executorService1=Executors.newFixedThreadPool(4);
        ExecutorService executorService2=Executors.newCachedThreadPool();
        ScheduledExecutorService executorService3=Executors.newScheduledThreadPool(4);
        ExecutorService executorService4=Executors.newSingleThreadExecutor();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    synchronized(this){
                        System.out.println("sleep 3000ms:"+index++);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss：SSS");
                        String str=sdf.format(System.currentTimeMillis());
                        System.out.println("当前时间是："+str);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
//        executorService3.schedule(runnable,2000,TimeUnit.MILLISECONDS);
    executorService3.scheduleAtFixedRate(runnable,10,1000,TimeUnit.MILLISECONDS);
        //executorService3.scheduleWithFixedDelay(runnable,10,3000,TimeUnit.MILLISECONDS);
        /*for(int i=0;i<12;i++){
            executorService3.execute(runnable);
        }
        executorService3.shutdown();
        boolean isTermination=executorService3.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("isTermination:"+isTermination);*/
        System.out.println("isShutdown:"+executorService3.isShutdown());
        System.out.println("isTerminated:"+executorService3.isTerminated());
        //executorService3.shutdown();
        //executorService3.submit(runnable);
        System.out.println("isShutdown:"+executorService3.isShutdown());
        System.out.println("isTerminated:"+executorService3.isTerminated());
    }
}



