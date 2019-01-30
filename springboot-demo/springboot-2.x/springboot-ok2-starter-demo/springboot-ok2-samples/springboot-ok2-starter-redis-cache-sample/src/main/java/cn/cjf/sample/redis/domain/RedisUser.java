package cn.cjf.sample.redis.domain;

import java.io.Serializable;

public class RedisUser implements Serializable {
    public RedisUser() {
    }

    public RedisUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private static final long serialVersionUID = 8655851615465363473L;
    private Long id;
    private String username;
    private String password;
    // TODO  省略get set


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}