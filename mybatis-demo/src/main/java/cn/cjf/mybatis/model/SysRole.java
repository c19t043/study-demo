package cn.cjf.mybatis.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysRole {
    /**
     * 角色id
     */
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 有效标志
     */
    private Integer enabled;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
}
