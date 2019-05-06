# 23种设计模式

## 设计模式的六大原则

总原则：对扩展开发，对修改关闭

+ 单一职责原则
    不存在多个导致类变更的原因
+ 厘式替换原则
    任何基类出现的地方，子类也可以出现
+ 依赖倒置原则
    面向接口编程，依赖抽象而不依赖具体实现
+ 接口隔离原则
    每个子类中不存在不使用但必须实现的接口方法
+ 迪米特原则（最少知道原则）
    一个类对依赖的类知道的越少越好
+ 合成复用原则
    尽量少时用继承，而使用合成/聚合的方式

## 关系模式

关系模式分为4类：父类与子类，类与类，类的状态，中间类

### 策略模式

策略模式：定义了一系列算法，并算法封装起来，使不同算法之间可以相互替换，
且算法的变化不会影响使用算法的客户

一系列算法，实现同一接口，通过一定规则，可以使算法切换不同算法

```java
public interface Calculate{
    int calculate(String exp);
}
public abstract class AbstractCalculate{
    public int[] split(String exp,String opt){
        String[] arr = exp.split(opt);
        return new int[]{Integer.parseInt(arr[0]),Integer.parseInt(arr[1])};
    }
}
public class Plus extends AbstractCalculate implements Calculate{
    public int calculate(String exp){
        int[] arr = split(exp,"\\+");
        return arr[0]+arr[1];
    }
}
public class Minus extends AbstractCalculate implements Calculate{
    public int calculate(String exp){
        int[] arr = split(exp,"-");
        return arr[0]-arr[1];
    }
}
public class Multiply extends AbstractCalculate implements Calculate{
    public int calculate(String exp){
        int[] arr = split(exp,"\\*");
        return arr[0]*arr[1];
    }
}
public class StrategyTest{
    @Test
    public void testStrategy(){
        String exp = "2+8";
        Calculate cal = new Plus();
        int val = cal.calculate(exp);
    }
}
```

### 模板方法模式

模板方法模式:抽象类中有一个主方法，定义1到多个辅助抽象方法，
主方法完成算法主体逻辑，抽象方法由子类决定具体实现

主方法完成主体公共逻辑代码，将主体不同逻辑实现的部分抽取为抽象方法

```java
public abstract class AbstractCalculate{

    public int calcuate(String exp){
        int[] arr = split(exp);
        return calculate(arr[0],arr[1]);
    }

    public abstract int[] split(String exp);


    public abstract int calculate(int num1,int nun2);
}

public class Plus extends AbstractCalculate{
    @Override
    public int[] split(String exp){
        String[] arr = exp.split("\\+");
        return new int[]{Integer.parseInt(arr[0]),Integer.parseInt(arr[1])};
    }

    @Override
    public int calculate(int num1,int num2){
        return num1+num2;
    }
}

public class Minus extends AbstractCalculate{
    @Override
    public int[] split(String exp){
        String[] arr = exp.split("-");
        return new int[]{Integer.parseInt(arr[0]),Integer.parseInt(arr[1])};
    }

    @Override
    public int calculate(int num1,int num2){
        return num1-num2;
    }
}
public class TemplateMethodTest{
    @Test
    public void testTemplateMethod(){
        String exp = "2+8";
        AbstractCalculate cal = new Plus();
        int result = cal.calculate(exp);
    }
}
```


### 观察者模式

观察者模式类似于邮件订阅，当被订阅的内容有更新，可及时通知订阅者

```java
public interface Observer{
    void update();
}
public class Observer1 implements Observer{
    public void update(){
        System.out.println("observer1 receive");
    }
}
public class Observer2 implements Observer{
    public void update(){
        System.out.println("observer1 receive");
    }
}
public interface Subject{
    /* 添加观察者 */
    void add(Observer observer);
    /* 删除观察者 */
    void del(Observer observer);
    /* 通知所有观察者 */
    void notifyObservers();
    /* 自定义操作 */
    void operate();
}
public abstract class AbstractSubject implements Subject{
    private List<Observer> observers = new ArrayList<>();

    public void  add(Observer observer){
        observers.add(observer);
    }
    public void  del(Observer observer){
        observers.remove(observer);
    }
    public void  notifyObservers(){
        observers.foreach(observer -> observer.update());
    }
}
public class MySubject extends AbstractSubject{
    public void operate(){
        System.out.println("mysubject operate");
        this.notifyObservers();
    }
}
public class TestObserver{
    @Test
    public void testObserver(){
        MySubject mySubject = new MySubject();
        mySubject.add(new Observer1());
        mySubject.add(new Observer2());
        mySubject.operate();
    }
}
```

