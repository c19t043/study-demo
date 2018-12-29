package cn.cjf.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:test.properties"})
@ConfigurationProperties(prefix = "com.user")
@Data
public class UserTest {
    private String name;
    private int age;
}
