# 线程

## ThreadLocal

面对多线程资源共享问题，同步机制采用“时间换空间”的方式，提供一份变量，多个线程排队访问，
ThreadLocal采用“空间换时间”的策略，对于每一个线程提供一个变量，多个线程不存在并发访问的问题

## interrupted与isInterrupted的区别

interrupted()：测试当前线程是否已经是中断状态，执行后具有状态标志清除为false的功能。
isInterrupted()：测试线程Thread对象是否已经是中断状态，但不清除状态标志。
方法：

```java
public static boolean interrupted() {
    return currentThread().isInterrupted(true);
}
public boolean isInterrupted() {
    return isInterrupted(false);
}
private native boolean isInterrupted(boolean ClearInterrupted);
```

## 终止正在运行的线程的三种方法：

使用退出标志，是线程正常退出，也就是当run方法完成后线程终止；
使用stop方法强行终止线程，但是不推荐使用这个方法，因为stop和suspend及resume一样都是作废过期的方法，使用它们可能产生不可预料的结果；
使用interrupt方法中断线程；（推荐）

## yield方法

yield()方法的作用是放弃当前的CPU资源，将它让给其他的任务去占用CPU执行时间。但放弃时间不确定，有可能刚刚放弃，马上又获得CPU时间片。这里需要注意的是yield()方法和sleep方法一样，线程并不会让出锁，和wait不同。

## 线程的优先级

Java中线程的优先级分为1-10这10个等级，如果小于1或大于10则JDK抛出IllegalArgumentException()的异常，默认优先级是5。在Java中线程的优先级具有继承性，比如A线程启动B线程，则B线程的优先级与A是一样的。注意程序正确性不能依赖线程的优先级高低，因为操作系统可以完全不理会Java线程对于优先级的决定。

## Java中线程的状态

New, Runnable, Blocked, Waiting, Time_waiting, Terminated.

## 守护线程

