package cn.cjf.service;

import cn.cjf.config2.FeignConfig;
import cn.cjf.hystrix.HiHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feign在远程调用失败后会进行重试
 * FeignClientsConfiguration:默认配置类
 */
@FeignClient(value = "eureka-client",
        configuration = FeignConfig.class,
        fallback = HiHystrix.class)
public interface EurekaClientFeign {


    @GetMapping(value = "/hi")
    String sayHiFromClientEureka(@RequestParam(value = "name") String name);
}
