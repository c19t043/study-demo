package cn.cjf.service;

import cn.cjf.dao.UserDAO;
import cn.cjf.entity.po.UserBo;

public interface UserService {
    void setUserDAO(UserDAO userDAO);

    void saveUser(UserBo user);

    UserBo getUser(Long id);
}