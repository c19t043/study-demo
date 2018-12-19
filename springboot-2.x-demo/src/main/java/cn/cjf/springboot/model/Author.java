package cn.cjf.springboot.model;

import lombok.Data;

@Data
public class Author {
    private int age;
    private String name;
    private String email;
    // 省略 get set
}