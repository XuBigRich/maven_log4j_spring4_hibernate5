# **日志框架的学习**

    基础部分
        日志框架简单比较（slf4j、log4j、logback、log4j2 ）
        log4j2基础示例
        log4j2配置文件
    实战部分
        slf4j + log4j2 实际使用

## 日志接口(slf4j)

* slf4j是对所有日志框架制定的一种规范、标准、接口，并不是一个框架的具体的实现，因为接口并不能独立使用，需要和具体的日志框架实现配合使用（如log4j、log4j2、logback）

## 日志实现(log4j、logback、log4j2)

* log4j是apache实现的一个开源日志组件
* logback同样是由log4j的作者设计完成的，拥有更好的特性，用来取代log4j的一个日志框架，是slf4j的原生实现
* Log4j2是log4j 1.x和logback的改进版，据说采用了一些新技术（无锁异步、等等），使得日志的吞吐量、性能比log4j 1.x提高10倍，并解决了一些死锁的bug，而且配置更加简单灵活，

## 为什么需要日志接口，直接使用具体的实现不就行了吗？

接口用于定制规范，可以有多个实现，使用时是面向接口的（导入的包都是slf4j的包而不是具体某个日志框架中的包），即直接和接口交互，不直接使用实现，所以可以任意的更换实现而不用更改代码中的日志相关代码。

比如：slf4j定义了一套日志接口，项目中使用的日志框架是logback，开发中调用的所有接口都是slf4j的，不直接使用logback，调用是
自己的工程调用slf4j的接口，slf4j的接口去调用logback的实现，可以看到整个过程应用程序并没有直接使用logback，当项目需要更换更加优秀的日志框架时（如log4j2）只需要引入Log4j2的jar和Log4j2对应的配置文件即可，完全不用更改Java代码中的日志相关的代码logger.info(
“xxx”)，也不用修改日志相关的类的导入的包（import org.slf4j.Logger; import org.slf4j.LoggerFactory;）

使用日志接口便于更换为其他日志框架。

## log4j2基础示例

* 引入log4j2必要的依赖(log4j-api、log4j-core)

---

```
<dependencies>
	<dependency>  
        <groupId>org.apache.logging.log4j</groupId>  
        <artifactId>log4j-api</artifactId>  
        <version>${log4j.version}</version>  
    </dependency>  
    <dependency>  
        <groupId>org.apache.logging.log4j</groupId>  
        <artifactId>log4j-core</artifactId>  
        <version>${log4j.version}</version>  
    </dependency>  
</dependencies>
```

# 开始学习 log4j

* 学习前我们要知道在日志中， 共有8个级别，按照粒度从低到高为：ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF，日志级别越低，记录的粒度越小，记录的东西越多

---
Log4j中 rootLogger 是设置整体日志记录的级别的。

而每一个appender 是用来定义 当前他所关注的 日志级别。

但是当 整日日志级别粒度 高于 appender 定义所关注的日志级别时,那么依照rootLogger的日志级别去定义。

## 典型例子

````
log4j.rootLogger=OFF,D
log4j.appender.D.Threshold=ALL   
````

上面的整体日志级别为关闭状态,但是appender.D 所关注的日志级别为ALL 为全部, 但是依照log4j的定义,这个日志是一个关闭状态的

## 配置文件中的各种含义

1.配置日志信息输出目的地Appender

* Appender的配置方式：

```
log4j.appender.appenderName = Log4j提供的appender类(下面有详细介绍)
log4j.appender.appenderName.属性名 = 属性值
.....
log4j.appender.appenderName.属性名 = 属性值
```

* Appender的配置详解

1. Log4j提供的appender有以下5种，分别可以将日志信息输出到5个不同的平台

```
org.apache.log4j.ConsoleAppender（控制台）
org.apache.log4j.FileAppender（文件）
org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）
org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）
```

* ConsoleAppender

````
Threshold=WARN：指定日志消息的输出最低层次。
ImmediateFlush=true：默认值是true,意谓着所有的消息都会被立即输出。
Target=System.err：默认情况下是：System.out,指定输出控制台
````

* FileAppender

````
Threshold=WARN：指定日志消息的输出最低层次。
ImmediateFlush=true：默认值是true,意谓着所有的消息都会被立即输出。
File=mylog.txt：指定消息输出到mylog.txt文件。
Append=false：默认值是true,即将消息增加到指定文件中，false指将消息覆盖指定的文件内容。
DailyRollingFileAppender
Threshold=WARN：指定日志消息的输出最低层次。
ImmediateFlush=true：默认值是true,意谓着所有的消息都会被立即输出。
File=mylog.txt：指定消息输出到mylog.txt文件。
Append=false：默认值是true,即将消息增加到指定文件中，false指将消息覆盖指定的文件内容。
DatePattern=”.”yyyy-ww:每周滚动一次文件，即每周产生一个新的文件。

当然也可以指定按月、周、天、时和分。即对应的格式如下：
---------------------------------------------------
1)”.”yyyy-MM: 每月
2)”.”yyyy-ww: 每周
3)”.”yyyy-MM-dd: 每天
4)”.”yyyy-MM-dd-a: 每天两次
5)”.”yyyy-MM-dd-HH: 每小时
6)”.”yyyy-MM-dd-HH-mm: 每分钟
````

