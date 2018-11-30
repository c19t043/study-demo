package cn.cjf.mybatis.model;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 简介
     */
    private String userInfo;
    /**
     * 头像
     */
    private Byte[] headImg;
    /**
     * 创建时间
     */
    private Date createTime;
}
