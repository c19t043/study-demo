# spring

## spring简介

spring帮助java开发者解决了开发中基础性的问题，使得开发人员专注于应用程序的开发
为了简化开发流程，提高java程序开发效率，spring提供了从表现层，业务层，到持久层的一整套解决方案，
而且spring对各种优秀框架提供了很好的支持，spring框架的核心功能IOC和AOP，方便我们对程序解耦，简化代码逻辑，提供了很好的支持

## 使用spring框架的好处

+ spring采用IOC和DI设计，降低了组件之间的耦合性，方便解耦，简化开发
+ spring提供了对AOP编程的支持，可以方便对程序进行统一日志处理，权限管理等功能
+ spring提供了对声明式事务的支持，通过配置就可以完成对事务的管理，无需手动编程
+ spring提供了统一的异常，对其他具体技术的异常进行封装转换
+ spring方便集成各种优秀框架，内部提供了对各种优秀框架的支持

## spring 中主要有哪些模块

+ Core container:beans,core,context,spel
+ 数据访问和集成：jdbc,orm,transaction
+ web:websocket,servlet,web
+ aop
+ jms

## ioc 与 di

+ ioc:将手动创建对象的权利交由spring容器管理（将对象的创建，初始化，销毁交给spring容器来管理）
+ di: 在spring框架中创建bean实例，动态将依赖对象注入到组件中（组件之间的依赖关系由容器在运行期间决定。即由容器动态的将某个依赖关系注入到组件中）

## IOC容器的初始化分为三个过程实现

+ 第一个过程是Resource资源定位。将用户定义bean的配置文件载入内存中
+ 第二个过程是BeanDefinition的载入过程。这个过程是把用户定义好的Bean转换成Ioc容器内部的数据结构BeanDefition 
+ 第三个过程是向IOC容器注册这些BeanDefinition的过程

## spring bean

在spring中，我们将被spring ioc容器管理的对象，称为bean

### bean的作用域

+ singleton:在springIOC容器中仅存在一个bean实例，bean以单例方式存在，默认值
+ prototype：一个bean有多个实例
+ request：每次http请求时会创建一个新的bean，该作用域仅适用于webApplicationContext环境
+ session：同一个http session共享一个bean,不同session适用不同bean，该作用域仅适用于webApplicationContext环境
+ global-session:一般用于portlet应用环境，该作用域仅适用于webApplicationContext环境

### spring的生命周期

+ bean生命周期执行过程
  1. spring对bean进行实例化，默认bean是单例
  2. spring对bean进行依赖注入
  3. 如果bean实现了BeanNameAware接口，spring将bean的id传给setBeanNameI()方法；
  4. 如果bean实现了BeanFactoryAware接口，spring将调用setBeanFactory方法，将beanFactory实例传进来
  5. 如果bean实现了ApplicationContextAware接口，它的setApplicationContext方法将被调用，将应用上下文的引用传入到bean中
  6. 如果bean实现了BeanPostProcessor接口，它的postProcessBeforeInitialization方法将被调用
  7. 如果bean实现了InitializingBean接口，spring将调用它的afterPropertiesSet方法
  8. 如果bean使用init-method属性声明了初始化方法，该方法也会被调用
  9. 如果bean实现了BeanPostProcessor接口，它的postProcessorAfterInitalization接口方法将被调用
  10. 此时bean已经准备就绪了，可以被应用程序使用了，他们将一直主流在应用上下文中，直到该应用上下文被销毁
  11. 如果bean实现了DisposableBean接口，spring将调用它的destroy方法。
  12. 如果bean使用了destroy-method属性声明了销毁方法，该方法会被调用

### bean的生命周期

+ 可以实现InitializingBean和DisposableBean接口，自定义bean的初始化和销毁逻辑
+ 也可以自定义init-method和destroy-method属性，自定义初始化和销毁方法

### bean创建的方式

