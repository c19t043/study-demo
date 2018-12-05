package cn.cjf.thread.demo2;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MultiThread {
    public static void main(String[] args) {
        //调用java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
        }

//        [6]Monitor Ctrl-Break
//        [5]Attach Listener
        //发送给JVM信号的线程
//        [4]Signal Dispatcher
        //调用对象finalizer方法的线程
//        [3]Finalizer
        //清除Reference的线程
//        [2]Reference Handler
        //main线程，用户程序入口
//        [1]main
    }
}
