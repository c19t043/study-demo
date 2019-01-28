package cn.cjf.ok2.redis.lock.distribute.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisDistributedLock extends AbstractDistributedLock {

    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String value = "";

    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    @Override
    public boolean tryLock(String key, long expire, int retryTimes, long timeout, TimeUnit timeUnit) {
        boolean result;
        try {
            do {
                result = setRedis(key, expire, timeUnit);
                if (!result && timeout > 0) {
                    timeout = TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
                    Thread.sleep(timeout);
                }
            } while ((!result) && retryTimes-- > 0);
        } catch (InterruptedException e) {
            return false;
        }
        return result;
    }

    private boolean setRedis(String key, long expire, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, expire, timeUnit);

            Boolean success = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                String uuid = UUID.randomUUID().toString();
                lockFlag.set(uuid);
                return connection.set(key.getBytes(),
                        new byte[0],
                        Expiration.from(expire, timeUnit),
                        RedisStringCommands.SetOption.SET_IF_ABSENT);
            });
            return success;
        } catch (Exception e) {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {
        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("release lock occured an exception", e);
        } finally {
            // 清除掉ThreadLocal中的数据，避免内存溢出
            lockFlag.remove();
        }
        return false;
    }

}
