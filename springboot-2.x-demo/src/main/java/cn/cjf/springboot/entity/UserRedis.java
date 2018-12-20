package cn.cjf.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRedis implements Serializable{
    private static final long serialVersionUID = 8655851615465363473L;
    private Long id;
    private String username;
    private String password;
}
