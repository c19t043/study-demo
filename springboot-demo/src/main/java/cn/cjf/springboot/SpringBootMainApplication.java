package cn.cjf.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 标记这是springboot的主程序
 */
@SpringBootApplication
public class SpringBootMainApplication {

    public static void main(String[] args) {
        //启动springboot应用
//        SpringApplication.run(SpringBootApplication.class,args);

        //修改默认配置文件
        new SpringApplicationBuilder(SpringBootMainApplication.class)
                .properties("spring.config.location=classpath:application.yml")
                .properties("spring.profiles.active=true")
                .run(args);
    }

}
