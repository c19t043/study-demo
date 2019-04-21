package cn.cjf.shiro.dao;

import cn.cjf.shiro.domain.Permission;

import java.util.List;

public interface IPermissionDAO {

    /**
     * 保存权限对象
     *
     * @param permission
     */
    void save(Permission permission);

    /**
     * 获取员工的权限表达式
     *
     * @param userId
     * @return
     */
    List<String> getPermissionResourceByUserId(Integer userId);


    List<String> getAllResources();
}