Java中有两种线程，一种是用户线程，另一种是守护线程。当进程中不存在非守护线程了，则守护线程自动销毁。通过setDaemon(true)设置线程为后台线程。注意thread.setDaemon(true)必须在thread.start()之前设置，否则会报IllegalThreadStateException异常；在Daemon线程中产生的新线程也是Daemon的；在使用ExecutorSerice等多线程框架时，会把守护线程转换为用户线程，并且也会把优先级设置为Thread.NORM_PRIORITY。在构建Daemon线程时，不能依靠finally块中的内容来确保执行关闭或清理资源的逻辑。更多详细内容可参考[《Java守护线程概述》](https://blog.csdn.net/u013256816/article/details/50392298)

## synchronized的类锁与对象锁

类锁：在方法上加上static synchronized的锁，或者synchronized(xxx.class)的锁。如下代码中的method1和method2：
对象锁：参考method4, method5,method6.

```java
public class LockStrategy
{
    public Object object1 = new Object();
 
    public static synchronized void method1(){}
    public void method2(){
        synchronized(LockStrategy.class){}
    }
 
    public synchronized void method4(){}
    public void method5()
    {
        synchronized(this){}
    }
    public void method6()
    {
        synchronized(object1){}
    }
}
```

注意方法method4和method5中的同步块也是互斥的。
下面做一道习题来加深一下对对象锁和类锁的理解：
有一个类这样定义

```java
public class SynchronizedTest
{
    public synchronized void method1(){}
    public synchronized void method2(){}
    public static synchronized void method3(){}
    public static synchronized void method4(){}
}
```

那么，有SynchronizedTest的两个实例a和b，对于一下的几个选项有哪些能被一个以上的线程同时访问呢？

A. a.method1() vs. a.method2()
B. a.method1() vs. b.method1()
C. a.method3() vs. b.method4()
D. a.method3() vs. b.method3()
E. a.method1() vs. a.method3()

答案是什么呢？BE

有关Java中的锁的详细信息，可以参考[《Java中的锁》](http://blog.csdn.net/u013256816/article/details/51204385)

## 同步不具备继承性

当一个线程执行的代码出现异常时，其所持有的锁会自动释放。同步不具有继承性（声明为synchronized的父类方法A，在子类中重写之后并不具备synchronized的特性）。

## wait, notify, notifyAll用法

只能在同步方法或者同步块中使用wait()方法。在执行wait()方法后，当前线程释放锁（这点与sleep和yield方法不同）。调用了wait函数的线程会一直等待，知道有其他线程调用了同一个对象的notify或者notifyAll方法才能被唤醒，需要注意的是：被唤醒并不代表立刻获得对象的锁，要等待执行notify()方法的线程执行完，即退出synchronized代码块后，当前线程才会释放锁，而呈wait状态的线程才可以获取该对象锁。

如果调用wait()方法时没有持有适当的锁，则抛出IllegalMonitorStateException，它是RuntimeException的一个子类，因此，不需要try-catch语句进行捕获异常。

notify方法只会（随机）唤醒一个正在等待的线程，而notifyAll方法会唤醒所有正在等待的线程。如果一个对象之前没有调用wait方法，那么调用notify方法是没有任何影响的。

详细可以参考[《JAVA线程间协作：wait.notify.notifyAll》](http://blog.csdn.net/u013256816/article/details/50440123)

带参数的wait(long timeout)或者wait(long timeout, int nanos)方法的功能是等待某一时间内是否有线程对锁进行唤醒，如果超过这个时间则自动唤醒。

## 管道

在Java中提供了各种各样的输入/输出流Stream，使我们能够很方便地对数据进行操作，其中管道流（pipeStream)是一种特殊的流，用于在不同线程间直接传送数据。一个线程发送数据到输出管道，另一个线程从输入管道中读数据，通过使用管道，实现不同线程间的通信，而无须借助类似临时文件之类的东西。在JDK中使用4个类来使线程间可以进行通信：PipedInputStream, PipedOutputStream, PipedReader, PipedWriter。使用代码类似inputStream.connect(outputStream)或outputStream.connect(inputStream)使两个Stream之间产生通信连接。

几种进程间的通信方式

- 管道( pipe )：管道是一种半双工的通信方式，数据只能单向流动，而且只能在具有亲缘关系的进程间使用。进程的亲缘关系通常是指父子进程关系。
- 有名管道 (named pipe) ： 有名管道也是半双工的通信方式，但是它允许无亲缘关系进程间的通信。
- 信号量( semophore ) ： 信号量是一个计数器，可以用来控制多个进程对共享资源的访问。它常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。因此，主要作为进程间以及同一进程内不同线程之间的同步手段。
- 消息队列( message queue ) ： 消息队列是由消息的链表，存放在内核中并由消息队列标识符标识。消息队列克服了信号传递信息少、管道只能承载无格式字节流以及缓冲区大小受限等缺点。
- 信号 ( sinal ) ： 信号是一种比较复杂的通信方式，用于通知接收进程某个事件已经发生。
- 共享内存( shared memory ) ：共享内存就是映射一段能被其他进程所访问的内存，这段共享内存由一个进程创建，但多个进程都可以访问。共享内存是最快的 IPC 方式，它是针对其他进程间通信方式运行效率低而专门设计的。它往往与其他通信机制，如信号两，配合使用，来实现进程间的同步和通信。
- 套接字( socket ) ： 套解口也是一种进程间通信机制，与其他通信机制不同的是，它可用于不同及其间的进程通信。

## join方法

如果一个线程A执行了thread.join()语句，其含义是：当前线程A等待thread线程终止之后才从thread.join()返回。join与synchronized的区别是：join在内部使用wait()方法进行等待，而synchronized关键字使用的是“对象监视器”做为同步。
join提供了另外两种实现方法：join(long millis)和join(long millis, int nanos)，至多等待多长时间而退出等待(释放锁)，退出等待之后还可以继续运行。内部是通过wait方法来实现的。

join()是可以不在同步代码块中执行的等待方法

可以参考一下一个例子：

```java
System.out.println("method main begin-----");
Thread t = new Thread(new Runnable(){
    int i = 0;
    @Override
    public void run()
    {
        while(true)
        {
            System.out.println(i++);
            try
            {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
});
t.start();
t.join(2000);
System.out.println("method main end-----");
```

运行结果：

```java
method main begin-----
0
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
method main end-----
19
20
21
```

## ThreadLocal

ThreadLocal可以实现每个线程绑定自己的值，即每个线程有各自独立的副本而互相不受影响。一共有四个方法：get, set, remove, initialValue。可以重写initialValue()方法来为ThreadLocal赋初值。如下：

```java
private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
    @Override
    protected Long initialValue()
    {
        return System.currentTimeMillis();
    }
};
```

ThreadLocal建议设置为static类型的。
使用类InheritableThreadLocal可以在子线程中取得父线程继承下来的值。可以采用重写childValue（Object parentValue）方法来更改继承的值。
查看案例：

```java
public class InheriableThreadLocal
{
    public static final InheritableThreadLocal<?> itl = new InheritableThreadLocal<Object>(){
        @Override protected Object initialValue()
        {
            return new Date().getTime();
        }

        @Override protected Object childValue(Object parentValue)
        {
            return parentValue+" which plus in subThread.";
        }
    };

    public static void main(String[] args)
    {
        System.out.println("Main: get value = "+itl.get());
        Thread a = new Thread(new Runnable(){
            @Override public void run()
            {
                System.out.println(Thread.currentThread().getName()+": get value = "+itl.get());
            }
        });
        a.start();
    }
}
```

运行结果：

Main: get value = 1461585405704
Thread-0: get value = 1461585405704 which plus in subThread.
如果去掉@Override protected Object childValue(Object parentValue)方法运行结果：

Main: get value = 1461585396073
Thread-0: get value = 1461585396073
注意：在线程池的情况下，在ThreadLocal业务周期处理完成时，最好显式的调用remove()方法，清空”线程局部变量”中的值。正常情况下使用ThreadLocal不会造成内存溢出，弱引用的只是threadLocal，保存的值依然是强引用的，如果threadLocal依然被其他对象强引用，”线程局部变量”是无法回收的。

## 公平锁

公平锁是指多个线程在等待同一个锁时，必须按照申请锁的先后顺序来一次获得锁

## ReentrantLock

ReentrantLock提供了tryLock方法，tryLock调用的时候，如果锁被其他线程持有，那么tryLock会立即返回，返回结果为false；如果锁没有被其他线程持有，那么当前调用线程会持有锁，并且tryLock返回的结果为true。

boolean tryLock()
boolean tryLock(long timeout, TimeUnit unit)
可以在构造ReentranLock时使用公平锁，公平锁是指多个线程在等待同一个锁时，必须按照申请锁的先后顺序来一次获得锁。synchronized中的锁时非公平的，默认情况下ReentrantLock也是非公平的，但是可以在构造函数中指定使用公平锁。

ReentrantLock()
ReentrantLock(boolean fair)
对于ReentrantLock来说，还有一个十分实用的特性，它可以同时绑定多个Condition条件，以实现更精细化的同步控制。

ReentrantLock使用方式如下：

```java
Lock lock = new ReentrantLock();
lock.lock();
try{
}finally{
    lock.unlock();
}
```

## ReentrantLock中的其余方法

int getHoldCount()：查询当前线程保持此锁定的个数，也就是调用lock()方法的次数。
int getQueueLength()：返回正等待获取此锁定的线程估计数。比如有5个线程，1个线程首先执行await()方法，那么在调用getQueueLength方法后返回值是4，说明有4个线程在等待lock的释放。
int getWaitQueueLength(Condition condition)：返回等待此锁定相关的给定条件Condition的线程估计数。比如有5个线程，每个线程都执行了同一个condition对象的await方法，则调用getWaitQueueLength(Condition condition)方法时返回的int值是5。
boolean hasQueuedThread(Thread thread)：查询指定线程是否正在等待获取此锁定。
boolean hasQueuedThreads()：查询是否有线程正在等待获取此锁定。
boolean hasWaiters(Condition condition)：查询是否有线程正在等待与此锁定有关的condition条件。
boolean isFair()：判断是不是公平锁。
boolean isHeldByCurrentThread()：查询当前线程是否保持此锁定。
boolean isLocked()：查询此锁定是否由任意线程保持。
void lockInterruptibly()：如果当前线程未被中断，则获取锁定，如果已经被中断则出现异常。

## Condition

一个Condition和一个Lock关联在一起，就想一个条件队列和一个内置锁相关联一样。要创建一个Condition，可以在相关联的Lock上调用Lock.newCondition方法。正如Lock比内置加锁提供了更为丰富的功能，Condition同样比内置条件队列提供了更丰富的功能：在每个锁上可存在多个等待、条件等待可以是可中断的或者不可中断的、基于时限的等待，以及公平的或非公平的队列操作。与内置条件队列不同的是，对于每个Lock，可以有任意数量的Condition对象。Condition对象继承了相关的Lock对象的公平性，对于公平的锁，线程会依照FIFO顺序从Condition.await中释放。

注意：在Condition对象中，与wait,notify和notifyAll方法对于的分别是await,signal,signalAll。但是，Condition对Object进行了扩展，因而它也包含wait和notify方法。一定要确保使用的版本——await和signal.

详细可参考[《JAVA线程间协作：Condition》](http://blog.csdn.net/u013256816/article/details/50445241)

## 读写锁ReentrantReadWriteLock

读写锁表示也有两个锁，一个是读操作相关的锁，也称为共享锁；另一个是写操作相关的锁，也叫排它锁。也就是多个读锁之间不互斥，读锁与写锁互斥，写锁与写锁互斥。在没有Thread进行写操作时，进行读取操作的多个Thread都可以获取读锁，而进行写入操作的Thread只有在获取写锁后才能进行写入操作。即多个Thread可以同时进行读取操作，但是同一时刻只允许一个Thread进行写入操作。(lock.readlock.lock(), lock.readlock.unlock, lock.writelock.lock, lock.writelock.unlock)

## Timer的使用

JDK中的Timer类主要负责计划任务的功能，也就是在指定时间开始执行某一任务。Timer类的主要作用就是设置计划任务，但封装任务的类却是TimerTask类（public abstract class TimerTask extends Object implements Runnable）。可以通过new Timer(true)设置为后台线程。

有以下几个方法：

void schedule(TimerTask task, Date time)：在指定的日期执行某一次任务。如果执行任务的时间早于当前时间则立刻执行。
void schedule(TimerTask task, Date firstTime, long period)：在指定的日期之后，按指定的间隔周期性地无限循环地执行某一任务。如果执行任务的时间早于当前时间则立刻执行。
void schedule(TimerTask task, long delay)：以当前时间为参考时间，在此基础上延迟指定的毫秒数后执行一次TimerTask任务。
void schedule(TimerTask task, long delay, long period）：以当前时间为参考时间，在此基础上延迟指定的毫秒数，再以某一间隔无限次数地执行某一任务。
void scheduleAtFixedRate(TimerTask task, Date firstTime, long period)：下次执行任务时间参考上次任务的结束时间，且具有“追赶性”。
TimerTask是以队列的方式一个一个被顺序执行的，所以执行的时间有可能和预期的时间不一致，因为前面的任务有可能消耗的时间较长，则后面的任务运行时间也会被延迟。
TimerTask类中的cancel方法的作用是将自身从任务队列中清除。
Timer类中的cancel方法的作用是将任务队列中的全部任务清空，并且进程被销毁。

Timer的缺陷：Timer支持基于绝对时间而不是相对时间的调度机制，因此任务的执行对系统时钟变化很敏感，而ScheduledThreadPoolExecutor只支持相对时间的调度。Timer在执行所有定时任务时只会创建一个线程。如果某个任务的执行时间过长，那么将破坏其他TimerTask的定时精确性。Timer的另一个问题是，如果TimerTask抛出了一个未检查的异常，那么Timer将表现出糟糕的行为。Timer线程并不波或异常，因此当TimerTask抛出为检测的异常时将终止定时线程。

JDK5或者更高的JDK中已经很少使用Timer.

## 线程安全的单例模式

建议不要采用DCL的写法，建议使用下面这种写法：

```java
public class LazyInitHolderSingleton {  
        private LazyInitHolderSingleton() {  
        }  

        private static class SingletonHolder {  
                private static final LazyInitHolderSingleton INSTANCE = new LazyInitHolderSingleton();  
        }  

        public static LazyInitHolderSingleton getInstance() {  
                return SingletonHolder.INSTANCE;  
        }  
}
```

或者这种：

```java
public enum SingletonClass
{
    INSTANCE;
}
```

## 线程组ThreadGroup

为了有效地对一些线程进行组织管理，通常的情况下事创建一个线程组，然后再将部分线程归属到该组中，这样可以对零散的线程对象进行有效的组织和规划。参考以下案例：

```JAVA
ThreadGroup tgroup = new ThreadGroup("mavelous zzh");
new Thread(tgroup, new Runnable(){
    @Override
    public void run()
    {
        System.out.println("A: Begin: "+Thread.currentThread().getName());
        while(!Thread.currentThread().isInterrupted())
        {
 
        }
        System.out.println("A: DEAD: "+Thread.currentThread().getName());
    }}).start();;
new Thread(tgroup, new Runnable(){
    @Override
    public void run()
    {
        System.out.println("B: Begin: "+Thread.currentThread().getName());
        while(!Thread.currentThread().isInterrupted())
        {
 
        }
        System.out.println("B: DEAD: "+Thread.currentThread().getName());
    }}).start();;
System.out.println(tgroup.activeCount());
System.out.println(tgroup.getName());
System.out.println(tgroup.getMaxPriority());
System.out.println(tgroup.getParent());
TimeUnit.SECONDS.sleep(5);
tgroup.interrupt();
```

输出：

```JAVA
A: Begin: Thread-0
2
mavelous zzh
10
B: Begin: Thread-1
java.lang.ThreadGroup[name=main,maxpri=10]
B: DEAD: Thread-1
A: DEAD: Thread-0
```

## 多线程的异常捕获UncaughtExceptionHandler

setUncaughtExceptionHandler()的作用是对指定线程对象设置默认的异常处理器。

```JAVA
Thread thread = new Thread(new Runnable(){
    @Override
    public void run()
    {
        int a=1/0;
    }
});
thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler(){
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        System.out.println("线程："+t.getName()+" 出现了异常："+e.getMessage());
    }
});
thread.start();
```

输出：线程：Thread-0 出现了异常：/ by zero
setDefaultUncaughtExceptionHandler()方法对所有线程对象设置异常处理器。

```JAVA
Thread thread = new Thread(new Runnable(){
    @Override
    public void run()
    {
        int a=1/0;
    }
});
Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler(){
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        System.out.println("线程："+t.getName()+" 出现了异常："+e.getMessage());
    }
});
thread.start();
```

输出同上，注意两者之间的区别。如果既包含setUncaughtExceptionHandler又包含setDefaultUncaughtExceptionHandler那么会被setUncaughtExceptionHandler处理，setDefaultUncaughtExceptionHandler则忽略。更多详细信息参考《JAVA多线程之UncaughtExceptionHandler——处理非正常的线程中止》

## ReentrantLock与synchonized区别

ReentrantLock可以中断地获取锁（void lockInterruptibly() throws InterruptedException）
ReentrantLock可以尝试非阻塞地获取锁（boolean tryLock()）
ReentrantLock可以超时获取锁。通过tryLock(timeout, unit)，可以尝试获得锁，并且指定等待的时间。
ReentrantLock可以实现公平锁。通过new ReentrantLock(true)实现。
ReentrantLock对象可以同时绑定多个Condition对象，而在synchronized中，锁对象的的wait(), notify(), notifyAll()方法可以实现一个隐含条件，如果要和多于一个的条件关联的对象，就不得不额外地添加一个锁，而ReentrantLock则无需这样做，只需要多次调用newCondition()方法即可。

## 使用多线程的优势

更多的处理器核心；更快的响应时间；更好的编程模型。

## 构造线程

一个新构造的线程对象是由其parent线程来进行空间分配的，而child线程继承了parent线程的：是否为Daemon、优先级、加载资源的contextClassLoader以及InheritableThreadLocal(参考第12条)，同时还会分配一个唯一的ID来标志这个child线程

## 使用多线程的方式

extends Thread 或者implements Runnable 或者 implememts Callable

## 读写锁

读写锁在同一时刻可以允许多个读线程访问，但是在写线程访问时，所有的读线程和其他写线程均被阻塞。读写锁维护了一对锁，一个读锁和一个写锁，通过分离读锁和写锁，使得并发性相比一般的排它锁有了很大的提升。Java中使用ReentrantReadWriteLock实现读写锁，读写锁的一般写法如下(修改自JDK7中的示例)：

```java
   class RWDictionary {
   private final Map<String, Object> m = new TreeMap<String, Object>();
   private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
   private final Lock r = rwl.readLock();
   private final Lock w = rwl.writeLock();

   public Object get(String key)
   {
       r.lock();
       try
       {
           return m.get(key);
       }
       finally
       {
           r.unlock();
       }
   }

   public String[] allKeys()
   {
       r.lock();
       try
       {
           return (String[]) m.keySet().toArray();
       }
       finally
       {
           r.unlock();
       }
   }

   public Object put(String key, Object value)
   {
       w.lock();
       try
       {
           return m.put(key, value);
       }
       finally
       {
           w.unlock();
       }
   }

   public void clear()
   {
       w.lock();
       try
       {
           m.clear();
       }
       finally
       {
           w.unlock();
       }
   }
}
```

## 锁降级

锁降级是指写锁降级成读锁。如果当前线程拥有写锁，然后将其释放，最后获取读锁，这种分段完成的过程不能称之为锁降级。锁降级是指把持住（当前拥有的）写锁，再获取到读锁，最后释放（先前拥有的）写锁的过程。参考下面的示例：

```java
private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
private final Lock r = rwl.readLock();
private final Lock w = rwl.writeLock();
private volatile static boolean update = false;

public void processData()
{
    r.lock();
    if(!update)
    {
        //必须先释放读锁
        r.unlock();
        //锁降级从写锁获取到开始
        w.lock();
        try
        {
            if(!update)
            {
                //准备数据的流程（略）
                update = true;
            }
            r.lock();
        }
        finally
        {
            w.unlock();
        }
        //锁降级完成，写锁降级为读锁
    }

    try
    {
        //使用数据的流程（略）
    }
    finally
    {
        r.unlock();
    }
}
```

锁降级中的读锁是否有必要呢？答案是必要。主要是为了保证数据的可见性，如果当前线程不获取读锁而是直接释放写锁，假设此刻另一个线程（T）获取了写锁并修改了数据，那么当前线程无法感知线程T的数据更新。如果当前线程获取读锁，即遵循锁降级的步骤，则线程T将会被阻塞，直到当前线程使用数据并释放读锁之后，线程T才能获取写锁进行数据更新。

## ConcurrentHashMap

ConcurrentHashMap是线程安全的HashMap，内部采用分段锁来实现，默认初始容量为16，装载因子为0.75f，分段16，每个段的HashEntry<K,V>[]大小为2。键值都不能为null。每次扩容为原来容量的2倍，ConcurrentHashMap不会对整个容器进行扩容，而只对某个segment进行扩容。在获取size操作的时候，不是直接把所有segment的count相加就可以可到整个ConcurrentHashMap大小，也不是在统计size的时候把所有的segment的put, remove, clean方法全部锁住，这种方法太低效。在累加count操作过程中，之前累加过的count发生变化的几率非常小，所有ConcurrentHashMap的做法是先尝试2（RETRIES_BEFORE_LOCK）次通过不锁住Segment的方式统计各个Segment大小，如果统计的过程中，容器的count发生了变化，再采用加锁的方式来统计所有的Segment的大小

## 线程安全的非阻塞队列

非阻塞队列有ConcurrentLinkedQueue, ConcurrentLinkedDeque。元素不能为null。以ConcurrentLinkedQueue为例，有头head和尾tail两个指针，遵循FIFO的原则进行入队和出队，方法有add(E e), peek()取出不删除, poll()取出删除, remove(Object o)，size(), contains(Object o), addAll(Collection c), isEmpty()。ConcurrentLinkedDeque是双向队列，可以在头和尾两个方向进行相应的操作。

## 阻塞队列

阻塞队列（BlockingQueue）是一个支持两个附加操作的队列。这两个附加的操作支持阻塞的插入和移除方法。
支持阻塞的插入方法：意思是当队列满时，队列会阻塞插入元素的线程，直到队列不满。
支持阻塞的移除方法：意思是队列为空时，获取元素的线程会等待队列变为非空。
任何阻塞队列中的元素都不能为null.

## 阻塞队列的插入和移除操作处理方式：

方法-处理方法 抛出异常 返回特殊值 可能阻塞等待 可设定等待时间
入队 add(e) offer(e) put(e) offer(e,timeout,unit)
出队 remove() poll() take() poll(timeout,unit)
查看 element() peek() 无 无
如果是无界队列，队列不可能出现满的情况，所以使用put或offer方法永远不会被阻塞，而且使用offer方法时，该方法永远返回true.

## Java里的阻塞队列

ArrayBlockingQueue:一个由数组结构组成的有界阻塞队列。
LinkedeBlockingQueue:一个有链表结构组成的有界阻塞队列。
PriorityBlockingQueue:一个支持优先级排序的无界阻塞队列
DelayQueue:一个使用优先级队列实现的无界阻塞队列。
SynchronousQueue:一个不存储元素的阻塞队列。
LinkedTransferQueue:一个由链表结构组成的无界阻塞队列。
LinkedBlockingDeque:一个由链表结构组成的双向阻塞队列。

## ArrayBlockingQueue

此队列按照FIFO的原则对元素进行排序，可以设定为公平ArrayBlockingQueue(int capacity, boolean fair)，默认为不公平。初始化时必须设定容量大小ArrayBlockingQueue(int capactiy)。

## LinkedBlockingQueue

与ArrayBlockingQueue一样，按照FIFO原则进行排序，与ArrayBlockingQueue不同的是内部实现是一个链表结构，且不能设置为公平的。默认和最大长度为Integer.MAX_VALUE。

LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列是指可以从队列的两端插入和移除元素。双向队列因为多了一个操作队列的入口，在多线程同时入队时，也就减少了一半的竞争。相对其他的阻塞队列，LinkedBlockingDeque多了addFirst, addLast, offerFirst, offerLast, peekFirst, peekLast等方法

## PriorityBlockingQueue

是一个支持优先级的无界阻塞队列，默认初始容量为11，默认情况下采用自然顺序升序排列，不能保证同优先级元素的顺序。内部元素要么实现Comparable接口，要么在初始化的时候指定构造函数的Comparator来对元素进行排序，有关Comparable与Comparator的细节可以参考：[Comparable与Comparator浅析](http://blog.csdn.net/u013256816/article/details/50899416)。

## DelayQueue

DelayQueue是一个支持延时获取元素的无界阻塞队列。内部包含一个PriorityQueue来实现，队列中的元素必须实现Delay接口，在创建元素时可以指定多久才能从队列中获取当前元素。只有在延迟期满时才能从队列中提取元素。
DelayQueue非常有用，可以将DelayQueue运用在下面应用场景。

- 缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询DelayQueue,一旦能从DelayQueue中获取元素时，表示缓存有效期到了。
- 定时任务调度：使用DelayQueue保存当天将会执行的任务和执行时间，一旦从DelayQueue中获取到任务就开始执行，比如TimerQueue就是使用DelayQueue实现的。

## SynchronousQueue

是一个不存储元素的阻塞队列，每一个put操作必须等待一个take操作，否则不能继续添加元素，非常适合传递性场景。支持公平访问队列。默认情况下线程采用非公平策略访问队列。

## LinkedTransferQueue

是一个由链表结构组成的无界阻塞TransferQueue队列。相对于其他阻塞队列，LinkedTransferQueue多了tryTransfer和transfer方法。
transfer方法：如果当前有消费者正在等待接收元素（消费者使用take()或者带时间限制的poll方法时），transfer方法可以把生产者传入的元素立刻transfer给消费者，如果没有消费者在等待接收元素，transfer方法会将元素存放在队列的tail节点，并等到该元素被消费者消费了才返回。
tryTransfer方法：用来试探生产者传入的元素是否能直接传给消费者。如果没有消费者等待接收元素，则返回false。和transfer方法的区别是tryTransfer方法无论消费者是否接收，方法立刻返回，而transfer方法是必须等到消费者消费了才返回。

## Fork/Join框架

Fork/Join框架是JDK7提供的一个用于并行执行任务的框架，是一个把大任务切分为若干子任务并行的执行，最终汇总每个小任务后得到大任务结果的框架。我们再通过Fork和Join来理解下Fork/Join框架。Fork就是把一个大任务划分成为若干子任务并行的执行，Join就是合并这些子任务的执行结果，最后得到这个大任务的结果。

使用Fork/Join框架时，首先需要创建一个ForkJoin任务，它提供在任务中执行fork()和join操作的机制。通常情况下，我们不需要直接继承ForkJoinTask，只需要继承它的子类，Fork/Join框架提供了两个子类：RecursiveAction用于没有返回结果的任务；RecursiveTask用于有返回结果的任务。ForkJoinTask需要通过ForkJoinPool来执行。

任务分割出的子任务会添加到当前工作线程所维护的双端队列中，进入队列的头部。当一个工作线程的队列里暂时没有任务时，它会随机从其他工作线程的队列的尾部获取一个任务。（工作窃取算法work-stealing）

示例：计算1+2+3+…+100的结果。

```java
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer>
{
    private static final int THRESHOLD = 10;
    private int start;
    private int end;

    public CountTask(int start, int end)
    {
        super();
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute()
    {
        int sum = 0;
        boolean canCompute = (end-start) <= THRESHOLD;
        if(canCompute)
        {
            for(int i=start;i<=end;i++)
            {
                sum += i;
            }
        }
        else
        {
            int middle = (start+end)/2;
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle+1,end);
            leftTask.fork();
            rightTask.fork();
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
            sum = leftResult+rightResult;
        }

        return sum;
    }

    public static void main(String[] args)
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1,100);
        Future<Integer> result = forkJoinPool.submit(task);
        try
        {
            System.out.println(result.get());
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        if(task.isCompletedAbnormally()){
            System.out.println(task.getException());
        }
    }
}
```

## 原子类

Java中Atomic包里一共提供了12个类，属于4种类型的原子更新方式，分别是原子更新基本类型、原子更新数组、原子更新引用、原子更新属性（字段）。Atomic包里的类基本都是使用Unsafe实现的包装类。
1）原子更新基本类型：AtomicBoolean，AtomicInteger, AtomicLong.
2）原子更新数组：AtomicIntegerArray，AtomicLongArray, AtomicReferenceArray.
3）原子更新引用类型：AtomicReference, AtomicStampedReference, AtomicMarkableReference.
4 ) 原子更新字段类型：AtomicReferenceFieldUpdater, AtomicIntegerFieldUpdater, AtomicLongFieldUpdater.

## 原子更新基本类型

AtomicBoolean，AtomicInteger, AtomicLong三个类提供的方法类似，
以AtomicInteger为例：有int addAndGet(int delta),
boolean compareAndSet(int expect, int update),
int getAndIncrement(), void lazySet(int newValue),
int getAndSet(int newValue)。其中大多数的方法都是调用compareAndSet方法实现的，譬如getAndIncrement():

public final int getAndIncrement() {
    for (;;) {
        int current = get();
        int next = current + 1;
        if (compareAndSet(current, next))
            return current;
    }
}
public final boolean compareAndSet(int expect, int update) {
    return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
}
sun.misc.Unsafe只提供三种CAS方法：compareAndSwapObject,
 compareAndSwapInt和compareAndSwapLong，
 再看AtomicBoolean源码，发现它是先把Boolean转换成整形，
 再使用compareAndSwapInt进行CAS，原子更新char,float,double变量也可以用类似的思路来实现。

## 原子更新数组

以AtomicIntegerArray为例，此类主要提供原子的方式更新数组里的整形，常用方法如下：
int addAndGet(int i, int delta)：以原子的方式将输入值与数组中索引i的元素相加。
boolean compareAndSet(int i, int expect, int update)：如果当前值等于预期值，则以原子方式将数组位置i的元素设置成update值。
AtomicIntegerArray的两个构造方法：
AtomicIntegerArray(int length)：指定数组的大小，并初始化为0
AtomicIntegerArray(int [] array)：对给定的数组进行拷贝。

案例：

int value[] = new int[]{1,2,3};
AtomicIntegerArray aia = new AtomicIntegerArray(value);
System.out.println(aia.getAndSet(1, 9));
System.out.println(aia.get(1));
System.out.println(value[1]);
运行结果：2 9 2

## CyclicBarrier

让一组线程达到一个屏障时被阻塞，知道最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续运行。
CyclicBarrier默认的构造方法是CyclicBarrier(int parties)，其参数表示屏障拦截的线程数量，
每个线程调用await方法告诉CyclicBarrier我已经达到了屏障，然后当前线程被阻塞。
CyclicBarrier还提供了一个更高级的构造函数CyclicBarrier(int parties, Runnable barrierAction)用于在线程达到屏障时，
优先执行barrierAction，方便处理更复杂的业务场景，举例如下。

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest
{
    static CyclicBarrier c = new CyclicBarrier(2,new A());

    public static void main(String[] args)
    {
        new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    System.out.println(1);
                    c.await();
                }
                catch (InterruptedException | BrokenBarrierException e)
                {
                    e.printStackTrace();
                }
                System.out.println(2);
            }
        }).start();

        try
        {
            System.out.println(3);
            c.await();
        }
        catch (InterruptedException | BrokenBarrierException e)
        {
            e.printStackTrace();
        }
        System.out.println(4);
    }

    static class A implements Runnable
    {
        @Override
        public void run()
        {
            System.out.println(5);
        }
    }
}
输出结果：3 1 5 2 4

