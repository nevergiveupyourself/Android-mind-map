//方法一：单例饿汉模式,在类装载时创建
public class Singleton{
  private static final Singleton INSTANCE=new Singleton();
  // Private constructor suppresses
  // default public constructor
  private Singleton(){};
  
  public static Singleton getInstance(){
    return INSTANCE;
  }
}

//方法二：单例懒汉模式,第一次使用单例时创建,此种方法只能用在JDK5及以后版本(注意 INSTANCE 被声明为 volatile)
public class Singleton {
  
    private static volatile Singleton INSTANCE=null;

    //Private constructor suppresses
    //default public constructor
    private Singleton(){};

    //Thread safe and performance  promote
    public static Singleton getInstance(){
        if(INSTANCE == null){
            //more than two threads run into here,and wait.
            synchronized (Singleton.class){
                // When more than two threads run into the first null check same time,
                // to avoid instanced more than one time, it needs to be checked again.
              //疑问:2次判空的理由待进一步确认,synchronized按道理已经阻塞了？
              //解答疑问:当多个线程任务同时提交时,他们实际已经到达synchronized处,除了第一个任务进入获得Singleton类的对象锁之外,其余的
              //任务等待;当第一个任务创建完Singleton实例后,释放对象锁,第二个任务进入,此时必须再次判空,否则会创建多个实例.
                if(INSTANCE == null){
                    INSTANCE=new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
