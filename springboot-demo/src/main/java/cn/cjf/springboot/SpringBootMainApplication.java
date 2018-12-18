package cn.cjf.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 标记这是springboot的主程序
 */
@SpringBootApplication
public class SpringBootMainApplication {

    public static void main(String[] args) {
        //启动springboot应用
        SpringApplication.run(SpringBootApplication.class,args);
    }
}
