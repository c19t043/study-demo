package cn.cjf.thread.demo2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockUseCase {
    /**
     * Lock接口提供的synchronized关键字不具备的主要特性
     * 尝试非阻塞地获取锁：当前线程尝试获取锁，如果这一时刻锁没有被其他线程获取到，
     *      则成功获取并持有锁
     * 能被中断地获取锁：与synchronized不同，获取到锁的线程能够响应中断，
     *      当获取到锁的线程被中断时，中断异常将会被抛出，同时锁会被释放
     * 超时获取锁：在指定的截止时间之前获取锁，如果截止时间到了仍旧无法获取锁，则返回
     */
    /**
     * Lock是一个接口，它定义了锁获取和释放的基本操作，Lock的API所示
     * void lock()：获取锁，调用该方法当前线程将会获取锁，当锁获得后，从该方法返回
     * void lockInterruptibly() throws InterruptedException：可中断地获取锁，
     *      和lock()方法的不同之处在于该方法会响应中断，即在锁的获取中可用中断当前线程
     * boolean tryLock()：尝试非阻塞地获取锁，调用该方法后立即返回，如果能够获取
     *      则返回true,否则返回false
     * boolean tryLock(long time,TimeUnit unit)throws InterruptedException:
     *  超时的获取锁，当前线程在以下3种情况下会返回：
     *      1、当前线程在超时时间内获得了锁
     *      2、当前线程在超时时间内被中断
     *      3、超时时间结束，返回false
     *  void unlock()：释放锁
     *  Condition newCondition()：获取等待通知组件，该组件和当前的锁绑定，当前线程
     *      只有获得了锁，才能调用该组件的wait()方法，而调用后，当前线程将释放锁
     */
    public void test1() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
