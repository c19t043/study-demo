预检请求的回应
服务器收到预检请求后，做出回应:
HTTP/1.1 200 OK
Date: Mon, 01 Dec 2008 01:15:39 GMT
Server: Apache/2.0.61 (Unix)
Access-Control-Allow-Origin: http://api.bob.com
Access-Control-Allow-Methods: GET, POST, PUT
Access-Control-Allow-Headers: X-Custom-Header
Content-Type: text/html; charset=utf-8
Content-Encoding: gzip
Content-Length: 0
Keep-Alive: timeout=2, max=100
Connection: Keep-Alive
Content-Type: text/plain
复制代码下面总结下，预检请求下的回应的与CORS相关的请求头:

Access-Control-Allow-Methods: 逗号分隔的字符串，表明服务器支持的所有跨域请求的方法。注意是所有方法，不是单个浏览器请求时的那个方法，这是为了避免多次 "预检"请求
Access-Control-Allow-Headers：如果浏览器请求包括 Access-Control-Request-Headers字段，则 Access-Control-Allow-Headers是必须的，它表明服务器支持的所有头信息字段，不限于浏览器再预检中请求的字段
Access-Control-Max-Age: 该字段可选，用来指定本次预检请求的有效期，单位为秒。

实现 CORS 跨域请求的方式
对于 CORS的跨域请求，主要有以下几种方式可供选择：

返回新的CorsFilter
重写 WebMvcConfigurer
使用注解 @CrossOrigin
手动设置响应头 (HttpServletResponse)

注意:

CorFilter / WebMvConfigurer / @CrossOrigin 需要 SpringMVC 4.2以上版本才支持，对应于springBoot 1.3版本以上
上面前两种方式属于全局 CORS 配置，后两种属性局部 CORS配置。如果使用了局部跨域是会覆盖全局跨域的规则，所以可以通过 @CrossOrigin 注解来进行细粒度更高的跨域资源控制

## 1.返回新的 CorsFilter(全局跨域)
在任意配置类，返回一个 新的 CorsFIlter Bean ，并添加映射路径和具体的CORS配置路径。
```
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        //1. 添加 CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //是否发送 Cookie
        config.setAllowCredentials(true);
        //放行哪些请求方式
        config.addAllowedMethod("*");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //暴露哪些头部信息
        config.addExposedHeader("*");
        //2. 添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",config);
        //3. 返回新的CorsFilter
        return new CorsFilter(corsConfigurationSource);
    }
}
```

## 2. 重写 WebMvcConfigurer(全局跨域)

```
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOrigins("*")
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
```

## 3. 使用注解 (局部跨域)
在控制器上使用注解 @CrossOrigin:

```
@RestController
@CrossOrigin(origins = "*")
public class HelloController {
    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
```
在方法上使用注解 @CrossOrigin:
```
@RequestMapping("/hello")
@CrossOrigin(origins = "*")
public String hello() {
    return "hello world";
}

```

## 4. 手动设置响应头(局部跨域)
使用 HttpServletResponse 对象添加响应头(Access-Control-Allow-Origin)来授权原始域，这里 Origin的值也可以设置为 "*",表示全部放行。

```
@RequestMapping("/index")
public String index(HttpServletResponse response) {
    response.addHeader("Access-Allow-Control-Origin","*");
    return "index";
}

```