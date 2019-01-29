package cn.cjf.security.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String uuid; //用户UUID
    private String username; //用户名
    private String password; //用户密码
    private String email; //用户邮箱
    private String telephone; //电话号码
    private String role; //用户角色
    private String image; //用户头像
    private String last_ip; //上次登录IP
    private String last_time; //上次登录时间
}