### 迭代器模式

迭代器模式，意思是顺序访问集合中的元素

```java
public interface Iterator{
    Object previous();
    Object next();
    boolean hasNext();
}
public interface Collection{
    Iterator iterator();
    
    void get(int index);
    
    int size();
}

public class DefaultIterator implements Iterator{
   private Collection collection;
   private int pos = -1;
   
   public DefaultIterator(Collection collection){
       this.collection = collection;
   }
   
   public Object previous(){
       if(pos>0){
           pos--;
       }
       return collection.get(pos);
   }
   public Object next(){
       if(pos<collection.size()-1){
           pos++;
       }
       return collection.get(pos);
   }
   public boolean hasNext(){
       if(pox < collection.size() - 1){
           return true;
       }else{
           return false;
       }
   }
}
public class DefaultCollection implements Collection{
      private String[] arr = {"A","B","C","D","E"};
      public Iterator iterator(){
          return new DefaultIterator(this);
      }
      public void get(int index){
          return arr[index];
      }
      public int size(){
          return arr.length;
      }
}
public class TestIterator{
    @Test
    public void testIterator(){
        Collection collection = new DefaultCollection();
        Iterator iterator = collection.iterator();
        if(iterator.hasNext()){
            Object obj = iterator.next();
        }
    }
}
```

## 结构型模式

### 适配器模式

将某个类的接口转为换客户期望的另外一个接口后使用，目的是消除由接口不匹配造成的类的兼容性问题

适配器模式主要分类的适配器模式，对象的适配器模式，接口的适配器模式

类的适配器模式：将一个类转换为满足另外一个新接口的类,
可以创建一个新的类，继承原始类，实现目标接口，实现接口方法，接口内部调用原始类的方法，最终使用目标接口来调用

对象的适配器模式：将一个类的实例转换为满足另外一个新接口的类，
可以创建一个新的类，持有类的实例，实现目标接口，实现接口方法，接口内部调用原始类的方法，最终使用目标接口来调用

接口的适配器模式：当不希望实现一个接口中的所有方法时，
可以创建一个抽象类，实现接口的所有方法，在接口的子类时，
可以只继承抽象类，实现需要的接口

### 类的适配器模式

```java
public class SourceClass{
    public void method(){}
}
public interface TargetInterface{
    void method1();
    void method2();
}
public class Adaper extends SourceClass implements TargetInterface{
    @Override
    public void method1(){
        super.method();
    }
    @Override
    public void method2(){}
}
public class AdapterTest{
    @Test
    public void testTargetInterface(){
        TargetInterface t = new Adapter();
        t.method1();
    }
}
```

## 对象的适配器模式

在类的适配器模式继承上改造

```java
public class Adapter implements TargetInterface{
    private SourceClass source;
    public Adapter(SourceClass source){
        this.source = source;
    }
    @Override
    public void method1(){
        source.method();
    }
    @Override
    public void method2(){}
}
```

## 接口的适配器模式

在类的适配器模式上改造

```java
public abstract class AbstractTarget implements TargetInterface{
    void method1(){}
    void method2(){}
}
public class Sub1 extends AbstractTarget{
    @Override
    public void method1(){}
}
```

## 装饰模式

装饰模式：给一个对象动态的添加一些功能，
实现时，装饰对象和被装饰对象都需要实现同一接口，装饰对象持有被装饰对象的实例

```java
public interface Sourceable{
    void method();
}
public class Source implements Sourceable{
    @Override
    public void method(){}
}
public class Decorator implements Sourceable{
    private Sourceable source;
    public Decorator(Sourceable source){
        this.source = source;
    }
    @Override
    public void method(){
        //装饰前
        source.method();
        //装饰后
    }
}
public class DecoratorTest{
    @Test
    public void testDecorator(){
        Sourceable source = new Source();
        Sourceable decorator = new Decorator(source);
        decorator.method();
    }
}
```

## 代理模式

代理模式跟装饰模式稍微有区别
代理模式，是只用使用代理类，调用时不用处理被代理类

```java
public class Proxy implements Sourceable{
    private Sourceable source;
    public Proxy(){
        source = new Source();
    }
    @Override
    public void method(){
        // 代理类前
        source.method();
        // 代理后
    }
}
public class ProxyTest{
    @Test
    public void testProxy(){
        Sourceable source = new Proxy();
        source.method();
    }
}
```

## 外观模式（门面模式）

外观模式：对外暴露统一接口，隐藏内部细节

