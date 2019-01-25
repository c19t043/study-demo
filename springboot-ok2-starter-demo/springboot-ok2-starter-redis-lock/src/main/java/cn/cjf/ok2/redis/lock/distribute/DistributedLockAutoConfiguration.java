package cn.cjf.ok2.redis.lock.distribute;

import cn.cjf.ok2.redis.lock.distribute.keygenerator.CacheKeyGenerator;
import cn.cjf.ok2.redis.lock.distribute.keygenerator.LockKeyGenerator;
import cn.cjf.ok2.redis.lock.distribute.lock.DistributedLock;
import cn.cjf.ok2.redis.lock.distribute.lock.RedisDistributedLock;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class DistributedLockAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public DistributedLock redisDistributedLock(RedisTemplate<Object, Object> redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }

    @Bean
    public CacheKeyGenerator lockKeyGenerator() {
        return new LockKeyGenerator();
    }
}