* RollingFileAppender

````
Threshold=WARN：指定日志消息的输出最低层次。
ImmediateFlush=true：默认值是true,意谓着所有的消息都会被立即输出。
File=mylog.txt：指定消息输出到mylog.txt文件。
Append=false：默认值是true,即将消息增加到指定文件中，false指将消息覆盖指定的文件内容。
MaxFileSize=100KB：后缀可以是KB, MB 或者是 GB. 在日志文件到达该大小时，将会自动滚动，即将原来的内容移到mylog.log.1文件。
MaxBackupIndex=2：指定可以产生的滚动文件的最大数。
````

2.配置日志信息的格式（布局） 日志信息格式的配置方式：

````
log4j.appender.appenderName.layout = Log4j提供的layout类
log4j.appender.appenderName.layout.属性 = 值
.....
log4j.appender.appenderName.layout.属性 = 值
````

日志信息格式的配置详解 Log4j提供的layout有以下几种：

````
org.apache.log4j.HTMLLayout（以HTML表格形式布局），
org.apache.log4j.PatternLayout（可以灵活地指定布局模式），
org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串），
org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等等信息）
````

HTMLLayout:

````
LocationInfo=true：默认值是false,输出java文件名称和行号 
Title=my app file：默认值是 Log4J Log Messages
````

PatternLayout:

````
ConversionPattern=%m%n：指定怎样格式化指定的消息。
````

XMLLayout:

````
LocationInfo=true：默认值是false,输出java文件和行号 

````

Log4J采用类似C语言中的printf函数的打印格式格式化日志信息，打印参数如下：

````
Log4j.appender.A1.layout.ConversionPattern=%-4r %-5p %d{yyyy-MM-dd HH:mm:ssS} %c %m%n
````

其含义 日志信息格式中几个符号所代表的含义：

````
-X号: X信息输出时左对齐；
%p: 输出日志信息优先级，即DEBUG，INFO，WARN，ERROR，FATAL,
%d: 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，
比如：%d{yyy MMM dd HH:mm:ss,SSS}，输出类似：2002年10月18日 22：10：28，921
%r: 输出自应用启动到输出该log信息耗费的毫秒数
%c: 输出日志信息所属的类目，通常就是所在类的全名
%t: 输出产生该日志事件的线程名
%l: 输出日志事件的发生位置，相当于%C.%M(%F:%L)的组合,包括类目名、发生的线程，以及行数。
举例：Testlog4.main(TestLog4.java:10)
%x: 输出和当前线程相关联的NDC(嵌套诊断环境)，尤其用到像java servlets这样的多客户多线程的应用中。
%%: 输出一个”%”字符
%F: 输出日志消息产生时所在的文件名称
%L: 输出代码中的行号
%m: 输出代码中指定的消息,产生的日志具体信息
%n: 输出一个回车换行符，Windows平台为”\r\n”，Unix平台为”\n”输出日志信息换行

可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度、和文本的对齐方式。如：

1)%20c：指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，默认的情况下右对齐。
2)%-20c:指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，”-“号指定左对齐。
3)%.30c:指定输出category的名称，最大的宽度是30，如果category的名称大于30的话，就会将左边多出的字符截掉，但小于30的话也不会有空格。
4)%20.30c:如果category的名称小于20就补空格，并且右对齐，如果其名称长于30字符，就从左边交远销出的字符截掉
````

## appender的日志输出种类(Log4j提供的appender类)

* ConsoleAppender，控制台输出
* FileAppender，文件日志输出
* SMTPAppender，发邮件输出日志
* SocketAppender，Socket 日志
* NTEventLogAppender，Window NT 日志
* SyslogAppender，
* JMSAppender，
* AsyncAppender，
* NullAppender
* RollingFileAppender 文件输出

## 输出详细内容举例

````
#log4j.rootLogger = INFO,logfile
#文件输出：RollingFileAppender
log4j.appender.logfile = org.apache.log4j.RollingFileAppender
# 输出以上的 INFO 信息
log4j.appender.logfile.Threshold = INFO
#保存 log 文件路径
log4j.appender.logfile.File = INFO_log.html
# 默认为 true，添加到末尾，false 在每次启动时进行覆盖
log4j.appender.logfile.Append = true
# 一个 log 文件的大小，超过这个大小就又会生成 1 个日志 # KB ，MB，GB
log4j.appender.logfile.MaxFileSize = 1MB
# 最多保存 3 个文件备份
log4j.appender.logfile.MaxBackupIndex = 3
# 输出文件的格式
log4j.appender.logfile.layout = org.apache.log4j.HTMLLayout
#是否显示类名和行数
log4j.appender.logfile.layout.LocationInfo = true
#html 页面的 < title >
############################## SampleLayout ####################################
log4j.appender.logfile.layout.Title
=title:\u63d0\u9192\u60a8\uff1a\u7cfb\u7edf\u53d1\u751f\u4e86\u4e25\u91cd\u9519\u8b
ef

