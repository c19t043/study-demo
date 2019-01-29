package cn.cjf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
// 启动Zipkin服务
@Enable
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
