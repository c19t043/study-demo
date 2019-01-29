package cn.cjf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Mono是一个Reactive stream，对外输出一个“fallback”字符串。
     */
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

    /**
     * 如果有很多个Predicate，并且一个请求满足多个Predicate，则按照配置的顺序第一个生效。
     * <p>
     * 使用了一个RouteLocatorBuilder的bean去创建路由，
     * 除了创建路由RouteLocatorBuilder可以让你添加各种predicates和filters，predicates断言的意思，
     * 顾名思义就是根据具体的请求的规则，由具体的route去处理，filters是各种过滤器，用来对请求做各种判断和修改。
     * <p>
     * 创建的route可以让请求“/get”请求都转发到“httpbin.org/get”
     * 在route配置上，我们添加了一个filter，该filter会将请求添加一个header,key为hello，value为world。
     * <p>
     * 在spring cloud gateway中可以使用Hystrix。Hystrix是 spring cloud中一个服务熔断降级的组件，
     * 在微服务系统有着十分重要的作用。 Hystrix是 spring cloud gateway中是以filter的形式使用的，
     * <p>
     * 我们使用了另外一个router，该router使用host去断言请求是否进入该路由，
     * 当请求的host有“*.hystrix.com”，都会进入该router，该router中有一个hystrix的filter,
     * 该filter可以配置名称、和指向性fallback的逻辑的地址，比如本案例中重定向到了“/fallback”。
     */
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }
//    {
//        "args": {},
//        "headers": {
//        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
//                "Accept-Encoding": "gzip, deflate, br",
//                "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
//                "Cache-Control": "max-age=0",
//                "Connection": "close",
//                "Cookie": "_ga=GA1.1.412536205.1526967566; JSESSIONID.667921df=node01oc1cdl4mcjdx1mku2ef1l440q1.node0; screenResolution=1920x1200",
//                "Forwarded": "proto=http;host=\"localhost:8080\";for=\"0:0:0:0:0:0:0:1:60036\"",
//                "Hello": "World",
//                "Host": "httpbin.org",
//                "Upgrade-Insecure-Requests": "1",
//                "User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36",
//                "X-Forwarded-Host": "localhost:8080"
//    },
//        "origin": "0:0:0:0:0:0:0:1, 210.22.21.66",
//            "url": "http://localhost:8080/get"
//    }
}