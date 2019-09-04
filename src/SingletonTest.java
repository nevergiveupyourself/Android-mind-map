//方法一:单例饿汉模式,单例实例在类装载时创建
public class Singleton{
  private static final Singleton INSTANCE=new Singleton();
  // Private constructor suppresses
  // default public constructor
  private Singleton(){};
  
  public static Singleton getInstance(){
    return INSTANCE;
  }
}
