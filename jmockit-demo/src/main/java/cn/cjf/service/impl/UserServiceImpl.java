package cn.cjf.service.impl;

import cn.cjf.dao.UserDAO;
import cn.cjf.entity.po.UserBo;
import cn.cjf.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Override
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserBo getUser(Long id) {
        return userDAO.getUser(id);
    }

    @Override
    public void saveUser(UserBo user) {
        userDAO.saveUser(user);
    }
}