+ 构造器创建
+ 静态工厂方式创建
+ 实例化工厂方式创建

### spring bean 配置的方式

+ 注解（Component/Controller/Service/Repotory）
+ xml配置(</bean>)
+ java配置（Configuration/Bean）

### 为bean注入依赖属性的方式

+ 构造器注入
+ Setter注入

### bean的自动装配方式

+ no >> spring框架默认配置,在该配置下自动装配是关闭的，开发者需要在bean定义中指定依赖关系
+ byType
+ byName
+ construct
+ autoDetect

### spring中注入一个集合

+ </list>
+ </set>
+ </map>
+ </props>

### spring inner bean

## spring aop

spring aop 在程序开发中主要用来解决系统层面上的问题，比如日志，事务，权限等，通过动态代理的方式为程序添加统一的功能，降低了各模块之间的耦合性，提高了系统的可扩展性

## jdk动态代理，cglib代理的优缺点

+ jdk动态代理不需要第三方库支持，只需要jdk环境就可以进行代理，cglib依赖于cglib类库
+ jdk动态代理只能对实现了接口的类生成代理，cglib对此没有限制，但是不能对为final修饰的方法进行代理
+ cglib底层采用asm字节码生成框架，动态的生成被代理类的子类，执行效率高，但随着jdk版本升级，逐渐对jdk动态代理进行优化，提升了jdk代理的执行效率
+ 现在流行面向接口编程，使用jdk动态代理的场景多

### aop名称解释

+ 目标对象（target）: 被代理的对象
+ 连接点（Joinpoint）：被代理对象中可以被拦截的方法
+ 切入点(pointcut)：对要被拦截的连接点进行定义
+ 通知（advice）:对连接点进行功能增强：如增加日志记录
+ 织入（weaving）:是指将通知应用到目标对象创建新的代理对象的过程
+ 代理（Proxy）:一个类被AOP增强，会生成一个代理类
+ 切面（Aspect）:切面由多个切入点和通知组成

### 通知的类型

+ 前置通知（Before）
+ 后置通知（AfterReturning）
+ 最终通知（After）
+ 异常通知（AfterThrowing）
+ 环绕通知（Around）

## spring transaction

事务是对一系列操作进行统一的提交和回滚，所有操作要嘛都成功，要嘛都失败，如果所有操作中，有一个操作执行失败，就回滚之前的所有操作，防止数据库数据出现问题
spring提供了两种事务管理的方式：编程式事务管理，声明式事务管理
编程式事务管理，比较灵活，但是代码量大，重复的代码比较多
声明式事务管理，

### Spring 框架的事务管理有哪些优点

+ spring对不同的事务API提供了统一的编程模式
+ spring为编程式事务管理提供一套简单的API
+ spring支持声明式事务
+ spring框架的事务管理与spring提供的数据访问抽象层有很好的集成

### 为什么要有事务传播行为

多个具有事务控制的service相互调用时，需要控制各service中的事务，常见的做法是使用Propagation_required事务传播行为，如果当前有事务就使用，没有使用就创建

### 事务传播行为

+ Propagation_required : 支持当前事务，如果当前没有事务，就创建事务（最常见用法）
+ Propagation_supported: 支持当前事务，如果当前没有事务，就以非事务方式执行
+ propagation_mandatory: 支持当前事务，如果当前没有事务，就抛出异常
+ propagation_requires_new: 新建事务，如果当前存在事务，将把当前事务挂起来
+ propagation_not_supported：以非事务方式运行，如果当前存在事务，就把当前事务挂起来
+ propagation_never: 以非事务方式运行，如果当前存在事务，就抛出异常
+ propagation_nested: 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则进行与Propagation_required类似的操作

### 事务的隔离级别

+ Serializable:
+ repeatable read:
+ read commited:
+ read uncommited:

### spring提供了几个关于事务处理的类

TransactionDefinition //事务属性定义

TranscationStatus //代表了当前的事务，可以提交，回滚。

