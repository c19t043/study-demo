package cn.cjf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableDiscoveryClient
@RefreshScope
public class ConfigClientApplication {

    /**
     * 本文要讲述的是用Spring Cloud Bus实现通知微服务架构的配置文件的更改。
     *
     * 我们只需要发送post请求:http://localhost:8881/actuator/bus-refresh
     * 发现config-client会重新读取配置文件
     *
     * 另外，/actuator/bus-refresh接口可以指定服务，即使用”destination”参数，
     * 比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为customers的所有服务。
     */

    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }

    @Value("${foo}")
    String foo;

    @RequestMapping(value = "/hi")
    public String hi() {
        return foo;
    }
}
