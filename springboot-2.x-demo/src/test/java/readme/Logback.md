
+ Logback 是没有 FATAL级别的日志，它将被映射到 ERROR

```
2014-03-05 10:57:51.702  INFO 45469 --- [ost-startStop-1] o.s.b.c.embedded.FilterRegistrationBean  : Mapping filter: 'hiddenHttpMethodFilter' to: [/*]

时间日期：精确到毫秒,可以用于排序
日志级别：ERROR、WARN、INFO、DEBUG、TRACE
进程ID
分隔符：采用 --- 来标识日志开始部分
线程名：方括号括起来（可能会截断控制台输出）
Logger名：通常使用源代码的类名
日志内容：我们输出的消息
```



