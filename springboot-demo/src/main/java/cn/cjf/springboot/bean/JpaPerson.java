package cn.cjf.springboot.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class JpaPerson implements Serializable{
    private Integer id;
    private String name;
}
