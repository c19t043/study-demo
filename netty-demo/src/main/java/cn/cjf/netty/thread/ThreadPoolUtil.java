package cn.cjf.netty.thread;

import cn.cjf.netty.config.Conf;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理工具
 *
 * @author CJF
 * @date 2018-09-19
 */
public class ThreadPoolUtil {
    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 线程池初始化
     *
     * @param corePoolSize   核心线程数
     * @param maxNumPoolSize 最大线程数
     * @param keepAliveTime  空闲线程存活时间
     * @param queueLength    线程队列大小
     */
    private void init(int corePoolSize, int maxNumPoolSize, long keepAliveTime, int queueLength) {
        if (threadPoolExecutor == null) {
            ThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("netty-chat_ThreadPoolUtil_%d").daemon(true).build();
            threadPoolExecutor =
                    new ThreadPoolExecutor(corePoolSize, maxNumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(queueLength), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        }
    }

    /**
     * 提交任务
     *
     * @param runnable 线程任务
     */
    public void executeTask(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    /**
     * 提交任务
     *
     * @param callable 线程任务
     */
    public Future<?> submitTask(Callable<?> callable) {
        return threadPoolExecutor.submit(callable);
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }

    private ThreadPoolUtil() {
        init(Conf.myThreadPoolSize, Conf.myThreadPoolMaxSize, Conf.myThreadTaskKeepAliveTime, Conf.myThreadPoolQueueSize);
    }

    private static class SingleHolder {
        static ThreadPoolUtil instance;

        static {
            instance = new ThreadPoolUtil();
        }

        public static ThreadPoolUtil getInstance() {
            return instance;
        }
    }

    public static ThreadPoolUtil getInstance() {
        return ThreadPoolUtil.SingleHolder.getInstance();
    }
}
