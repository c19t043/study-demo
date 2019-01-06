package cn.cjf.dao;

import cn.cjf.entity.po.UserBo;

public interface UserDAO {
    void saveUser(UserBo user);

    UserBo getUser(Long id);
}    