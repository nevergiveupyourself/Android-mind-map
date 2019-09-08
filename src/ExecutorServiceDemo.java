import java.text.SimpleDateFormat;
import java.util.List;
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
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("runnable");
                synchronized(this) {
                    System.out.println("sleep 3000ms:" + index++);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss：SSS");
                    String str = sdf.format(System.currentTimeMillis());
                    System.out.println("当前时间是：" + str);
                }
            }
        };
        Callable<String> callable=new Callable<>() {
            @Override
            public String call() throws Exception {
                String str;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("runnable");
                synchronized(this) {
                    System.out.println("sleep 3000ms:" + index++);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss：SSS");
                    str = sdf.format(System.currentTimeMillis());
                    System.out.println("当前时间是：" + str);
                }
                return str;
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
           //executorService.execute(runnable);
            Future future2=executorService.submit(runnable);
            try {
                System.out.println("future2.get():"+future2.get());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            /*Future<String> future=executorService.submit(callable);
            try {
                String callableResult=future.get();

                System.out.println("callableResult:"+callableResult+",future.isCancelled():"+future.isCancelled()+",future.isDone():"+future.isDone());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("executorService.submit(callable):"+executorService.submit(callable));*/

            /*Future<String> future= (Future<String>) executorService.submit(runnable);
            future.cancel(true);
            System.out.println("future.isCancelled():"+future.isCancelled()+",future.isDone():"+future.isDone());
            try {
                System.out.println("future.get():"+future.get());//当task执行成功的时候future.get()返回null
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/
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