PlatformTransactionManager这个是spring提供的用于管理事务的基础接口，其下有一个实现的抽象类AbstractPlatformTransactionManager,我们使用的事务管理类例如DataSourceTransactionManager等都是这个类的子类。

```java
//编程式事务
TransactionDefinition td = new TransactionDefinition();
TransactionStatus ts = transactionManager.getTransaction(td);
try{ //do sth
  transactionManager.commit(ts);
}
catch(Exception e){
  transactionManager.rollback(ts);
}
```

## spring orm

## spring mvc

## spring的核心类

+ beanFactory:提供了IOC容器的基本功能，spring使用BeanFactory来初始化，配置和管理bean
+ applicationContext:继承至BeanFactory，不仅提供了BeanFactory原本的功能，还提供了更多个高级特性，比如国际化资源，资源加载，应用事件发布等
+ beanWrapper:BeanWrapper对Bean提供了统一的setter/getter方法，用来访问bean的属性

## spring中如何处理线程并发问题

spring使用ThreadLocal解决线程安全问题
ThreadLocal为每一个线程提供了一份独立的变量副本，从而隔离多个线程对数据的访问冲突。
对于多线程资源共享问题，同步机制采取了以“时间换空间”的方式，仅提供一份变量，多个线程排队访问。
ThreadLocal采用了“空间换时间”的方式，对每一个线程都提供一份变量，因此可用同时访问而互补影响

## factoryBean 与 beanFactory

beanFactory，以Factory结尾，表示是一个工厂类（接口），用于管理beand的一个工厂
在Spring中，beanFactory是IOC容器的核心接口
它的职责是实例化、定位、配置应用程序中的对象及建立这些对象之间的依赖

FactoryBean，以bean结尾， 表示他是一个bean,不同于普通的bean：它是实现了FactoryBean<T>接口的bean,
根据该bean的id从BeanFactory中实际上获取的是FactoryBean的getObject()方法返回的对象，而不是FactoryBean本身，
如果想要回去FactoryBean本身，在getBean(“beanName”)，在beanName前加“&”符号

## factorybean 与 beanfactory的区别

beanFactory是一个工厂接口，提供了IOC容器的基本功能，spring使用BeanFactory来实例化，管理和配置bean。
FactoryBean是也是一个接口，为IOC容器中的bean的实现提供了更加灵活的方式，用户可以通过实现该接口定制实例化bean的逻辑，

### 实例化容器的方式

1. XmlBeanFactory

```java
Resource resource = new FileSystemResource("beans.xml");
BeanFactory beanFactory = new XmlBeanFactory(resource);
```

```java
Resource resource = new ClassPathResource("beans.xml");
BeanFactory beanFactory = new XmlBeanFactory(resource);
```

```java
ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"beans.xml"});
BeanFactory beanFactory = (BeanFactory) context;
```

### BeanFactory提供的方法(常用)

+ boolean containsBean(String beanName) >> 判断工厂中是否包含给定名称的bean定义，若有则返回true
+ Object getBean(String) >> 返回给定名称注册的bean实例
+ <T> T getBean(Class<T> requiredType) >> 返回给定类型注册的bean的实例
+ boolean isSingleton(String name) >> 判断给定名称的bean定义是否为单例模式

## spring中用的设计模式

+ 模板方法

定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。Template Method使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
Template Method模式一般是需要继承的

这里想要探讨另一种对Template Method的理解。Spring中的JdbcTemplate，在用这个类时并不想去继承这个类，因为这个类的方法太多，但是我们还是想用到JdbcTemplate已有的稳定的、公用的数据库连接，那么我们怎么办呢？我们可以把变化的东西抽出来作为一个参数传入JdbcTemplate的方法中。但是变化的东西是一段代码，而且这段代码会用到JdbcTemplate中的变量。怎么办？那我们就用回调对象吧。在这个回调对象中定义一个操纵JdbcTemplate中变量的方法，我们去实现这个方法，就把变化的东西集中到这里了。然后我们再传入这个回调对象到JdbcTemplate，从而完成了调用。这可能是Template Method不需要继承的另一种实现方式吧。

