# project
这三个工程中，其中service-hi、service-gateway向注册中心eureka-server注册。用户的请求首先经过service-gateway，根据路径由gateway的predict 去断言进到哪一个 router， router经过各种过滤器处理后，最后路由到具体的业务服务，比如 service-hi


| 工程名 | 端口 | 作用 |
| --- | ---| --- |
|eureka-server|8761|注册中心eureka server|
|service-hi|8762|服务提供者 eurka client|
|service-gateway|8081|路由网关 eureka client|
