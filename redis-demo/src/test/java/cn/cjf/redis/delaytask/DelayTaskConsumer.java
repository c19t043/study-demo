package cn.cjf.redis.delaytask;

import cn.cjf.redis.Config.Constants;
import cn.cjf.redis.utils.JedisUtil;
import redis.clients.jedis.Jedis;

import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DelayTaskConsumer {
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public void start() {
        scheduledExecutorService.scheduleWithFixedDelay(new DelayTaskHandler(), 1, 1, TimeUnit.SECONDS);
    }

    public static class DelayTaskHandler implements Runnable {

        @Override
        public void run() {
            try (Jedis client = JedisUtil.getJedis()) {
                Set<String> ids = client.zrangeByScore(Constants.DELAY_TASK_QUEUE, 0, System.currentTimeMillis(),
                        0, 1);
                if (ids == null || ids.isEmpty()) {
                    return;
                }
                for (String id : ids) {
                    Long count = client.zrem(Constants.DELAY_TASK_QUEUE, id);
                    if (count != null && count == 1) {
                        System.out.println(MessageFormat.format("发布资讯。id - {0} , timeStamp - {1} , " +
                                "threadName - {2}", id, System.currentTimeMillis(), Thread.currentThread().getName()));
                    }
                }
            }
        }
    }
}