+ 策略（Strategy）

定义一系列的算法，把它们一个个封装起来，并且使它们可相互替换。本模式使得算法可独立于使用它的客户而变化。

spring aop中根据被代理类是接口还是是类，来动态切换使用JDK动态代理或CGlib代理

+ 观察者（Observer）

定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。

Spring中Observer模式常用的地方是listener的实现。如ApplicationListener。

+ 包装器（Decorator）

动态地给一个对象添加一些额外的职责。就增加功能来说，Decorator模式相比生成子类更为灵活。

Spring中用到的包装器模式在类名上有两种表现：一种是类名中含有Wrapper，另一种是类名中含有Decorator。基本上都是动态地给一个对象添加一些额外的职责。

比如：BeanWrapper:包装Bean提供了统一的访问方法

+ 代理（Proxy）

为其他对象提供一种代理以控制对这个对象的访问。

从结构上来看和Decorator模式类似，但Proxy是控制，更像是一种对功能的限制，而Decorator是增加职责。

springAOP使用了JDK动态代理和CgLib代理

+ 适配器（Adapter）

将一个类的接口转换成客户希望的另外一个接口。Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。

spring-jms的MessagingMessageListenerAdapter，在onMessage中将jms的Message转换为message模块的Message对象(内部类的LazyResolutionMessage，重写了getPayload、getHeader)，并交给message模块的InvocableHandleMethod，这样一来便可以实现jms与spring message无缝适配对接了，在spring-websocket也是相同的套路

+ 单例（Singleton）

保证一个类仅有一个实例，并提供一个访问它的全局访问点。

Spring中的单例模式完成了后半句话，即提供了全局的访问点BeanFactory。但没有从构造器级别去控制单例，这是因为Spring管理的是是任意的Java对象。

+ 工厂方法（Factory Method）

定义一个用于创建对象的接口，让子类决定实例化哪一个类。Factory Method使一个类的实例化延迟到其子类。

+ 简单工厂

又叫做静态工厂方法（StaticFactory Method）模式，但不属于23种GOF设计模式之一。

简单工厂模式的实质是由一个工厂类根据传入的参数，动态决定应该创建哪一个产品类。

Spring中的BeanFactory就是简单工厂模式的体现，根据传入一个唯一的标识来获得Bean对象，但是否是在传入参数后创建还是传入参数前创建这个要根据具体情况来定。

## FileSystemResource，ClassPathResource

+ FileSystenResource访问文件系统中的资源需要给出文件的相对路径或绝对路径
+ ClassPathResource会ClassPath路径下搜索文件资源

## applicationContext创建的几种方式

+ FileSystemXmlApplicationContext
+ ClassPathXmlApplicationContext
+ AnnotationConfigApplicationContext

## beanFactory 与 applicationContext

BeanFactory是IOC容器的核心接口，它定义了IOC的基本功能，Spring 使用 BeanFactory 来实例化、配置和管理 Bean。
ApplicationContext：是IOC容器另一个重要接口， 它继承了BeanFactory的基本功能,同时也提供了容器的高级功能，如：MessageSource（国际化资源接口）、ResourceLoader（资源加载接口）、ApplicationEventPublisher（应用事件发布接口）等。

## beanFactory 与 applicationContext区别

1.BeanFactroy采用的是延迟加载形式来注入Bean的，即只有在使用到某个Bean时(调用getBean())，才对该Bean进行加载实例化，这样，我们就不能发现一些存在的Spring的配置问题。而ApplicationContext则相反，它是在容器启动时，一次性创建了所有的Bean。这样，在容器启动时，我们就可以发现Spring中存在的配置错误。 相对于基本的BeanFactory，ApplicationContext 唯一的不足是占用内存空间。当应用程序配置Bean较多时，程序启动较慢。

