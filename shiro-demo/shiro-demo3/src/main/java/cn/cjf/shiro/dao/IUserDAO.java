package cn.cjf.shiro.dao;

import cn.cjf.shiro.domain.User;

public interface IUserDAO {
    /**
     * 通过用户名查找用户对象
     * @param username
     * @return
     */
    User getUserByUsername(String username);
}
