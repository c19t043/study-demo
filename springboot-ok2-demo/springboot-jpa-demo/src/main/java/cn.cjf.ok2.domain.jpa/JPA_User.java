package cn.cjf.ok2.domain.jpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_user")
@Data
@NoArgsConstructor
public class JPA_User implements Serializable {

    public JPA_User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public JPA_User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private static final long serialVersionUID = 8655851615465363473L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    /**
     * TODO 忽略该字段的映射
     */
    @Transient
    private String email;

    // TODO  省略get set
}