BeanFacotry延迟加载,如果Bean的某一个属性没有注入，BeanFacotry加载后，直至第一次使用调用getBean方法才会抛出异常；而ApplicationContext则在初始化自身是检验，这样有利于检查所依赖属性是否注入；所以通常情况下我们选择使用 ApplicationContext。
应用上下文则会在上下文启动后预载入所有的单实例Bean。通过预载入单实例bean ,确保当你需要的时候，你就不用等待，因为它们已经创建好了。

2.BeanFactory和ApplicationContext都支持BeanPostProcessor、BeanFactoryPostProcessor的使用，但两者之间的区别是：BeanFactory需要手动注册，而ApplicationContext则是自动注册。（Applicationcontext比 beanFactory 加入了一些更好使用的功能。而且 beanFactory 的许多功能需要通过编程实现而 Applicationcontext 可以通过配置实现。比如后处理 bean ， Applicationcontext 直接配置在配置文件即可而 beanFactory 这要在代码中显示的写出来才可以被容器识别。 ）

3.beanFactory主要是面对与 spring 框架的基础设施，面对 spring 自己。而 Applicationcontex 主要面对与 spring 使用的开发者。基本都会使用 Applicationcontex 并非 beanFactory 。

## spring常用注解

+ Required >> 标记依赖必须注入
+ AutoWired >> 自动装配（byType）
+ Inject >> 自动装配（byType）
+ Qualifier >> 自动装配时，指定需要装配的bean的名字
+ Resource >> 自动装配（byName）
+ Configuration >> 配置类
+ PostConstruct  >> 标记bean初始化前方法
+ PreDestroy >> 标记bean销毁前方法
+ Component/Controller/Service/Repostory >> 标记类注册为组件[自己写的类]
+ Scope >> bean作用域
+ Conditional >> 类中组件统一设置。满足当前条件，这个类中配置的所有bean注册才能生效；
+ Lazy >> 标记组件为懒加载
+ ConponentScan >> 组件扫描
+ Bean >> [导入的第三方包里面的组件]
+ Import >> [快速给容器中导入一个组件]，id为组件的全类名，首字母小写
+ Primary >> 让Spring进行自动装配的时候，默认使用首选的bean
+ ImportSelector >> 手动注册多个组件到容器中
+ Profile >> 指定组件在哪个环境中才能被注册到容器中，不指定，任何环境下都能注册这个组件
+ PropertySource >> 加载外部配置文件中k/v保存到环境变量中，以后可以使用${}取出配置文件中的值
+ EnableAspectJAutoProxy >> 开启切面代理
+ Aspect >> 标注类为切面类
+ PointCut >> 切入点
+ Before/After/AfterReturning/AfterThrowing/Around >> 前置通知/后置通知/最终完成通知/异常通知/环绕通知
+ EnableTransactionManagement  >> 开启事务
+ Transactional >> 事务注解
+ Async >> 方法异步调用
  类：表示这个类中的所有方法都是异步的
  方法：表示这个方法是异步的，如果类也注解了，则以这个方法的注解为准

## ApplicationEvent

+ 事件源

```java
import org.springframework.context.ApplicationEvent;
//A事件
public class AEvent extends ApplicationEvent{

    private static final long serialVersionUID = 1L;

    private String name = "AAAA";
    public String getName() {
        return name;
    }
    //继承ApplicationEvent,重写构造函数
    public AEvent(Object source) {
        super(source);
    }
}
```

## ApplicationListener

+ 监听事件

```java
@Component
//实现ApplicationListener接口
public class AEventListener implements ApplicationListener{
    public void onApplicationEvent(ApplicationEvent event) {
        System.err.println(this.getClass().getName());
        if(event instanceof AEvent){
            //处理事件A业务逻辑
        }
        System.out.println(event.getSource());
    }
}
```

