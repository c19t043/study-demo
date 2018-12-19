package cn.cjf.thread.demo2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockUseCase {
    /**
     * Lock接口提供的synchronized关键字不具备的主要特性
     * 尝试非阻塞地获取锁：
     * 当前线程尝试获取锁，如果这一时刻锁没有被其他线程获取到，
     *      则成功获取并持有锁
     * 能被中断地获取锁：
     * 与synchronized不同，获取到锁的线程能够响应中断，
     *      当获取到锁的线程被中断时，中断异常将会被抛出，同时锁会被释放
     * 超时获取锁：
     * 在指定的截止时间之前获取锁，如果截止时间到了仍旧无法获取锁，则返回
     */
    /**
     * Lock是一个接口，它定义了锁获取和释放的基本操作，Lock的API所示
     * void lock()：
     * 获取锁，调用该方法当前线程将会获取锁，当锁获得后，从该方法返回
     * void lockInterruptibly() throws InterruptedException：
     * 可中断地获取锁，
     * 和lock()方法的不同之处在于该方法会响应中断，即在锁的获取中可用中断当前线程
     * boolean tryLock()：
     * 尝试非阻塞地获取锁，调用该方法后立即返回，如果能够获取
     * 则返回true,否则返回false
     * boolean tryLock(long time,TimeUnit unit)throws InterruptedException:
     * 超时的获取锁，当前线程在以下3种情况下会返回：
     * 1、当前线程在超时时间内获得了锁
     * 2、当前线程在超时时间内被中断
     * 3、超时时间结束，返回false
     * void unlock()：
     * 释放锁
     * Condition newCondition()：
     * 获取等待通知组件，该组件和当前的锁绑定，当前线程
     * 只有获得了锁，才能调用该组件的wait()方法，而调用后，当前线程将释放锁
     */
    public void test1() {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }

    /**
     * 同步器提供3个方法来访问或修改同步状态
     * getState()：获取当前同步状态
     * setState(): 设置当前同步状态
     * compareAndSetState(int expect,int update):
     * 使用CAS设置当前状态，该方法能够保证状态设置的原子性
     * <p>
     * 同步器可重写的方法与描述
     * protected boolean tryAcquire(int arg):
     * 独占式获取同步状态，实现该方法需要查询当前状态并判断同步状态是否符合预期，
     * 然后再进行CAS设置同步状态
     * protected boolean tryRelease(int arg):
     * 独占式释放同步状态，等待获取同步状态的线程将有机会获取同步状态
     * protected int tryAcquireShared(int arg):
     * 共享式获取同步状态，返回大于等于0的值，标识获取成功，反之获取失败
     * protected boolean tryReleaseShared(int arg):
     * 共享式释放同步状态
     * protected boolean isHeldExclusively():
     * 当前同步器是否在独占模式下被线程占用，一般改方法表示是否被当前线程独占
     * <p>
     * 同步器提供的模板方法
     * void acquire(int arg):
     * 独占式获取同步状态，如果当前线程获取同步状态成功，则由该方法返回，
     * 否则，将会进入同步队列等待，该方法将会调用重写的tryAcquire(int arg)方法
     * void acquireInterruptibly(int arg):
     * 与acquire(int arg)相同，但是该方法响应中断，当前线程未获取到同步状态而进入
     * 同步队列中，如果当前线程被中断，则该方法会抛出InterruptedException并返回
     * boolean tryAcquireNanos(int arg,long nanos):
     * 在acquireInterruptibly(int arg)基础上增加了超时限制，如果当前线程在超时
     * 时间内没有获取到同步状态，那么将会返回false，如果获取到了返回true
     * void acquireShared(int arg):
     * 共享式的获取同步状态，如果当前线程未获取道同步状态，将会进入同步队列等待，
     * 与独占式获取的主要区别是同一时刻可以有多个线程获取到同步状态
     * void acquireSharedInterruptibly(int arg):
     * 与acquireShared(int arg)相同，该方法响应中断
     * boolean tryAcquireSharedNanos(int arg,long nanos):
     * 在acquireSharedInterruptibly(int arg)基础上增加了超时限制
     * boolean release(int arg):
     * 独占式的释放同步状态，该方法会在释放同步状态之后，将同步队列中第一个节点包含
     * 的线程唤醒
     * boolean releaseShared(int arg):
     * 共享式的释放同步状态
     * Collection<Thread> getQueuedThreads():
     * 获取等待在同步队列上的线程集合
     */
    public void test2() {

    }
}
