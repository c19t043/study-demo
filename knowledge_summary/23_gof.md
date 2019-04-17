# 23种设计模式

## 设计模式的六大原则

总原则：对扩展开发，对修改关闭

+ 单一职责原则
    不存在多个导致类变更的原因
+ 厘式替换原则
    任何基类出现的地方，子类也可以出现
+ 依赖倒置原则
    面向接口编程，依赖抽象而不依赖具体实现
+ 接口隔离原则
    每个子类中不存在不使用但必须实现的接口方法
+ 迪米特原则（最少知道原则）
    一个类对依赖的类知道的越少越好
+ 合成复用原则
    尽量少时用继承，而使用合成/聚合的方式

### 单例模式

单例对象的类只允许有一个实例存在

#### 单列模式的八种写法

+ 饿汉式（静态常量）

```java
public class Singleton{
    private static class Singleton INSTANCE = new Singleton();

    private Singleton(){}

    public static Singleton getInstance(){
        return INSTANCE;
    }
}
```

+ 饿汉式（静态代码块）

```java
public class Singleton{
    private static class Singleton INSTANCE;

    private Singleton(){}

    static{
        INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return INSTANCE;
    }
}
```

+ 懒汉式（线程不安全）

```java
public class Singleton{
    private static class Singleton INSTANCE;

    private Singleton(){}

    public static Singleton getInstance(){
        if(INSTANCE != null){
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }
}
```

+ 懒汉式（线程安全,同步方法）

```java
public class Singleton{
    private static class Singleton INSTANCE;

    private Singleton(){}

    public static synchronized Singleton getInstance(){
        if(INSTANCE != null){
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }
}
```

+ 懒汉式（线程安全,同步代码块）

```java
public class Singleton{
    private static class Singleton INSTANCE;

    private Singleton(){}

    public static Singleton getInstance(){
        synchronized(Singleton.class){
            if(INSTANCE != null){
                INSTANCE = new Singleton();
            }
        }
        return INSTANCE;
    }
}
```

+ 懒汉式（线程安全,同步代码块,双重检查）

```java
public class Singleton{
    private static volatile class Singleton INSTANCE;

    private Singleton(){}

    public static Singleton getInstance(){
        if(INSTANCE != null){
            synchronized(Singleton.class){
                if(INSTANCE != null){
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
```

+ 静态内部类

```java
public class Singleton{
    private Singleton(){}

    private static class SingletonInstance{
        public static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonInstance.INSTANCE;
    }
}
```

+ 枚举

```java
public enum Singleton{
    INSTANCE;
}
```