> 从上面的监听器处理可以看出，需要通过event instanceof AEvent判断事件源来处理对于的业务逻辑，这样很不爽，当然有更好的方式
> 接口ApplicationListener支持泛型，可以通过泛型来判断处理的事件源。如下只处理BEvent源。

```java
@Component
public class BEventListener implements ApplicationListener<BEvent>{

    public void onApplicationEvent(BEvent bEvent) {
        System.out.println("event name : "+bEvent.getName());
        System.out.println(bEvent.getSource());
    }
}
```

> 如果事件没要处理的监听器，就会被抛弃。
> 以上处理事件都是同步的，如果发布事件处的业务存在事务，监听器处理也会在相同的事务下。
> 如果是发送邮箱、短信等耗时操作，如何让事件异步处理呢？

```java
@Component
public class BEventListener implements ApplicationListener<BEvent>{
    @Async
    public void onApplicationEvent(BEvent bEvent) {
        System.out.println("event name : "+bEvent.getName());
        System.out.println(bEvent.getSource());
    }
}
```

## 发布事件

applicationContext.publishEvent(new AEvent("testA"));

## spring中有哪些不同类型的事件

Spring 提供了以下5中标准的事件：

上下文更新事件（ContextRefreshedEvent）：该事件会在ApplicationContext被初始化或者更新时发布。也可以在调用ConfigurableApplicationContext 接口中的refresh()方法时被触发。
上下文开始事件（ContextStartedEvent）：当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件。
上下文停止事件（ContextStoppedEvent）：当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。
上下文关闭事件（ContextClosedEvent）：当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。
请求处理事件（RequestHandledEvent）：在Web应用中，当一个http请求（request）结束触发该事件。

## Aware

aware,翻译过来是知道的，已感知的，意识到的，所以这些接口从字面意思应该是能感知到所有Aware前面的含义
实现这些Aware接口的Bean在被实例化之后，可以取得相对应的资源

Aware起作用的时机是在Bean已经完成实例化之后，初始化Bean的同时。

+ BeanNameAware >> 可以让该Bean感知到自身的BeanName（对应Spring容器的BeanId属性）属性
    获得容器中Bean的名称
+ BeanFactoryAware >> 可以让bean获取到BeanFactory
    获得当前bean factory，这样可以调用容器的服务
+ ServletContextAware >> bean实例化后，由IOC容器注入ServletContext对象
+ ApplicationContextAware >> 可以让bean获取到ApplicationContext
+ BeanClassLoaderAware >> 如果spring容器检测 到当前对象实了此接口，会将对应加载当前对象的ClassLoader注入当前对象实例。
+ ResourceLoaderAware >> 如果当前对象实例实现了此接口，容器会自动将ApplicationContext注入到当前对象，因为ApplicationContext实现了ResourceLoader接口。
    获得资源加载器，可以获得外部资源文件
+ ApplicationEventPublisherAware >> 如果当前对象实例实现了此接口，容器会自动将ApplicationContext注入到当前对象，因为ApplicationContext实现了ApplicationEventPublisher接口。
    应用事件发布器，可以发布事件
+ MessageSourceAware >> MessageSource接口主要提供国际化信息支持。同样的注入ApplicationContext。
    获得message source-国际化的时候用的，这样可以获得文本信息
> 注意：除了通过实现Aware结尾接口获取spring内置对象，
> 也可以通过@Autowired注解直接注入相关对象，如下：（如果需要用到静态方法中，如工具方法，还是采用实现接口的方式）

## BeanPostProcessor

主要针对于Bean这一级别，关注的主要是Bean实例化后，初始化前后的。

如果我们想在spring容器完成bean实例化，配置以及其他初始化前后添加一些自定义逻辑处理，要将需要特殊处理的bean实现BeanPostProcessor接口，然后注册到容器中

## BeanFactoryPostProcessor