## CyclicBarrier和CountDownLatch的区别

CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置。

## Semaphore

Semaphore(信号量)是用来控制同事访问特定资源的线程数量，它协调各个线程，以保证合理的使用公共资源。
Semaphore有两个构造函数：Semaphore(int permits)默认是非公平的，
Semaphore(int permits, boolean fair)可以设置为公平的。应用案例如下：

public class SemaphoreTest
{
    private static final int THREAD_COUNT=30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(30);
    private static Semaphore s = new Semaphore(10);
 
    public static void main(String[] args)
    {
        for(int i=0;i<THREAD_COUNT;i++)
        {
            final int a = i;
            threadPool.execute(new Runnable(){
                @Override
                public void run()
                {
                    try
                    {
                        s.acquire();
                        System.out.println("do something...."+a);
                        s.release();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}

由上例可以看出Semaphore的用法非常的简单，首先线程使用Semaphore的acquire()方法获取一个许可证，
使用完之后调用release()方法归还许可证。还可以用tryAcquire()方法尝试获取许可证。
Semaphore还提供了一些其他方法： int availablePermits()返回此信号量中当前可用的许可证数；
int getQueueLength()返回正在等待获取许可证的线程数；boolean hasQueuedThreads()是否有线程正在等待获取许可证；
void reducePermits(int reduction)减少reduction个许可证，是个protected方法；
Collection<Thread> getQueuedThreads()返回所有等待获取许可证的线程集合，也是一个protected方法。

## 线程间交换数据的Exchanger

Exchanger是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交换。
它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
这两个线程通过exchange方法交换数据，如果第一个线程先执行exchange()方法，它会一直等待第二个线程也执行exchange方法。
当两个线程都到达同步点时，这两个线程就可以交换数据，将本现场生产出来的数据传递给对方。

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerTest
{
    private static final Exchanger<String> exchanger = new Exchanger<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args)
    {
        threadPool.execute(new Runnable(){
            @Override
            public void run()
            {
                String A = "I'm A!";
                try
                {
                    String B = exchanger.exchange(A);
                    System.out.println("In 1-"+Thread.currentThread().getName()+": "+B);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });

        threadPool.execute(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    String B="I'm B!";
                    String A = exchanger.exchange(B);
                    System.out.println("In 2-"+Thread.currentThread().getName()+": "+A);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        threadPool.shutdown();
    }
}
输出结果：

In 2-pool-1-thread-2: I'm A!
In 1-pool-1-thread-1: I'm B!
如果两个线程有一个没有执行exchange(V x)方法，则会一直等待，如果担心有特殊情况发生，避免一直等待，
可以使用exchange(V x, long timeout, TimeUnit unit)设置最大等待时长。

## Java中的线程池ThreadPoolExecutor

可以通过ThreadPoolExecutor来创建一个线程池：


ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)

1. corePoolSize（线程池基本大小）：当向线程池提交一个任务时，若线程池已创建的线程数小于corePoolSize，即便此时存在空闲线程，也会通过创建一个新线程来执行该任务，直到已创建的线程数大于或等于corePoolSize时，才会根据是否存在空闲线程，来决定是否需要创建新的线程。除了利用提交新任务来创建和启动线程（按需构造），也可以通过 prestartCoreThread() 或 prestartAllCoreThreads() 方法来提前启动线程池中的基本线程。
2. maximumPoolSize（线程池最大大小）：线程池所允许的最大线程个数。当队列满了，且已创建的线程数小于maximumPoolSize，则线程池会创建新的线程来执行任务。另外，对于无界队列，可忽略该参数。
3. keepAliveTime（线程存活保持时间）：默认情况下，当线程池的线程个数多于corePoolSize时，线程的空闲时间超过keepAliveTime则会终止。但只要keepAliveTime大于0，allowCoreThreadTimeOut(boolean) 方法也可将此超时策略应用于核心线程。另外，也可以使用setKeepAliveTime()动态地更改参数。
4. unit（存活时间的单位）：时间单位，分为7类，从细到粗顺序：NANOSECONDS（纳秒），MICROSECONDS（微妙），MILLISECONDS（毫秒），SECONDS（秒），MINUTES（分），HOURS（小时），DAYS（天）；
5. workQueue（任务队列）：用于传输和保存等待执行任务的阻塞队列。可以使用此队列与线程池进行交互：

如果运行的线程数少于 corePoolSize，则 Executor 始终首选添加新的线程，而不进行排队。
如果运行的线程数等于或多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不添加新的线程。
如果无法将请求加入队列，则创建新的线程，除非创建此线程超出 maximumPoolSize，在这种情况下，任务将被拒绝。
6. threadFactory（线程工厂）：用于创建新线程。由同一个threadFactory创建的线程，属于同一个ThreadGroup，创建的线程优先级都为Thread.NORM_PRIORITY，以及是非守护进程状态。threadFactory创建的线程也是采用new Thread()方式，threadFactory创建的线程名都具有统一的风格：pool-m-thread-n（m为线程池的编号，n为线程池内的线程编号）;
7. handler（线程饱和策略）：当线程池和队列都满了，则表明该线程池已达饱和状态。

ThreadPoolExecutor.AbortPolicy：处理程序遭到拒绝，则直接抛出运行时异常 RejectedExecutionException。(默认策略)
ThreadPoolExecutor.CallerRunsPolicy：调用者所在线程来运行该任务，此策略提供简单的反馈控制机制，能够减缓新任务的提交速度。
ThreadPoolExecutor.DiscardPolicy：无法执行的任务将被删除。
ThreadPoolExecutor.DiscardOldestPolicy：如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重新尝试执行任务（如果再次失败，则重复此过程）。
可以使用两个方法向线程池提交任务，分别为execute()和submit()方法。execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功。submit()方法用于提交需要返回值的任务，线程池会返回一个Future类型的对象，通过这个对象可以判断任务是否执行成功。如Future<Object> future = executor.submit(task);

利用线程池提供的参数进行监控，参数如下：

getTaskCount()：线程池需要执行的任务数量。
getCompletedTaskCount()：线程池在运行过程中已完成的任务数量，小于或等于taskCount。
getLargestPoolSize()：线程池曾经创建过的最大线程数量，通过这个数据可以知道线程池是否满过。如等于线程池的最大大小，则表示线程池曾经满了。
getPoolSize()：线程池的线程数量。如果线程池不销毁的话，池里的线程不会自动销毁，所以这个大小只增不减。
getActiveCount()：获取活动的线程数。

## shutdown和shutdownNow

可以调用线程池的shutdown或者shutdownNow方法来关闭线程池。
他们的原理是遍历线程池的工作线程，然后逐个调用线程的interrupt方法来中断线程，所以无法响应中断的任务可能永远无法停止。

区别：shutdown方法将执行平缓的关闭过程：不在接收新的任务，同时等待已提交的任务执行完成——包括哪些还未开始执行的任务。
shutdownNow方法将执行粗暴的关闭过程：它将尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务。

只要调用了这两个关闭方法中的任意一个,isShutdown方法就会返回true，当所有的任务都已关闭后，才表示线程池关闭成功，这时调用isTerminated方法会返回true。
至于应该调用哪一种方法来关闭线程池，应该由提交到线程池的任务特性决定，通常调用shutdown方法来关闭线程池，如果任务不一定要执行完，则可以调用shutdownNow方法。

## 扩展ThreadPoolExecutor

可以通过继承线程池来自定义线程池，重写线程池的beforeExecute, afterExecute和terminated方法。
在执行任务的线程中将调用beforeExecute和afterExecute等方法，在这些方法中还可以添加日志、计时、监视或者统计信息收集的功能。
无论任务是从run中正常返回，还是抛出一个异常而返回，afterExecute都会被调用。
如果任务在完成后带有一个Error，那么就不会调用afterExecute。如果beforeExecute抛出一个RuntimeException，那么任务将不被执行，并且afterExecute也不会被调用。
在线程池完成关闭时调用terminated，也就是在所有任务都已经完成并且所有工作者线程也已经关闭后，terminated可以用来释放Executor在其生命周期里分配的各种资源，
此外还可以执行发送通知、记录日志或者手机finalize统计等操作。详细可以参考[《JAVA多线程之扩展ThreadPoolExecutor》](http://blog.csdn.net/u013256816/article/details/50403962)

## SimpleDateFormat非线程安全

当多个线程共享一个SimpleDateFormat实例的时候，就会出现难以预料的异常。

主要原因是parse()方法使用calendar来生成返回的Date实例，而每次parse之前，都会把calendar里的相关属性清除掉。问题是这个calendar是个全局变量，也就是线程共享的。因此就会出现一个线程刚把calendar设置好，另一个线程就把它给清空了，这时第一个线程再parse的话就会有问题了。

解决方案:1. 每次使用时创建一个新的SimpleDateFormat实例；2. 创建一个共享的SimpleDateFormat实例变量，并对这个变量进行同步；3. 使用ThreadLocal为每个线程都创建一个独享的SimpleDateFormat实例变量。

## CopyOnWriteArrayList

在每次修改时，都会创建并重新发布一个新的容器副本，从而实现可变现。CopyOnWriteArrayList的迭代器保留一个指向底层基础数组的引用，这个数组当前位于迭代器的起始位置，由于它不会被修改，因此在对其进行同步时只需确保数组内容的可见性。因此，多个线程可以同时对这个容器进行迭代，而不会彼此干扰或者与修改容器的线程相互干扰。“写时复制”容器返回的迭代器不会抛出ConcurrentModificationException并且返回的元素与迭代器创建时的元素完全一致，而不必考虑之后修改操作所带来的影响。显然，每当修改容器时都会复制底层数组，这需要一定的开销，特别是当容器的规模较大时，仅当迭代操作远远多于修改操作时，才应该使用“写入时赋值”容器。

## 工作窃取算法（work-stealing）

工作窃取算法是指某个线程从其他队列里窃取任务来执行。在生产-消费者设计中，所有消费者有一个共享的工作队列，而在work-stealing设计中，每个消费者都有各自的双端队列，如果一个消费者完成了自己双端队列中的全部任务，那么它可以从其他消费者双端队列末尾秘密地获取工作。

优点：充分利用线程进行并行计算，减少了线程间的竞争。
缺点：在某些情况下还是存在竞争，比如双端队列（Deque）里只有一个任务时。并且该算法会消耗了更多的系统资源，比如创建多个线程和多个双端队列。

##  Future & FutureTask

FutureTask表示的计算是通过Callable来实现的，相当于一种可生产结果的Runnable，并且可以处于一下3种状态：等待运行，正在运行和运行完成。
运行表示计算的所有可能结束方式，包括正常结束、由于取消而结束和由于异常而结束等。当FutureTask进入完成状态后，它会永远停止在这个状态上。
Future.get的行为取决于任务的状态，如果任务已经完成，那么get会立刻返回结果，否则get将阻塞知道任务进入完成状态，然后返回结果或者异常。
FutureTask的使用方式如下：

public class Preloader
{
    //method1
    private final static FutureTask<Object> future = new FutureTask<Object>(new Callable<Object>(){
        @Override
        public Object call() throws Exception
        {
            return "yes";
        }
    });

    //method2
    static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Future<Object> futureExecutor = executor.submit(new Callable<Object>(){
        @Override
        public Object call() throws Exception
        {
            return "no";
        }
    });

    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        executor.shutdown();
        future.run();
        System.out.println(future.get());
        System.out.println(futureExecutor.get());
    }
}

运行结果：yes no
Callable表示的任务可以抛出受检查或未受检查的异常，并且任何代码都可能抛出一个Error.无论任务代码抛出什么异常，都会被封装到一个ExecutionException中，并在Future.get中被重新抛出。

## Executors

newFixedThreadPool：创建一个固定长度的线程池，每当提交一个任务时就创建一个线程，直到达到线程池的最大数量，这时线程池的规模将不再变化（如果某个线程由于发生了未预期的Exception而结束，那么线程池会补充一个新的线程）。（LinkedBlockingQueue）
newCachedThreadPool：创建一个可换成的线程池，如果线程池的当前规模超过了处理需求时，那么将回收空闲的线程，而当需求增加时，则可以添加新的线程，线程池的规模不存在任何限制。（SynchronousQueue）
newSingleThreadExecutor：是一个单线程的Executor，它创建单个工作者线程来执行任务，如果这个线程异常结束，会创建另一个线程来替代。能确保一组任务在队列中的顺序来串行执行。（LinkedBlockingQueue）
newScheduledThreadPool：创建了一个固定长度的线程池，而且以延迟或者定时的方式来执行任务，类似于Timer。

## ScheduledThreadPoolExecutor替代Timer

由第17项可知Timer有两个缺陷，在JDK5开始就很少使用Timer了，取而代之的可以使用ScheduledThreadPoolExecutor。使用实例如下：

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolTest
{
    private static ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);

    public static void method1()
    {
        exec.schedule(new Runnable(){
            @Override
            public void run()
            {
                System.out.println("1");
            }}, 2, TimeUnit.SECONDS);
    }

    public static void method2()
    {
        ScheduledFuture<String> future = exec.schedule(new Callable<String>(){
            @Override
            public String call() throws Exception
            {
                return "Callable";
            }}, 4, TimeUnit.SECONDS);
        try
        {
            System.out.println(future.get());
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        method1();
        method2();
    }
}
运行结果：1 Callable

## Callable & Runnable

Executor框架使用Runnable作为基本的任务表示形式。Runnable是一种有很大局限的抽象，虽然run能写入到日志文件或者将结果放入某个共享的数据结构，但它不能返回一个值或抛出一个受检查的异常。

许多任务实际上都是存在延迟的计算——执行数据库查询，从网络上获取资源，或者计算某个复杂的功能。对于这些任务，Callable是一种更好的抽象：它认为主入口点（call()）将返回一个值，并可能抛出一个异常。

Runnable和Callable描述的都是抽象的计算任务。这些任务通常是有范围的，即都有一个明确的起始点，并且最终会结束。

## CompletionService

如果想Executor提交了一组计算任务，并且希望在计算完成后获得结果，那么可以保留与每个任务关联的Future，然后反复使用get方法，同事将参数timeout指定为0，从而通过轮询来判断任务是否完成。这种方法虽然可行，但却有些繁琐。幸运的是，还有一种更好的方法：CompletionService。CompletionService将Executor和BlockingQueue的功能融合在一起。你可以将Callable任务提交给它来执行，然后使用类似于队列操作的take和poll等方法来获得已完成的结果，而这些结果会在完成时被封装为Future。ExecutorCompletionService实现了CompletionService,并将计算部分委托到一个Executor。代码示例如下：

        int coreNum = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(coreNum);
        CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor);

        for(int i=0;i<coreNum;i++)
        {
            completionService.submit( new Callable<Object>(){
                @Override
                public Object call() throws Exception
                {
                    return Thread.currentThread().getName();
                }});
        }

        for(int i=0;i<coreNum;i++)
        {
            try
            {
                Future<Object> future = completionService.take();
                System.out.println(future.get());
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }

运行结果：

pool-1-thread-1
pool-1-thread-2
pool-1-thread-3
pool-1-thread-4

可以通过ExecutorCompletionService(Executor executor, BlockingQueue<Future<V>> completionQueue)构造函数指定特定的BlockingQueue（如下代码剪辑），默认为LinkedBlockingQueue。

        BlockingQueue<Future<Object>> bq = new LinkedBlockingQueue<Future<Object>>();
        CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executor,bq);

ExecutorCompletionService的JDK源码只有100行左右，有兴趣的朋友可以看看。

## 通过Future来实现取消

ExecutorService.submit将返回一个Future来描述任务。Future拥有一个cancel方法，该方法带有一个boolean类型的参数mayInterruptIfRunning，表示取消操作是否成功。如果mayInterruptIfRunning为true并且任务当前正在某个线程运行，那么这个线程能被中断。如果这个参数为false，那么意味着“若任务还没启动，就不要运行它”，这种方式应该用于那些不处理中断的任务中。当Future.get抛出InterruptedException或TimeoutException时，如果你知道不再需要结果，那么就可以调用Futuure.cancel来取消任务。

## 处理不可中断的阻塞

对于一下几种情况，中断请求只能设置线程的中断状态，除此之外没有其他任何作用。

Java.io包中的同步Socket I/O：虽然InputStream和OutputStream中的read和write等方法都不会响应中断，但通过关闭底层的套接字，可以使得由于执行read或write等方法而被阻塞的线程抛出一个SocketException。
Java.io包中的同步I/O：当中断一个在InterruptibleChannel上等待的线程时会抛出ClosedByInterrptException并关闭链路。当关闭一个InterruptibleChannel时，将导致所有在链路操作上阻塞的线程都抛出AsynchronousCloseException。
Selector的异步I/O：如果一个线程在调用Selector.select方法时阻塞了，那么调用close或wakeup方法会使线程抛出ClosedSelectorException并提前返回。
获得某个锁：如果一个线程由于等待某个内置锁而阻塞，那么将无法响应中断，因为线程认为它肯定会获得锁，所以将不会理会中断请求，但是在Lock类中提供了lockInterruptibly方法，该方法允许在等待一个锁的同时仍能响应中断。

## 关闭钩子

JVM既可以正常关闭也可以强制关闭，或者说非正常关闭。关闭钩子可以在JVM关闭时执行一些特定的操作，譬如可以用于实现服务或应用程序的清理工作。关闭钩子可以在一下几种场景中应用：1. 程序正常退出（这里指一个JVM实例）；2.使用System.exit()；3.终端使用Ctrl+C触发的中断；4. 系统关闭；5. OutOfMemory宕机；6.使用Kill pid命令干掉进程（注：在使用kill -9 pid时，是不会被调用的）。使用方法（Runtime.getRuntime().addShutdownHook(Thread hook)）。
更多内容可以参考[JAVA虚拟机关闭钩子(Shutdown Hook)](http://blog.csdn.net/u013256816/article/details/50394923)

## 终结器finalize

终结器finalize：在回收器释放它们后，调用它们的finalize方法，从而保证一些持久化的资源被释放。在大多数情况下，通过使用finally代码块和显示的close方法，能够比使用终结器更好地管理资源。唯一例外情况在于：当需要管理对象，并且该对象持有的资源是通过本地方法获得的。但是基于一些原因（譬如对象复活），我们要尽量避免编写或者使用包含终结器的类。

## 线程工厂ThreadFactory

每当线程池（ThreadPoolExecutor）需要创建一个线程时，都是通过线程功夫方法来完成的。默认的线程工厂方法将创建一个新的、非守护的线程，并且不包含特殊的配置信息。通过指定一个线程工厂方法，可以定制线程池的配置信息。在ThreadFactory中只定义了一个方法newThread，每当线程池需要创建一个新线程时都会调用这个方法。默认的线程工厂(DefaultThreadFactory 是Executors的内部类)如下：

    static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

通过implements ThreadFactory可以定制线程工厂。譬如，你希望为线程池中的线程指定一个UncaughtExceptionHandler，或者实例化一个定制的Thread类用于执行调试信息的记录。

## synchronized与ReentrantLock之间进行选择

由第21条可知ReentrantLock与synchronized想必提供了许多功能：定时的锁等待，可中断的锁等待、公平锁、非阻塞的获取锁等，而且从性能上来说ReentrantLock比synchronized略有胜出（JDK6起），在JDK5中是远远胜出，为嘛不放弃synchronized呢？ReentrantLock的危险性要比同步机制高，如果忘记在finnally块中调用unlock，那么虽然代码表面上能正常运行，但实际上已经埋下了一颗定时炸弹，并很可能伤及其他代码。仅当内置锁不能满足需求时，才可以考虑使用ReentrantLock.

## Happens-Before规则

程序顺序规则：如果程序中操作A在操作B之前，那么在线程中A操作将在B操作之前。 
监视器锁规则：一个unlock操作现行发生于后面对同一个锁的lock操作。 
volatile变量规则：对一个volatile变量的写操作先行发生于后面对这个变量的读操作，这里的“后面”同样是指时间上的先后顺序。 
线程启动规则：Thread对象的start()方法先行发生于此线程的每一个动作。 
线程终止规则：线程的所有操作都先行发生于对此线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值等于段检测到线程已经终止执行。 
线程中断规则：线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生。 
终结器规则：对象的构造函数必须在启动该对象的终结器之前执行完成。 
传递性：如果操作A先行发生于操作B，操作B先行发生于操作C，那就可以得出操作A先行发生于操作C的结论。

注意：如果两个操作之间存在happens-before关系，并不意味着java平台的具体实现必须要按照Happens-Before关系指定的顺序来执行。如果重排序之后的执行结果，与按happens-before关系来执行的结果一致，那么这种重排序并不非法。

## as-if-serial

不管怎么重排序，程序执行结果不能被改变。

## ABA问题

ABA问题发生在类似这样的场景：线程1转变使用CAS将变量A的值替换为C，在此时，线程2将变量的值由A替换为C，又由C替换为A，然后线程1执行CAS时发现变量的值仍为A，所以CAS成功。但实际上这时的现场已经和最初的不同了。大多数情况下ABA问题不会产生什么影响。如果有特殊情况下由于ABA问题导致，可用采用AtomicStampedReference来解决，原理：乐观锁+version。可以参考下面的案例来了解其中的不同。

public class ABAQuestion
{
    private static AtomicInteger atomicInt = new AtomicInteger(100);
    private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>(100,0);

    public static void main(String[] args) throws InterruptedException
    {
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run()
            {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });

        Thread thread2 = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                System.out.println(c3);
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Thread thread3 = new Thread(new Runnable(){
            @Override
            public void run()
            {
                try
                {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp()+1);
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp()+1);
            }
        });

        Thread thread4 = new Thread(new Runnable(){
            @Override
            public void run()
            {
                int stamp = atomicStampedRef.getStamp();
                try
                {
                    TimeUnit.SECONDS.sleep(2);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp+1);
                System.out.println(c3);
            }
        });
        thread3.start();
        thread4.start();
    }
}

输出结果：true false

## 参考资料

1. 《Java多线程编程核心技术》高洪岩著。
2. 《Java并发编程的艺术》方腾飞 等著。
3. 《Java并发编程实战》Brian Goetz等著。
4. [深入JDK源码之ThreadLocal类](http://my.oschina.net/xianggao/blog/392440?fromerr=CLZtT4xC)
5. [JAVA多线程之扩展ThreadPoolExecutor](http://blog.csdn.net/u013256816/article/details/50403962)
6. [Comparable与Comparator浅析](http://blog.csdn.net/u013256816/article/details/50899416)
7. [JAVA多线程之UncaughtExceptionHandler——处理非正常的线程中止](http://blog.csdn.net/u013256816/article/details/50417822)
8. [JAVA线程间协作：Condition](http://blog.csdn.net/u013256816/article/details/50445241)
9. [JAVA线程间协作：wait.notify.notifyAll](http://blog.csdn.net/u013256816/article/details/50440123)
10. [Java中的锁](http://blog.csdn.net/u013256816/article/details/51204385)
11. [Java守护线程概述](http://blog.csdn.net/u013256816/article/details/50392298)
12. [Java SimpleDateFormat的线程安全性问题](http://zclau.com/2016/03/22/Java-SimpleDateFormat%E7%9A%84%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E6%80%A7%E9%97%AE%E9%A2%98/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io#)
13. [JAVA虚拟机关闭钩子(Shutdown Hook)](http://blog.csdn.net/u013256816/article/details/50394923)