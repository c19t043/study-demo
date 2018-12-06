package cn.cjf.thread.demo2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(),"WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(),"NotifyThread");
        notifyThread.start();
    }
    static class Wait implements Runnable{
        @Override
        public void run() {
            //加锁，拥有lock的monitor
            synchronized (lock){
                //当条件不满足时，继续wait，同时释放了lock的锁
                while (flag) {
                    System.out.println(Thread.currentThread() + " flag is true. wait" +
                            " @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //条件满足时，完成工作
                System.out.println(Thread.currentThread() + " flag is false. running" +
                        " @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }
    static class Notify implements Runnable{
        @Override
        public void run() {
            //加锁，拥有lock的monitor
            synchronized (lock){
                //获取lock的锁，然后进行通知，通知时不会释放Lockd的锁
                //直到当前线程释放了lock后，WaitThread才能从wait方法中返回
                System.out.println(Thread.currentThread()+" hold lock. notify" +
                        " @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notifyAll();
                flag=false;
                SleepUtils.seconds(5);
            }
            //再次加锁
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock again. sleep" +
                        " @ " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.seconds(5);
            }
        }
    }
}
/**
 *
 * 1、使用wait()、notify()、和notifyAll()时需要先对调用对象加锁
 * 2、调用wait()方法后，线程状态有RUNNING变为WAITING，并将当前线程放置到对象的等待队列
 * 3、notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，
 * 需要调用notify()或notifyAll()的线程释放锁后，等待线程才有机会从wait()放回。
 * 4、notify()方法将等待队列中的一个等待线程从等待队列中移到同步队列中，而notifyAll()
 * 方法则是将等待队列中所有的线程全部移动到同步队列，被移动的线程状态由WAITING变为BLOCKED
 * 5、从wait()方法返回的前提是获得了调用对象的锁。
 *
 *
 */

/**
 *
 * 等待方遵循的原则
 * 1、获取对象的锁
 * 2、如果条件不满足，那么调用对象的wait()方法，被通知后仍要检查条件
 * 3、条件满足则执行对应的逻辑。
 *
 * 通知方遵循的原则
 * 1、获得对象的锁
 * 2、改变条件
 * 3、通知所有等待在对象上的线程
 */
