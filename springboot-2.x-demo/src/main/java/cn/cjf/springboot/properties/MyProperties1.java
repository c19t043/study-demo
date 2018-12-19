package cn.cjf.springboot.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Levin
 * @since 2018/4/23 0023
 */
@Component
@ConfigurationProperties(prefix = "my1")
@Data
@ToString
public class MyProperties1 {

    private int age;
    private String name;
}