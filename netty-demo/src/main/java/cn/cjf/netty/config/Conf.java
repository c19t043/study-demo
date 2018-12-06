package cn.cjf.chat.config;

/**
 * app配合类
 *
 * @author CJF
 * @date 2018-09-19
 */
public class Conf {
    /**
     * 心跳检查间隔时间（毫秒）
     */
    public static Long heartBeatInterval;
    /**
     * 心跳检查失败次数
     */
    public static Integer heartBeatFailCount;
    /**
     * 发送请求超时时间（毫秒）
     */
    public static Long reqTimeOut;
    /**
     * 失败重试次数
     */

    public static Integer reqFailRetryCount;
    /**
     * 自建线程池大小
     */
    public static Integer myThreadPoolSize = 20;
    /**
     * 最大线程数
     */
    public static Integer myThreadPoolMaxSize = 50;
    /**
     * 空闲线程存活时间（毫秒）
     */
    public static Long myThreadTaskKeepAliveTime = 5000L;

    /**
     * 线程队列大小
     */
    public static Integer myThreadPoolQueueSize = 65335;

    public static int answerRollTimeOut;
    /**
     * 图片域名
     */
    public static String picDomain;
}
