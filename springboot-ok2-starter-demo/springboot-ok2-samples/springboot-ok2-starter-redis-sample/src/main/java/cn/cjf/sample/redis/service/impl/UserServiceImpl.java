package cn.cjf.sample.redis.service.impl;

import cn.cjf.sample.redis.domain.User;
import cn.cjf.sample.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("redisCacheTemplateWithLettuce")
    private RedisTemplate<String, Serializable> redisTemplate;

    @Override
    public void saveUser(User user) {
        System.out.println("保存user对象");
        String key = "user:" + user.getId();
        redisTemplate.opsForValue().set(key, user);
    }

    @Override
    public void deleteUser(Integer id) {
        System.out.println("删除user对象：" + id);
        String key = "user:" + id;
        redisTemplate.delete(key);
    }

    @Override
    public User findUserById(Integer id) {
        System.out.println("查找user对象:" + id);
        String key = "user:" + id;
        return (User) redisTemplate.opsForValue().get(key);
    }
}
