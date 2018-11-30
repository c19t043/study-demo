package cn.cjf.mybatis.model;

import lombok.Data;

@Data
public class SysRolePrivilege {
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 权限id
     */
    private Long privilegeId;
}
