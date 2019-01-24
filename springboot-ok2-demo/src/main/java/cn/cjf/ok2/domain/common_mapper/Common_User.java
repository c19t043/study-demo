package cn.cjf.ok2.domain.common_mapper;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_user")
@Data
@NoArgsConstructor
public class Common_User implements Serializable {
    public Common_User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Common_User(Long id, String username, String password) {
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
    // TODO  省略get set
}