package cn.cjf.redis.delaytask;

import cn.cjf.redis.Config.Constants;
import cn.cjf.redis.utils.JedisUtil;
import redis.clients.jedis.Jedis;

public class DelayTaskProducer {
    /**
     * @param newsId    资讯的id
     * @param timeStamp 任务执行的时间戳
     */
    public void produce(String newsId, long timeStamp) {
        try (Jedis client = JedisUtil.getJedis()) {
            client.zadd(Constants.DELAY_TASK_QUEUE, timeStamp, newsId);
        }
    }
}
