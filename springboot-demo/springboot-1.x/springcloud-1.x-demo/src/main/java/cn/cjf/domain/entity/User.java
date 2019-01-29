package cn.cjf.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Entity:是一个实体类，和数据库中的表名对应
 */
@Entity
@Data
public class User {
    /**
     * @Id： 表明数据库主键
     * @GeneratedValue: 表明id字段为自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * @Column: 对应数据库表中的字段
     * <p>
     * unique：表明对应表中的字段为唯一约束
     */
    @Column(nullable = false, unique = true)
    private String username;
    @Column
    private String password;
}
