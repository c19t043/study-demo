package cn.cjf.springboot.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Levin
 * @since 2018/4/23 0023
 */
@Component
//@PropertySource("classpath:my2.properties")
@ConfigurationProperties(prefix = "my2")
@Data
@ToString
public class MyProperties2 {

    private int age;
    private String name;
    private String email;
}