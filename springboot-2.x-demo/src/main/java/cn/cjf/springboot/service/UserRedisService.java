package cn.cjf.springboot.service;

import cn.cjf.springboot.entity.UserRedis;

public interface UserRedisService {
    /**
     * 删除
     *
     * @param user 用户对象
     * @return 操作结果
     */
    UserRedis saveOrUpdate(UserRedis user);

    /**
     * 添加
     *
     * @param id key值
     * @return 返回结果
     */
    UserRedis get(Long id);

    /**
     * 删除
     *
     * @param id key值
     */
    void delete(Long id);
}
