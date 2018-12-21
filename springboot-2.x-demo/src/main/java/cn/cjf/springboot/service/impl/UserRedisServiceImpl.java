package cn.cjf.springboot.service.impl;

import cn.cjf.springboot.entity.UserRedis;
import cn.cjf.springboot.service.UserRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRedisServiceImpl implements UserRedisService {
    private static final Map<Long, UserRedis> DATABASES = new HashMap<>();

    static {
        DATABASES.put(1L, new UserRedis(1L, "u1", "p1"));
        DATABASES.put(2L, new UserRedis(2L, "u2", "p2"));
        DATABASES.put(3L, new UserRedis(3L, "u3", "p3"));
    }

    private static final Logger log = LoggerFactory.getLogger(UserRedisServiceImpl.class);

    @CachePut(value = "user", key = "#user.id")
    @Override
    public UserRedis saveOrUpdate(UserRedis user) {
        DATABASES.put(user.getId(), user);
        log.info("进入 saveOrUpdate 方法");
        return user;
    }

    @Cacheable(value = "user", key = "#id")
    @Override
    public UserRedis get(Long id) {
        // TODO 我们就假设它是从数据库读取出来的
        log.info("进入 get 方法");
        return DATABASES.get(id);
    }

    @CacheEvict(value = "user", key = "#id")
    @Override
    public void delete(Long id) {
        DATABASES.remove(id);
        log.info("进入 delete 方法");
    }
}
