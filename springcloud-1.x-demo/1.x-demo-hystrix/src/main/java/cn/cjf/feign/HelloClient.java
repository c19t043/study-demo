package cn.cjf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "spring-hystrix-provider", fallback = HelloClient.HelloClientFallback.class)
public interface HelloClient {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String hello();

    @Component
    class HelloClientFallback implements HelloClient {

        public String hello() {
            return "error hello";
        }
    }
}
