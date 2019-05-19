# spring mvc

## 简单的谈一下SpringMVC的工作流程？

1. 用户发送请求至前端控制器DispatcherServlet
2. DispatcherServlet收到请求调用HandlerMapper处理器映射器
3. 处理器映射器找到具体的处理器，生成处理器对象及处理器拦截器（如果有则生成）一并返回给DispatcherServlet。
4. DispatcherServlet调用HandlerAdapter处理器适配器
5. HandlerAdapater经过适配调用具体的处理器(Controller，也叫后端控制器)
6. Controller执行完毕返回ModelAndView
7. HandleAdapter将Controller执行结果ModelAndView返回给DispatcherServlet
8. DispatcherServlet将ModelAndVie传给ViewResolver视图解析器
9. ViewResolver解析后返回具体View
10. DispatcherServlet根据View进行视图渲染（即将模型数据填充到视图中）
11. DispatcherServlet响应用户

## HandleExecutionChain

处理器执行链由处理器对象和处理器拦截器组成
HandlerMapping会把请求映射为HandlerExecutionChain类型的handler对象;

## 如何解决POST请求中文乱码问题，GET的又如何处理呢？

```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>utf-8</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

以上可以解决post请求乱码问题。对于get请求中文参数出现乱码解决方法有两个：
修改tomcat配置文件添加编码与工程编码一致，如下：

<ConnectorURIEncoding="utf-8" connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>

 另外一种方法对参数进行重新编码：

String userName = new String(request.getParamter("userName").getBytes("ISO8859-1"),"utf-8")

ISO8859-1是tomcat默认编码，需要将tomcat编码后的内容按utf-8编码

## SpringMVC与Struts2的主要区别？

1. springmvc的入口是一个servlet即前端控制器，而struts2入口是一个filter过虑器。
2. springmvc是基于方法开发，传递参数是通过方法形参，可以设计为单例或多例(建议单例)，struts2是基于类开发，传递参数是通过类的属性，只能设计为多例。
3. springmvc通过参数解析器是将request对象内容进行解析成方法形参，将响应数据和页面封装成ModelAndView对象,通过视图解析器解析View，然后渲染视图，返回给用户
   Struts采用值栈存储请求和响应的数据，通过OGNL存取数据
    Jsp视图解析器默认使用jstl。
