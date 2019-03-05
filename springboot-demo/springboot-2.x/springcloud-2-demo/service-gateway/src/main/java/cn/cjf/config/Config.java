package cn.cjf.config2;

import cn.cjf.resolver.HostAddrKeyResolver;
import cn.cjf.resolver.UriKeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class Config {

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

    @Bean
    public UriKeyResolver uriKeyResolver() {
        return new UriKeyResolver();
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
    }
}
