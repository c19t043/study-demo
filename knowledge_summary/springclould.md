# spring cloud

## ZooKeeper、Eureka对比

简介
Eureka本身是Netflix开源的一款提供服务注册和发现的产品，并且提供了相应的Java封装。在它的实现中，节点之间相互平等，部分注册中心的节点挂掉也不会对集群造成影响，即使集群只剩一个节点存活，也可以正常提供发现服务。哪怕是所有的服务注册节点都挂了，Eureka Clients（客户端）上也会缓存服务调用的信息。这就保证了我们微服务之间的互相调用足够健壮。

Zookeeper主要为大型分布式计算提供开源的分布式配置服务、同步服务和命名注册。曾经是Hadoop项目中的一个子项目，用来控制集群中的数据，目前已升级为独立的顶级项目。很多场景下也用它作为Service发现服务解决方案。

对比
在分布式系统中有个著名的CAP定理（C-数据一致性；A-服务可用性；P-服务对网络分区故障的容错性，这三个特性在任何分布式系统中不能同时满足，最多同时满足两个）；

Zookeeper
Zookeeper是基于CP来设计的，即任何时刻对Zookeeper的访问请求能得到一致的数据结果，同时系统对网络分割具备容错性，但是它不能保证每次服务请求的可用性。从实际情况来分析，在使用Zookeeper获取服务列表时，如果zookeeper正在选主，或者Zookeeper集群中半数以上机器不可用，那么将无法获得数据。所以说，Zookeeper不能保证服务可用性。

诚然，在大多数分布式环境中，尤其是涉及到数据存储的场景，数据一致性应该是首先被保证的，这也是zookeeper设计成CP的原因。但是对于服务发现场景来说，情况就不太一样了：针对同一个服务，即使注册中心的不同节点保存的服务提供者信息不尽相同，也并不会造成灾难性的后果。因为对于服务消费者来说，能消费才是最重要的——拿到可能不正确的服务实例信息后尝试消费一下，也好过因为无法获取实例信息而不去消费。（尝试一下可以快速失败，之后可以更新配置并重试）所以，对于服务发现而言，可用性比数据一致性更加重要——AP胜过CP。

Eureka
而Spring Cloud Netflix在设计Eureka时遵守的就是AP原则。Eureka Server也可以运行多个实例来构建集群，解决单点问题，但不同于ZooKeeper的选举leader的过程，Eureka Server采用的是Peer to Peer对等通信。这是一种去中心化的架构，无master/slave区分，每一个Peer都是对等的。在这种架构中，节点通过彼此互相注册来提高可用性，每个节点需要添加一个或多个有效的serviceUrl指向其他节点。每个节点都可被视为其他节点的副本。

如果某台Eureka Server宕机，Eureka Client的请求会自动切换到新的Eureka Server节点，当宕机的服务器重新恢复后，Eureka会再次将其纳入到服务器集群管理之中。当节点开始接受客户端请求时，所有的操作都会进行replicateToPeer（节点间复制）操作，将请求复制到其他Eureka Server当前所知的所有节点中。

一个新的Eureka Server节点启动后，会首先尝试从邻近节点获取所有实例注册表信息，完成初始化。Eureka Server通过getEurekaServiceUrls()方法获取所有的节点，并且会通过心跳续约的方式定期更新。默认配置下，如果Eureka Server在一定时间内没有接收到某个服务实例的心跳，Eureka Server将会注销该实例（默认为90秒，通过eureka.instance.lease-expiration-duration-in-seconds配置）。当Eureka Server节点在短时间内丢失过多的心跳时（比如发生了网络分区故障），那么这个节点就会进入自我保护模式。

