package cn.cjf.service;

import cn.cjf.dao.UserJpaDao;
import cn.cjf.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserJpaDao userDao;

    public User findUserByName(String username) {
        return userDao.findUserByUsername(username);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public User findUserById(Long id) {
        return userDao.findOne(id);
    }

    public User updateUser(User user) {
        return userDao.saveAndFlush(user);
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }

}
