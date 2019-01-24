package cn.cjf.springboot.dao;

import cn.cjf.springboot.bean.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {
    @Select("SELECT * FROM USERS ")
    List<User> findUserList();
}