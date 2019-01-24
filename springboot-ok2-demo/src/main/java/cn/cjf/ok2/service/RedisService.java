package cn.cjf.ok2.service;

import cn.cjf.ok2.domain.redis.RedisUser;

public interface RedisService {

    RedisUser get(Long id);

    void saveOrUpdate(RedisUser user);

    void delete(Long id);
}
