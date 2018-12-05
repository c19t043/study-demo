package cn.cjf.thread.demo2;

public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(),"TimeWaitingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        //使用两个blocked线程，一个获取锁成功，另一个被阻塞
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();

        //BlockedThread-2线程获取到了Blocked.class的锁
//        "BlockedThread-2" #15 prio=5 os_prio=0 tid=0x000000001f40a000 nid=0x36ac waiting on condition [0x000000001ffcf000]
//        java.lang.Thread.State: TIMED_WAITING (sleeping)
//
        //BlockedThread-1线程阻塞在获取Blocked.class实例的锁上
//        "BlockedThread-1" #14 prio=5 os_prio=0 tid=0x000000001e9ce800 nid=0x944 waiting for monitor entry [0x000000001fece000]
//        java.lang.Thread.State: BLOCKED (on object monitor)
//
        //WaitingThread线程在Waiting实例上等待
//        "WaitingThread" #13 prio=5 os_prio=0 tid=0x000000001f411000 nid=0x2274 in Object.wait() [0x000000001fdcf000]
//        java.lang.Thread.State: WAITING (on object monitor)
//
        //TimeWaitingThread线程处于超时等待
//        "TimeWaitingThread" #12 prio=5 os_prio=0 tid=0x000000001f3e8800 nid=0xdb8 waiting on condition [0x000000001fccf000]
//        java.lang.Thread.State: TIMED_WAITING (sleeping)
    }

    //该线程不断地及逆行睡眠
    static class TimeWaiting implements Runnable{
        @Override
        public void run() {
            while (true){
                SleepUtils.seconds(100);
            }
        }
    }
    //该线程在Waiting.class实例上等待
    static class Waiting implements Runnable{
        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable{
        @Override
        public void run() {
            synchronized (Blocked.class){
                while (true){
                    SleepUtils.seconds(100);
                }
            }
        }
    }
}
