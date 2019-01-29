package cn.cjf.ok2.domain.mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private static final long serialVersionUID = 8655851615465363473L;
    private Long id;
    private String username;
    private String password;
}