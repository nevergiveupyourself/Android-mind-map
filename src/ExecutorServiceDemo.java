import java.text.SimpleDateFormat;
import java.util.concurrent.*;

public class ExecutorServiceDemo {
    private static int index=1;
    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world");
        System.out.println(Integer.MAX_VALUE);
        //建议通过ThreadPoolExecutor来创建线程池
        ExecutorService executorService=new ThreadPoolExecutor(3,3,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(4));
        ExecutorService executorService1=Executors.newFixedThreadPool(2);
        ExecutorService executorService2=Executors.newCachedThreadPool();
        ScheduledExecutorService executorService3=Executors.newScheduledThreadPool(2);
        ExecutorService executorService4=Executors.newSingleThreadExecutor();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);

                    synchronized(this) {
                        System.out.println("sleep 3000ms:" + index++);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss：SSS");
                        String str = sdf.format(System.currentTimeMillis());
                        System.out.println("当前时间是：" + str);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
//        executorService3.schedule(runnable,2000,TimeUnit.MILLISECONDS);
        /*executorService3.scheduleAtFixedRate(runnable,10,1000,TimeUnit.MILLISECONDS);
          executorService3.scheduleWithFixedDelay(runnable,10,3000,TimeUnit.MILLISECONDS);*/
        /*(1)当i=2027的时候,报如下错误(2026的时候正常):
         [1.005s][warning][os,thread] Failed to start thread - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
        Error occurred during initialization of VM
        java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached

        (2)当i-2028的时候,报错[0.790s][warning][os,thread] Failed to start thread - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
        Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
        	at java.base/java.lang.Thread.start0(Native Method)
        	at java.base/java.lang.Thread.start(Thread.java:803)
        	at java.base/java.util.concurrent.ThreadPoolExecutor.addWorker(ThreadPoolExecutor.java:937)
        	at java.base/java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1354)
        	at ExecutorServiceDemo.main(ExecutorServiceDemo.java:40)
        [0.987s][warning][os,thread] Failed to start thread - pthread_create failed (EAGAIN) for attributes: stacksize: 1024k, guardsize: 4k, detached.
        Error occurred during initialization of VM
        java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached*/
        for(int i=0;i<6;i++){
            executorService.execute(runnable);
        }
        executorService.shutdown();
//        boolean isTermination=executorService3.awaitTermination(20, TimeUnit.SECONDS);
//        System.out.println("isTermination:"+isTermination);
        System.out.println("isShutdown:"+executorService.isShutdown());
        System.out.println("isTerminated:"+executorService.isTerminated());
        //executorService3.shutdown();
        //executorService3.submit(runnable);
//        System.out.println("isShutdown:"+executorService3.isShutdown());
//        System.out.println("isTerminated:"+executorService3.isTerminated());
    }
}