什么是自我保护模式？默认配置下，如果Eureka Server每分钟收到心跳续约的数量低于一个阈值（instance的数量(60/每个instance的心跳间隔秒数)自我保护系数），并且持续15分钟，就会触发自我保护。在自我保护模式中，Eureka Server会保护服务注册表中的信息，不再注销任何服务实例。当它收到的心跳数重新恢复到阈值以上时，该Eureka Server节点就会自动退出自我保护模式。它的设计哲学前面提到过，那就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例。该模式可以通过eureka.server.enable-self-preservation = false来禁用，同时eureka.instance.lease-renewal-interval-in-seconds可以用来更改心跳间隔，eureka.server.renewal-percent-threshold可以用来修改自我保护系数（默认0.85）。

> 总结
ZooKeeper基于CP，不保证高可用，如果zookeeper正在选主，或者Zookeeper集群中半数以上机器不可用，那么将无法获得数据。Eureka基于AP，能保证高可用，即使所有机器都挂了，也能拿到本地缓存的数据。作为注册中心，其实配置是不经常变动的，只有发版和机器出故障时会变。对于不经常变动的配置来说，CP是不合适的，而AP在遇到问题时可以用牺牲一致性来保证可用性，既返回旧数据，缓存数据。

所以理论上Eureka是更适合作注册中心。而现实环境中大部分项目可能会使用ZooKeeper，那是因为集群不够大，并且基本不会遇到用做注册中心的机器一半以上都挂了的情况。所以实际上也没什么大问题。

## dubbo , spring cloud区别

在业界，常规的微服务有两种类型：一种是基于dubbo的微服务架构、另外一种是基于Spring Cloud的微服务架构。
从概念上来讲，Dubbo和Spring Cloud并不能放在一起对比，因为Dubbo仅仅是一个RPC框架，实现Java程序的远程调用，实施服务化的中间件则需要自己开发；
而Spring Cloud则是实施微服务的一系列套件，包括：服务注册与发现、断路器、服务状态监控、配置管理、智能路由、一次性令牌、全局锁、分布式会话管理、集群状态管理等。

## Eureka服务注册与发现

云端服务发现，一个局域rest的服务，用于定位服务，以实现云端中间层服务发现和故障转移

## Feign服务消费者

声明式、模板化的http客户端

Spring Cloud Netflix 的微服务都是以 HTTP 接口的形式暴露的，所以可以用 Apache 的 HttpClient 或 Spring 的 RestTemplate 去调用，而 Feign 是一个使用起来更加方便的 HTTP 客戶端，使用起来就像是调用自身工程的方法，而感觉不到是调用远程方法。

Feign在RestTemplate的基础上对其封装，由它来帮助我们定义和实现依赖服务接口的定义。Spring Cloud Feign 基于Netflix Feign 实现的，整理Spring Cloud Ribbon 与 Spring Cloud Hystrix，并且实现了声明式的Web服务客户端定义方式。

```java
@FeignClient(value="${service.product}",configuration=RoundRibbon.class)
public interface ProductService {
    String PRE = "/product";
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    ProductVo getProductInfo(@PathVariable(value = "productId") Long productId);
}
```

```xml
#开启请求压缩功能
feign.compression.request.enabled=true
#开启响应压缩功能
feign.compression.response.enabled=true
#指定压缩请求数据类型
feign.compression.request.mime-types=text/xml;application/xml;application/json
#如果传输超过该字节，就对其进行压缩
feign.compression.request.min-request-size=2048
```

```yml
feign:
  compression:
    request:
      min-request-size: 2048
      mime-types:
      - text/xml
      - application/xml
      - application/json
```

## Ribbon负载均衡器

提供客户端负载均衡，有多重负载均衡策略可供选择
Ribbon是Netflix发布的负载均衡器，它有助于控制HTTP和TCP的客户端的行为。为Ribbon配置服务提供者地址后，Ribbon就可基于某种负载均衡算法，自动地帮助服务消费者去请求。Ribbon默认为我们提供了很多负载均衡算法，例如轮询、随机等。当然，我们也可为Ribbon实现自定义的负载均衡算法。
在Spring Cloud中，当Ribbon与Eureka配合使用时，Ribbon可自动从Eureka Server获取服务提供者地址列表，并基于负载均衡算法，请求其中一个服务提供者实例

