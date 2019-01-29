package cn.cjf.sample.redis.service.impl;

import cn.cjf.sample.redis.domain.RedisUser;
import cn.cjf.sample.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {
    private static final Map<Long, RedisUser> DATABASES = new HashMap<>(3);

    static {
        DATABASES.put(1L, new RedisUser(1L, "u1", "p1"));
        DATABASES.put(2L, new RedisUser(2L, "u2", "p2"));
        DATABASES.put(3L, new RedisUser(3L, "u3", "p3"));
    }

    private static final Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    /**
     * (根据方法的请求参数对其结果进行缓存)
     * 如果没有缓存，就调用真是的方法
     */
    @Cacheable(value = "user", key = "#id")
    @Override
    public RedisUser get(Long id) {
        // TODO 我们就假设它是从数据库读取出来的
        log.info("进入 get 方法");
        return DATABASES.get(id);
    }

    /**
     * (根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用)
     * 更新缓存
     */
    @CachePut(value = "user", key = "#user.id")
    @Override
    public void saveOrUpdate(RedisUser user) {
        DATABASES.put(user.getId(), user);
        log.info("进入 saveOrUpdate 方法");
    }

    /**
     * (根据条件对缓存进行清空)
     * 删除缓存
     */
    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Long id) {
        DATABASES.remove(id);
        log.info("进入 delete 方法");
    }
}
