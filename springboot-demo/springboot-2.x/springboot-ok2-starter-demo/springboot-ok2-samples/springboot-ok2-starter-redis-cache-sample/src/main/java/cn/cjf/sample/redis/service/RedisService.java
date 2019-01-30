package cn.cjf.sample.redis.service;


import cn.cjf.sample.redis.domain.RedisUser;

public interface RedisService {

    RedisUser get(Long id);

    void saveOrUpdate(RedisUser user);

    void delete(Long id);
}
