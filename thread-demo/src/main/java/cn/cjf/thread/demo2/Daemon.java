package cn.cjf.thread.demo2;

public class Daemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRuuner()
                , "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
        //守护线程的finally代码块不一定执行
        //在构建Daemon线程时，不能依靠finally块中内容确保执行关闭或清理资源的逻辑
    }

    static class DaemonRuuner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.seconds(10);
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