它起作用的时机发生在解析成BeanDefinition后，实例化之前。针对的应该是容器级别的扩展

Spring IoC容器允许BeanFactoryPostProcessor在容器实例化任何bean之前读取bean的定义(配置元数据)，并可以修改它。

注册一个BeanFactoryPostProcessor实例需要定义一个Java类来实现BeanFactoryPostProcessor接口，并重写该接口的postProcessorBeanFactory方法。通过beanFactory可以获取bean的定义信息，并可以修改bean的定义信息。

BeanFactoryPostProcessor有几个我们比较常用的子类PropertyPlaceholderConfigurer、CustomEditorConfigurer，前者用于配置文件中的${var}变量替换，后者用于自定义编辑BeanDefinition中的属性值，合理利用CustomEditorConfigurer会有一些意想不到的效果（例如可以通过修改某些属性实现类似aop的功能）。

```java
public interface BeanFactoryPostProcessor {  
        /** 
         * Modify the application context's internal bean factory after its standard 
         * initialization. All bean definitions will have been loaded, but no beans 
         * will have been instantiated yet. This allows for overriding or adding 
         * properties even to eager-initializing beans. 
         * @param beanFactory the bean factory used by the application context 
         * @throws org.springframework.beans.BeansException in case of errors 
         */  
        void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;  
    }
```

## ApplicationContextAwareProcessor

ApplicationContext为BeanFactory增加了ApplicationContextAwareProcessor，

ApplicationContextAwareProcessor是一种BeanPostProcessor

BeanPostProcessor发生在bean初始化前后，在bean初始化之前将调用postProcessBeforeInitialization方法，

ApplicationContext利用ApplicationContextAwareProcessor完成了ApplicationContext中特有的一些Aware的调用，发生的时机在初bean始化之前

## BeanDefinitionRegistryPostProcessor

BeanDefinitionRegistryPostProcessor继承自BeanFactoryPostProcessor,是一种比较特殊的BeanFactoryPostProcessor

BeanDefinitionRegistryPostProcessor中定义的postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)方法 可以让我们实现自定义的注册bean定义的逻辑。

```java
public class CustomBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

  }

  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    RootBeanDefinition helloBean = new RootBeanDefinition(Hello.class);
    //新增Bean定义
    registry.registerBeanDefinition("hello", helloBean);
  }
}
```

## ServletContainerInitializer

## Registration

+ ServletRegistration
+ FilterRegistration

## ServletContext

## WebApplicationContext

WebApplicationContext 继承了ApplicationContext 并增加了一些WEB应用必备的特有功能

## Spring框架中的单例Beans是线程安全的么

Spring框架并没有对单例bean进行任何多线程的封装处理。关于单例bean的线程安全和并发问题需要开发者自行去搞定。但实际上，大部分的Spring bean并没有可变的状态(比如Servie类和DAO类)，所以在某种程度上说Spring的单例bean是线程安全的。如果你的bean有多种状态的话（比如 View Model 对象），就需要自行保证线程安全。
最浅显的解决办法就是将多态bean的作用域由“singleton”变更为“prototype”。

## Ordered，PriorityOrdered

spring提供了Ordered接口，顾名思义，就是用来排序的
PriorityOrdered继承至Ordered接口
在Spring中PriorityOrdered优先级高于Ordered
> OrderComparator比较器进行排序的时候，若2个对象中有一个对象实现了PriorityOrdered接口，那么这个对象的优先级更高。
> 若2个对象都是PriorityOrdered或Ordered接口的实现类，那么比较Ordered接口的getOrder方法得到order值，值越低，优先级越高。

## TypeFilter

在使用@ComponentScan和@ComponentScan指定要过滤或包含的组件类型

比如spring mvc中，springmvc管理自己的组件

```java
@ComponentScan(value="basepackage",excludeFilters={@Filter(type=FilterType.ANNOTATION,classes={Controller.class})})
public class RootConfig {

}
```