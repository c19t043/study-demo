# session


## 分布式session

### session一致性问题。

1、通过数据库存储session信息，每次从数据查询。这种方式会导致数据库压力巨大。不适用

2、通过客户端Cookie写入到本地，这样很不安全，不可采取

3、通过nginx的hash一致性做ip绑定，这样失去了灵活性

4、使用tomcat内部通信机制同步数据通信，这样开销较大，不适合大型项目

5、使用spring-session框架来解决，底层采用重写httpclient保证数据共享

6、使用token令牌代替session功能，把数据存放在redis中，每次从redis中获取数据

### 利用token令牌和redis共享分布式集群方式替换Session功能。
    
 利用token令牌和redis共享分布式集群方式替换Session功能。大概思路原理如下：
 
 用户登录后，生成一个唯一令牌，令牌作为redis的key，用户信息作为key对应的value存入redis中，给设置过期时间为30分钟，然后把令牌返回给客户端，客户端保存令牌到本地Cookie中，以后用在获取用户信息的时候直接发送一个请求到后端，在请求头部传入令牌，后端通过request.getHeader("Authorization");获取到令牌，然后用令牌去redis查询对应得用户信息，如果没有查询到那么代表用户Session失效或者用户未登陆。如果查询到即返回用户信息。 