````

除此之外还有其他 记录日志的形式
---

````
#每天文件的输出：DailyRollingFileAppender
#log4j.rootLogger = INFO,errorlogfile
log4j.appender.errorlogfile = org.apache.log4j.DailyRollingFileAppender
log4j.appender.errorlogfile.Threshold = ERROR
log4j.appender.errorlogfile.File = ../logs/ERROR_log
log4j.appender.errorlogfile.Append = true
#默认为 true，添加到末尾，false 在每次启动时进行覆盖
log4j.appender.errorlogfile.ImmediateFlush = true
#直接输出，不进行缓存
# ' . ' yyyy - MM: 每个月更新一个 log 日志
# ' . ' yyyy - ww: 每个星期更新一个 log 日志
# ' . ' yyyy - MM - dd: 每天更新一个 log 日志
# ' . ' yyyy - MM - dd - a: 每天的午夜和正午更新一个 log 日志
# ' . ' yyyy - MM - dd - HH: 每小时更新一个 log 日志
# ' . ' yyyy - MM - dd - HH - mm: 每分钟更新一个 log 日志
Log4j 从入门到详解
11
log4j.appender.errorlogfile.DatePattern = ' . ' yyyy - MM - dd ' .log '
#文件名称的格式
log4j.appender.errorlogfile.layout = org.apache.log4j.PatternLayout
log4j.appender.errorlogfile.layout.ConversionPattern =%d %p [ %c] - %m %n %d
````

## 控制台输出：

````

#log4j.rootLogger = INFO,consoleAppender
log4j.appender.consoleAppender = org.apache.log4j.ConsoleAppender
log4j.appender.consoleAppender.Threshold = ERROR
log4j.appender.consoleAppender.layout = org.apache.log4j.PatternLayout
log4j.appender.consoleAppender.layout.ConversionPattern =%d %-5p %m %n
log4j.appender.consoleAppender.ImmediateFlush = true
# 直接输出，不进行缓存
log4j.appender.consoleAppender.Target = System.err
# 默认是 System.out 方式输出
````

## 发送邮件：SMTPAppender

````

#log4j.rootLogger = INFO,MAIL
log4j.appender.MAIL = org.apache.log4j.net.SMTPAppender
log4j.appender.MAIL.Threshold = INFO
log4j.appender.MAIL.BufferSize = 10
log4j.appender.MAIL.From = yourmail@gmail.com
log4j.appender.MAIL.SMTPHost = smtp.gmail.com
log4j.appender.MAIL.Subject = Log4J Message
log4j.appender.MAIL.To = yourmail@gmail.com
log4j.appender.MAIL.layout = org.apache.log4j.PatternLayout
log4j.appender.MAIL.layout.ConversionPattern =%d - %c -%-4r [%t] %-5p %c %x - %m %n
````

## 数据库：JDBCAppender

````
log4j.appender.DATABASE = org.apache.log4j.jdbc.JDBCAppender
log4j.appender.DATABASE.URL = jdbc:oracle:thin:@ 210.51 . 173.94 : 1521 :YDB
log4j.appender.DATABASE.driver = oracle.jdbc.driver.OracleDriver
log4j.appender.DATABASE.user = ydbuser
log4j.appender.DATABASE.password = ydbuser
log4j.appender.DATABASE.sql = INSERT INTO A1 (TITLE3) VALUES ( ' %d - %c %-5p %c %x - %m%n
' )
log4j.appender.DATABASE.layout = org.apache.log4j.PatternLayout
log4j.appender.DATABASE.layout.ConversionPattern =% d - % c -%- 4r [ % t] %- 5p % c %
x - % m % n
#数据库的链接会有问题，可以重写 org.apache.log4j.jdbc.JDBCAppender 的 getConnection() 使用数
````

# 开始学习 log4j2

关于log4j2 大部分与上面相同这里只着重讲解一个东西

````
   <ThresholdFilter level="warn" onMatch="DENY" onMismatch="NEUTRAL"/>
````
Filters

Filters决定日志事件能否被输出。过滤条件有三个值：ACCEPT(接受)，DENY(拒绝)，NEUTRAL(中立)。

log4j2中的过滤器ACCEPT和DENY之后，后续的过滤器就不会执行了，只有在NEUTRAL的时候才会执行后续的过滤器。
常用的Filter实现类有：

    LevelRangeFilter
    TimeFilter
    ThresholdFilter

ThresholdFilter

一. 属性详解

onMatch="ACCEPT"匹配该级别及以上级别;

onMatch="DENY"不匹配该级别及以上级别;

onMismatch="ACCEPT" 表示匹配该级别以下的级别;

onMismatch="DENY" 表示不匹配该级别以下的级别;

二. 单一应用

    匹配INFO级别以及以上级别,不匹配INFO级别以下级别,即: 匹配 >= INFO的级别
    <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
    不匹配WARN级别以及以上级别,匹配WARN级别以下级别,即: 匹配 < WARN的级别
    <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="ACCEPT"/>
    三. 组合应用
    <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
    <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>