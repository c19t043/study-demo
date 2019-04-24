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
    pubilc Sender produceSmsSender(){
        return new SmsSender();
    }
}
public class FactoryTest(){
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
    pubilc static Sender produceSmsSender(){
        return new SmsSender();
    }
}
public class FactoryTest(){
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

public interface class Factory{
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
        Assert.assertTrue(computer instance MicrosoftComputer);
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

```
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
    private static class Singleton INSTANCE = new Singleton();

    private Singleton(){}

    public static Singleton getInstance(){
        return INSTANCE;
    }
}
```

+ 饿汉式（静态代码块）

```java
public class Singleton{
    private static class Singleton INSTANCE;

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
    private static class Singleton INSTANCE;

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
    private static class Singleton INSTANCE;

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
    private static class Singleton INSTANCE;

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
    private static volatile class Singleton INSTANCE;

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

