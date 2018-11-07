# log
## pom
~~~
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.6.1</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>1.6.1</version>
    </dependency>
~~~

## log4j.properties
~~~properties
log4j.rootLogger=DEBUG,consoleAppender,logfile

log4j.appender.consoleAppender=org.apache.log4j.ConsoleAppender
log4j.appender.consoleAppender.layout=org.apache.log4j.PatternLayout
log4j.appender.consoleAppender.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss,SSS} [%l]-[%p] %m%n

log4j.appender.logfile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.logfile.File=/home/admin/demo/logs/demo.log
log4j.appender.logfile.Append = true
log4j.appender.logfile.DatePattern='.'yyyy-MM-dd
#log4j.appender.logfile=org.apache.log4j.RollingFileAppender
#log4j.appender.logfile.File=/home/admin/demo/logs/demo.log
#log4j.appender.logfile.Append = true
#log4j.appender.logfile.MaxFileSize = 10MB
#log4j.appender.logfile.MaxBackupIndex = 20
log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
log4j.appender.logfile.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss,SSS} [%t] [%c] [%p] - %m%n
~~~

##配置说明

~~~
#配置日志信息输出目的地

#log4j.appender.appenderName=

#1.org.apache.log4j.ConsoleAppender（控制台）
#2.org.apache.log4j.FileAppender（文件）
#3.org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）
#4.org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
#5.org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）


#ConsoleAppender选项属性
# -Threshold = DEBUG:指定日志消息的输出最低层次
# -ImmediateFlush = TRUE:默认值是true,所有的消息都会被立即输出
# -Target = System.err:默认值System.out,输出到控制台


#FileAppender选项属性
# -Threshold = INFO:指定日志消息的输出最低层次
# -ImmediateFlush = TRUE:默认值是true,所有的消息都会被立即输出
# -File = F:\log.log:指定消息输出到F:\log.log文件
# -Append = FALSE:默认值true,将消息追加到指定文件中，false指将消息覆盖指定的文件内容
# -Encoding = UTF-8:可以指定文件编码格式


#DailyRollingFileAppender选项属性
# -Threshold = WARN:指定日志消息的输出最低层次
# -ImmediateFlush = TRUE:默认值是true,所有的消息都会被立即输出
# -File = E:\log.log:指定消息输出到E:\log.log文件
# -Append = FALSE:默认值true,将消息追加到指定文件中，false指将消息覆盖指定的文件内容
# -DatePattern='.'yyyy-MM-dd:每天滚动一次文件,即每周产生一个新的文件。该参数可变。
# -Encoding = UTF-8:可以指定文件编码格式


#RollingFileAppender选项属性
# -Threshold = ERROR:指定日志消息的输出最低层次
# -ImmediateFlush = TRUE:默认值是true,所有的消息都会被立即输出
# -File = D:/log.log:指定消息输出到D:/log.log文件
# -Append = FALSE:默认值true,将消息追加到指定文件中，false指将消息覆盖指定的文件内容
# -MaxFileSize = 100KB:后缀可以是KB,MB,GB.在日志文件到达该大小时,将会自动滚动.如:log.log.1
# -MaxBackupIndex = 2:指定可以产生的滚动文件的最大数
# -Encoding = UTF-8:可以指定文件编码格式


#配置日志信息的格式

#log4j.appender.appenderName.layout =

#1.org.apache.log4j.HTMLLayout（以HTML表格形式布局）
#2.org.apache.log4j.PatternLayout（可以灵活地指定布局模式）
#3.org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串）
#4.org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等等信息）


#日志信息格式中几个符号所代表的含义

# -X号: X信息输出时左对齐；
# %p: 输出日志信息优先级，即DEBUG，INFO，WARN，ERROR，FATAL,
# %d: 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyy MMM dd HH:mm:ss,SSS}，输出类似：2002年10月18日 22：10：28，921
# %r: 输出自应用启动到输出该log信息耗费的毫秒数
# %c: 输出日志信息所属的类目，通常就是所在类的全名
# %t: 输出产生该日志事件的线程名
# %l: 输出日志事件的发生位置，相当于%C.%M(%F:%L)的组合,包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main (TestLog4.java:10)
# %x: 输出和当前线程相关联的NDC(嵌套诊断环境),尤其用到像java servlets这样的多客户多线程的应用中。
# %%: 输出一个”%”字符
# %F: 输出日志消息产生时所在的文件名称
# %L: 输出代码中的行号
# %m: 输出代码中指定的消息,产生的日志具体信息
# %n: 输出一个回车换行符，Windows平台为”\r\n”，Unix平台为”\n”输出日志信息换行
~~~
