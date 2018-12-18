package cn.cjf.thread.demo2;

import java.util.concurrent.TimeUnit;

public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            //每个线程拥有前一个线程的引用，需要等待前一个线程终止，才能从等待中返回
            Thread thread = new Thread(new Demino(previous), String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Thread.currentThread().getName()" + " terminate.");
    }

    static class Demino implements Runnable {
        private Thread thread;

        public Demino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread.currentThread().getName()" + " terminate.");
        }
    }
//    main terminate.
//0 terminate.
//1 terminate.
//2 terminate.
//3 terminate.
//4 terminate.
//5 terminate.
//6 terminate.
//7 terminate.
//8 terminate.
//9 terminate.
    /**
     * 从上述输出可以看到，每个线程终止的前提是前驱线程的终止，每个线程等待前驱线程
     * 终止后，才从Join()方法返回
     */
}
