package cn.cjf.mybatis.model;

import lombok.Data;

@Data
public class SysPrivilege {
    /**
     * 权限id
     */
    private Long id;
    /**
     * 权限名称
     */
    private String privilege_name;
    /**
     * 权限url
     */
    private String privilege_url;
}
