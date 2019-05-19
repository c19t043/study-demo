# 项目

## 项目打包工具

### maven插件

+ 一般maven项目编译工具

```text
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.8.0</version>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <encoding>utf-8</encoding>
    </configuration>
</plugin>
```

+ springboot 项目打包工具 

```text
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>2.1.1.RELEASE</version>
    <configuration>
        <fork>true</fork>
        <mainClass>可执行程序入口类</mainClass>
    </configuration>
</dependency>
```

+ docker 项目打包工具
