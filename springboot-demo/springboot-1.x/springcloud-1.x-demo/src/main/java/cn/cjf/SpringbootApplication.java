package cn.cjf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableConfigurationProperties(value = {MyInfo.class})
public class SpringbootApplication {

    @Value("${my.name}")
    String name;

    @Value("${my.age}")
    int age;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