```java
public class Cpu{
    public void start(){}
    public void shutdown(){}
}
public class Memory{
    public void start(){}
    public void shutdown(){}
}
public class Disk{
    public void start(){}
    public void shutdown(){}
}
public class ComputerFacade{
    Cpu cpu;
    Memory memory;
    Disk disk;
    public ComputerFacade(){
        cput = new Cpu();
        memory = new Memory();
        disk = new Disk();
    }
    public void start(){
        cpu.start();
        memory.start();
        disk.start();
    }
    public void shutdown(){
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
    }
}
public class FacadeTest{
    @Test
    public void testFacade(){
        ComputerFacade computer = new ComputerFacade();
        computer.start();
        computer.shutdown();
    }
}
```


## 桥接模式

桥接模式：将抽象类与实现类进行解耦，使二者可以独立变化

```java
public interface Sourceable{
    void method();
}
public class Sub1 implements Sourceable{
    @Override
    public void method(){}
}
public class Sub2 implements Sourceable{
    @Override
    public void method(){}
}

public class Bridge{
    private Sourceable source;
    public void setSource(Sourceable source){
        this.source = souce;
    }
    public void method() {}
}

public class TestBridge{
    @Test
    public void testBridge(){
        Bridge bridge = new Bridge();
        Sourceable sub1 = new Sub1();
        bridge.setSource(sub1);
        bridge.method();
    }
}
```

## 组合模式

组合模式又叫部分-整体模式，在处理类似树形结构时比较方便

```java
public class Node{
    private String name;
    private List<Node> subNodes;
    public void add(Node node){
        subNodes.add(node);
    }
    public void remove(Node node){
        subNodes.remove(node);
    }
    public List<Node> getChildren(){
        return subNodes;
    }
}
public class TestNode{
    @Test
    public void testNode(){
        Node parent = new Node();
        Node sub1 = new Node();
        Node sub2 = new Node();
        parent.add(sub1);
        parent.add(sub2);

        List<Node> subNodes = parent.getChildren();
    }
}
```


## 享元模式

享元模式：实现对象的共享，减少内存开销

数据库连接池
```java

public interface Connection{

}

public class DriverManager{
    public static Connection getConnection(String url,String username,String password);
}

public class ConnectionPool{
    private List<Connection> pool;
    private String url;
    private String username;
    private String password;

    public ConnectionPool(){
        private int poolSize = 10;
        Stream.iterate(0,t->t).limit(10).forEach(t->{
            Connection c = DriverManager.getConnection(url,username,password);
            pool.add(c);
        });
    }

    public Connection getConnection(){
       if(pool.isEmpty){
           Connection c = DriverManager.getConnection(url,username,password);
           pool.add(c);
       }
       return pool.get(0);
    }

    public void release(Connection connection){
        pool.add(connection);
    }
}

```


## 创建型模式

以下为创建型模式

## 原型模式

原型模式的思想是将一个对象，进行复制，克隆成为一个与对象类似的新对象。
关于对象复制有两种概念：浅复制，深复制
浅复制：将一个对象复制后，对象的基本数据类型重新创建赋值，引用类型依然是原有类型
深复制：将一个对象复制后，对象的基本数据类型和引用类型都重新创建赋值

```java
public class ProtoType implements Cloneable,Serializable{
    private static final long serialVersionUID =1L;
    private String str;
    /** 浅复制 */
    @Override
    public Object clone() throws CloneNotSupportedException{
        Prototype prototype = (Prototype) super.clone();
        return prototype;
    }
    /** 深复制 */
    public Object deepClone() throws IOException{
        /* 将当前对象写入二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        /* 从二进制流中读取新对象 */
        ByteArrayInputStream bis = new ByteArrayInputStrean(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
```



## 简单工厂模式

简单工厂模式不属于23种设计模式
分为：普通简单工厂，多方法简单工厂，静态方法简单工厂

工厂模式适合有大量产品需要创建，并且产品都实现了公共的接口

### 普通简单工厂

就是建立一个工厂类，对实现了同一接口的一些类进行实例的创建

```java
public interface sender{
    void send();
}
public class EmailSender implements Sender{
    @Override
    public void send(){
        System.out.println("EmailSender");
    }
}
public class SmsSender implements Sender{
    @Override
    public void send(){
        System.out.println("SmsSender");
    }
}
public class SenderFactory{
    public Sender produce(String name){
        Sender sender = null;
        switch(name){
            case "SmsSender": sender = new SmsSender();break;
            case "EmailSender": sender = new EmailSender();break;
            default:
        }
        return sender;
    }
}
public class FactoryTest{
    @Test
    public void testFactoryProduce(){
         SenderFactory  factory = new SenderFactory();
         Sender sender = factory.produce("EmailSender");
         Assert.assertTrue(sender instanceof EmailSender);
    }
}
```

