package cn.cjf.ok2.timer;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 基于 ScheduledExecutorService 方式,相对的比 Timer 要好
 * 多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中有一个因任务报错没有捕获抛出的异常，
 * 其它任务便会自动终止运行，使用 ScheduledExecutorService 则可以规避这个问题
 */
public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        // 参数：1、具体执行的任务   2、首次执行的延时时间
        //      3、任务执行间隔     4、间隔时间单位
        service.scheduleAtFixedRate(() -> System.out.println("执行任务A:" + LocalDateTime.now()), 0, 3, TimeUnit.SECONDS);
    }
}