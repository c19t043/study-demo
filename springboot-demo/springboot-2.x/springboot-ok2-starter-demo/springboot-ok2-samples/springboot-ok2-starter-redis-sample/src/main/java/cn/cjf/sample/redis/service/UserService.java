package cn.cjf.sample.redis.service;

import cn.cjf.sample.redis.domain.User;

public interface UserService {

    void saveUser(User user);

    void deleteUser(Integer id);

    User findUserById(Integer id);

}