### 多个方法简单工厂

多个方法简单工厂是对普通模式的改进，如果传入的字符串有误，则创建不了实例对象

```java
public class SenderFactory{
    public Sender produceEmailSender(){
        return new EmailSender();
    }
    public Sender produceSmsSender(){
        return new SmsSender();
    }
}
public class FactoryTest{
    @Test
    public void testProduce(){
        SenderFactory  factory = new SenderFactory();
        Sender sender = factory.produceEmailSender();
        Assert.assertTrue(sender instanceof EmailSender);
    }
}
```

### 静态方法简单工厂

静态方法简单工厂是对多方法简单工厂的改进，多方法简单工厂每次使用前都需要实例化工厂实例

```java
public class SenderFactory{
    public static Sender produceEmailSender(){
        return new EmailSender();
    }
    public static Sender produceSmsSender(){
        return new SmsSender();
    }
}
public class FactoryTest{
    @Test
    public void testProduce(){
        Sender sender = SenderFactory.produceEmailSender();
        Assert.assertTrue(sender instanceof EmailSender);
    }
}
```

## 工厂方法模式

简单工厂模式有个问题，就是每增加一种产品，都需要修改工厂类，违背了设计模式总原则：
对扩展开放，对修改关闭
工厂方法模式的处理办法是，产品实现公共产品接口，工厂实现公共工厂接口
如果要增加一种产品，产品实现公共产品接口，生成对应产品的工厂实现公共工厂接口
调用时，只需要使用对应产品工厂，其他无须改动

```java
public interface SenderFactory{
    Sender produce();
}
public class EmailFactory implements SenderFactory{
    @Override
    public sender produce(){
        return new EmailSender();
    }
}
public class SmsFactory implements SenderFactory{
    @Override
    public Sender produce(){
        return new SmsSender();
    }
}
public class FactoryTest{
    @Test
    public void testProduce(){
        SenderFactory factory = new EmailFactory();
        Sender sender = factory.produce();
        sender.send();
        Assert.assertTrue(sender instanceof EmailSender);
    }
}
```

## 抽象工厂模式

抽象工厂模式是简单工厂模式上的一种改进
原因：工厂方法模式只能生成单一产品，如果要生成多个产品需得在建一座工厂
怎么改进：一个工厂是一个产品线，统一工厂有多个产品线，每个产品线生成同类产品

```java
public interface Computer{
    void calculate();
}
public class MicrosoftComputer implements Computer{
    @Override
    public void calculate(){
        System.out.println("Microsoftcomputer calculate");
    }
}

public interface Factory{
    SenderFactory getSenderFactory(String typeName);
    ComputerFactory getComputerFactory(String typeName);
}
public class DefaultFactory implements Factory{
    @Override
    public Sender getSender(String typeName){
        return null;
    }
    @Override
    public Computer getComputer(String typeName){
        return null;
    }
}
public class SenderFactory extends DefaultFactory{
    @Override
    public Sender getSender(String typeName){
        Sender sender=null;
        switch(typeName){
            case "emailSender": sender = new EmailSender();break;
            case "smsSender": sender = new SmsSender();break;
        }
        return sender;
    }
}
public class ComputerFactory extends DefaultFacotry{
    @Override
    public Computer getComputer(String typeName){
        Computer computer=null;
        switch(typeName){
            case "microsoftComputer": computer = new MicrosoftComputer();break;
        }
        return computer;
    }
}
public class FactoryProducer{
    public static Factory getFactory(String factoryName){
        Factory factory = null;
        switch(factoryName){
            case "senderFactory": factory = new SenderFactory();break;
            case "computerFactory": factory = new ComputerFactory();break;
        }
        return factory;
    }
}

public class FactoryProducerTest{
    @Test
    public void testAbstractFactory(){
        Factory factory = FactoryProdcuer.getFactory("computerFactory");
        Computer computer = factory.getComputer("microsoftComputer");
        Assert.assertTrue(computer instanceof MicrosoftComputer);
        computer.calculate();
    }
}

```

## 建造者模式

工厂模式，关注工厂生产了产品，但不关注产品怎么生产的
建造者模式，关注怎么生产产品

以KFC为例：客户要吃快餐（KFC），购买一个套餐，

