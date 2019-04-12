package cn.cjf.platform.dao;

import cn.cjf.platform.domain.bo.Role;

import java.util.List;

public interface RoleDao {

    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();
}
