package cn.cjf;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient
@RestController
/**
 * 注解开启断路器，这个是必须的
 */
@EnableHystrix
/**
 * 开启HystrixDashboard
 * 打开locahost:8762/hystrix 可以看见以下界面
 * 在界面依次输入：http://localhost:8762/actuator/hystrix.stream 、2000 、miya ；点确定。
 * 在另一个窗口输入： http://localhost:8762/hi?name=forezp
 * 重新刷新hystrix.stream网页，你会看到良好的图形化界面：
 */
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ServiceHiApplication {


    private static final Logger LOG = Logger.getLogger(ServiceHiApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(ServiceHiApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String home(@RequestParam(value = "name", defaultValue = "forezp") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}