是一个基于 HTTP 和 TCP 客户端的负载均衡器
它可以在客户端配置 ribbonServerList（服务端列表），然后轮询请求以实现均衡负载。

负载均衡策略：轮训（round）,随机（random）

```java
@RibbonClients(value = { @RibbonClient(value = "${service.product}", configuration = RoundRibbon.class),
        @RibbonClient(value = "${service.productInventory}", configuration = RandomRibbon.class) })
```

使用属性自定义Ribbon配置
从Spring Cloud Netflix1.2.0开始，Ribbon支持使用属性自定义Ribbon客户端。这种方式比使用Java代码配置的方式更加方便。
支持的属性如下，配置的前缀是`clientName`.ribbon.
NFLoadBalancerClassName：配置ILoadBalancer的实现类
NFLoadBalancerRuleClassName：配置IRule的实现类
NFLoadBalancerPingClassName：配置IPing的实现类
NIWSServerListClassName：配置ServerList的实现类
NIWSServerListFilterClassName：配置ServerListFilter的实现类

```yml
microservice-provider-user:
  ribbon:
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```

## restful+ribbon

+ 负载均衡策略如果放在启动类所在目录及其子目录，需要将策略配置排除在组件扫面外，否则ribbon默认的负载均衡规则是轮训，如果被扫描到，就会同时有多个规则在容器中，使用的时候需要加区分@Qualifier(value="myRoundRibbon")

```java
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcludeFromComponentScan.class) })
```

## feign,ribbon区别

1. feign本身里面就包含有了ribbon
2. feign自身是一个声明式的伪http客户端，写起来思路清晰
3. fegin是一个采用基于接口注解的编程方式，更加简洁方便

## hystrix断路器

熔断器，容错管理工具，旨在通过熔断机制控制服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力

在大中型分布式系统中，通常系统有很多依赖服务，这些依赖服务的稳定性与否对系统的影响非常大,但是依赖服务有很多不可控问题:如网络连接缓慢，资源繁忙，暂时不可用，服务脱机等.在高并发场景下，依赖服务如果没有隔离措施，系统有被拖垮的风险。

### Hystrix如何解决依赖隔离

1:Hystrix使用命令模式HystrixCommand(Command)包装依赖调用逻辑，每个命令在单独线程中/信号授权下执行。

2:可配置依赖调用超时时间,超时时间一般设为比99.5%平均时间略高即可.当调用超时时，直接返回或执行fallback逻辑。

3:为每个依赖提供一个小的线程池（或信号），如果线程池已满调用将被立即拒绝，默认不采用排队.加速失败判定时间。

4:依赖调用结果分:成功，失败（抛出异常），超时，线程拒绝，短路。 请求失败(异常，拒绝，超时，短路)时执行fallback(降级)逻辑。

5:提供熔断器组件,可以自动运行或手动调用,停止当前依赖一段时间(10秒)，熔断器默认错误率阈值为50%,超过将自动运行。

6:提供近实时依赖的统计和监控

## hystrixDashboard断路器监控

监控hystrix的metricx情况

locahost:8762/hystrix.stream

## hystrixTurbine断路器聚合监控

聚合服务器发送事件流数据的一个工具，用来监控集群下hystrix的metrics情况

http://localhost:8765/turbine.stream

## zuul路由网关

zuul是在云平台上体用动态路由，监控，弹性，安全等边缘服务的框架。zuul相当于网站后端所有请求的前门

## spring clould config分布式配置中心

配置管理工具包，让你可以吧配置放到远程服务器，集中化管理集群配置，目前支持本地存储、git和subversion

## spring cloud bus消息总线

事件、消息总线，用于在集群（例如，配置变化事件）中传播状态变化，可与spring cloud config联合实现热部署

## spring cloud sleuth服务链路追踪

日志收集工具包，封装了dapper和log-based追踪以及zipkin和htrace操作，为springcloud应用实现了一种分布式追踪解决方案

