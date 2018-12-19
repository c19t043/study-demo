package cn.cjf.springboot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * JPA规范注解坐落在javax.persistence包下，@Id注解一定不要引用错了，否则会报错。
 *
 * @GeneratedValue(strategy = GenerationType.IDENTITY)自增策略，不需要映射的字段可以通过@Transient注解排除掉
 * <p>
 * 常见的几种自增策略
 * TABLE： 使用一个特定的数据库表格来保存主键
 * SEQUENCE： 根据底层数据库的序列来生成主键，条件是数据库支持序列。
 * 这个值要与generator一起使用，generator 指定生成主键使用的生成器（可能是orcale中自己编写的序列）。
 * IDENTITY： 主键由数据库自动生成（主要是支持自动增长的数据库，如mysql）
 * AUTO： 主键由程序控制，也是GenerationType的默认值。
 */
@Data
@NoArgsConstructor
/**
 * mybatis时注解
 */
@Table(name = "t_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Transient
    private String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}