一餐（meal）由多种食品组成（多个 item）
食品分汉堡（burger）和冷饮(cold drink)，
汉堡分素食汉堡(veg burger)，鸡肉汉堡（chicken burger），
冷饮分可口可乐(coke)，百世可乐(pepsi)
食品有不同的打包方式(packing)
其中汉堡用纸包装（wrapper），冷饮用瓶装(bottle)

客户可点素食套餐（meal），鸡肉套餐

```java
public interface Packing{
    String pack();
}
public class Wrapper implements Packing{
    @Override
    public String pack(){
        return "wrapper";
    }
}
public class Bottle implements Packing{
    @Override
    public String pack(){
        return "bottle";
    }
}
public abstract class Item{
   private String name;
   private Long price;
   private Packing packing;
   // 省略set/get
}
public abstract class Burger extends Item{
    @Overirde
    public Packing getPacking(){
        return new Wrapper();
    }
}
public class VegBurger extends Burger{
    @Override
    public String getName(){
        return "veg burger";
    }
    @Override
    public Long getPrice(){
        return 11L;
    }
}
public class ChickenBurger extends Burger{
    @Override
    public String getName(){
        return "chicken burger";
    }
    @Override
    public Long getPrice(){
        return 20L;
    }
}
public abstract class ColdDrink extends Item{
    @Overirde
    public Packing getPacking(){
        return new Bottle();
    }
}
public class Coke extends ColdDrink{
    @Override
    public String getName(){
        return "coke";
    }
    @Override
    public Long getPrice(){
        return 10L;
    }
}
public class Pepsi extends ColdDrink{
    @Override
    public String getName(){
        return "pepsi";
    }
    @Override
    public Long getPrice(){
        return 20L;
    }
}
public class Meal{
    private List<Item> itemList = new ArrayList<>();
    public addItem(Item item){
        itemList.add(item);
    }
    public getPrice(){
        AutomicLong price = new AutomicLong(0);
        itemList.foreach(item ->{
            price.set(price.get()+item.getPrice());
        });
        return price;
    }
    public void showItem(){
        System.out.println("套餐食品列表");
        itemList.foreach(item ->{
            System.out.println(itm.getName()+":"+item.getPrice());
        });
    }
}
public class MealBuilder{
    public static Meal vegMeal(){
        Meal meal = new Meal();
        meal.add(new VegBurger());
        meal.add(new Coke());
        return meal;
    }
    public static Meal chickenMeal(){
        Meal meal = new Meal();
        meal.add(new ChickenBurger());
        meal.add(new Pepsi());
        return meal;
    }
}
public class MealBuilderTest{
    @Test
    public void testBuilder(){
        Meal meal = MealBuilder.chickenMeal();
        Long price = meal.getPrice();
    }
}
```

## 单例模式

单例对象的类只允许有一个实例存在

### 单列模式的八种写法

+ 饿汉式（静态常量）

```java
public class Singleton{
    private static Singleton INSTANCE = new Singleton();

    private Singleton(){}

    public static Singleton getInstance(){
        return INSTANCE;
    }
}
```

+ 饿汉式（静态代码块）

```java
public class Singleton{
    private static Singleton INSTANCE;

    private Singleton(){}

    static{
        INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return INSTANCE;
    }
}
```

+ 懒汉式（线程不安全）

```java
public class Singleton{
    private static Singleton INSTANCE;

    private Singleton(){}

    public static Singleton getInstance(){
        if(INSTANCE != null){
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }
}
```

+ 懒汉式（线程安全,同步方法）

```java
public class Singleton{
    private static Singleton INSTANCE;

    private Singleton(){}

    public static synchronized Singleton getInstance(){
        if(INSTANCE != null){
            INSTANCE = new Singleton();
        }
        return INSTANCE;
    }
}
```

+ 懒汉式（线程安全,同步代码块）

```java
public class Singleton{
    private static Singleton INSTANCE;

    private Singleton(){}

    public static Singleton getInstance(){
        synchronized(Singleton.class){
            if(INSTANCE != null){
                INSTANCE = new Singleton();
            }
        }
        return INSTANCE;
    }
}
```

+ 懒汉式（线程安全,同步代码块,双重检查）

```java
public class Singleton{
    private static volatile Singleton INSTANCE;

    private Singleton(){}

    public static Singleton getInstance(){
        if(INSTANCE != null){
            synchronized(Singleton.class){
                if(INSTANCE != null){
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}
```

+ 静态内部类

```java
public class Singleton{
    private Singleton(){}

    private static class SingletonInstance{
        public static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonInstance.INSTANCE;
    }
}
```

+ 枚举

```java
public enum Singleton{
    INSTANCE;
}
```

