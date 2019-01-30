package cn.cjf.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 指定服务的负载均衡规则
 */
@Component
@RibbonClients(value = {
        @RibbonClient(name = "hi-service", configuration = RandomRule.class),
        @RibbonClient(name = "miya-service", configuration = RoundRobinRule.class)})
public class Configuration {
    /**
     * 修改默认负载均衡策略
     * @return
     */
    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
}
