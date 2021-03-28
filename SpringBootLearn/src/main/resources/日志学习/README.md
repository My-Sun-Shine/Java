# 日志

### 日志框架

* **市面上的日志框架**

| 日志门面 （日志的抽象层）                           | 日志实现                                     |
| ---------------------------------------- | ---------------------------------------- |
| JCL(Jakarta Commons Logging)、SLF4j(Simple Logging Facade for Java)、jboss-logging | Log4j、JUL(java.util.logging)、Log4j2、Logback |

* SpringBoot选用SLF4j(日志门面)和logback(日志实现)
* Spring框架默认是用JCL

### SLF4j使用

* 使用[SLF4j](https://www.slf4j.org)
    * 开发的时候，日志记录方法的调用，不应该来直接调用日志的实现类，而是调用日志抽象层里面的方法

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld.class);
        logger.info("Hello World");
    }
}
```

* 使用SLF4J时，需要使用对应的日志门面slf4j-api.jar，有些日志实现不支持直接被slf4j调用，需要使用适配器，如图：

![images/concrete-bindings.png](images/concrete-bindings.png)

* **遗留问题：不同框架使用不同的日志框架，需要进行统一日志记录，统一使用slf4j进行输出**
* **如何让系统中所有的日志都统一到slf4j**
    * 1、将系统中其他日志框架先排除出去
    * 2、用中间包来替换原有的日志框架
    * 3、我们导入slf4j其他的实现

![images/legacy.png](images/legacy.png)

### SpringBoot日志关系

```xml
<!--SpringBoot使用它来做日志功能-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
</dependencies>
```

* 底层依赖关系
    * SpringBoot底层是使用slf4j+logback的方式进行日志记录
    * SpringBoot也把其他的日志都替换成了slf4j，使用中间替换包
    * 因为中间替换包的命名空间和原包的一致，所以必须在引入其他框架，一定要把这个框架的默认日志依赖移除掉
    * SpringBoot能自动适配所有的日志，而且底层使用slf4j+logback的方式记录日志，引入其他框架的时候，只需要把这个框架依赖的日志框架排除掉即可

![images/image01.png](images/image01.png)

### 日志使用；

#### 默认配置：SpringBoot默认帮我们配置好了日志

```java

@SpringBootTest
class SpringBootLearnApplicationTests {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    void TestSLF4J() {
        //日志的级别；
        //由低到高   trace < debug < info < warn < error
        //可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");
    }
}
```

#### SpringBoot修改日志的默认配置

```properties
# 可以查看org.springframework.boot:spring-boot中的logging文件夹的logback文件夹
# 修改日志的默认配置，后面跟的com.springbootlearn是包路径
logging.level.com.springbootlearn=trace
# 1.使用相对路径，就会在项目根目录下生成一个springboot.log文件
#logging.file.name=springboot.log
# 2.在项目根目录下生成一个logs文件夹，logs文件夹里面生成一个springboot.log文件。
#logging.file.name=logs/springboot.log
# 3.使用绝对路径，这样写会在D盘下创建一个springboot.log日志
#logging.file.name=D:/springboot.log
# 4.使用相对路径，会在项目根目录下生成一个spring文件夹，logs文件夹会有一个spring.log文件。
logging.file.path=spring
# logging.file.name和logging.file.path不要同时指定，只需要指定一个，都不指定时只在控制台上输出
# 相对路径不指定盘符，直接在项目根目录下生成
# 日志输出格式：
#   %d表示日期时间，
#   %thread表示线程名，
#   %-5level：级别从左显示5个字符宽度
#   %logger{50} 表示logger名字最长50个字符，否则按照句点分割。
#   %msg：日志消息，
#   %n是换行符
# 在控制台输出的日志的格式
logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
# 指定文件中日志输出的格式
logging.pattern.file=%d{yyyy-MM-dd} === [%thread] === %-5level === %logger{50} ==== %msg%n
```

#### 指定配置

* 给类路径下放上每个日志框架自己的配置文件即可；SpringBoot就不使用默认配置
* logback.xml：这样不带spring的配置文件会直接被日志框架识别
* **logback-spring.xml**：日志框架就不直接加载日志的配置项，由SpringBoot解析日志配置，可以使用SpringBoot的高级Profile功能
* 如果使用logback.xml作为日志配置文件，还要使用profile功能，会有以下错误`no applicable action for [springProfile]`

| Logging System          | Customization                            |
| ----------------------- | ---------------------------------------- |
| Logback                 | `logback-spring.xml`, `logback-spring.groovy`, `logback.xml` or `logback.groovy` |
| Log4j2                  | `log4j2-spring.xml` or `log4j2.xml`      |
| JDK (Java Util Logging) | `logging.properties`                     |

```xml
<!--查看配置文件logback-spring.xml-->
<springProfile name="staging">
    <!-- configuration to be enabled when the "staging" profile is active -->
    可以指定某段配置只在某个环境下生效
</springProfile>
```

```xml
<!--查看配置文件logback-spring.xml-->
<appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
    <!--
    日志输出格式：
        %d表示日期时间，
        %thread表示线程名，
        %-5level：级别从左显示5个字符宽度
        %logger{50} 表示logger名字最长50个字符，否则按照句点分割。 
        %msg：日志消息，
        %n是换行符
    -->
    <layout class="ch.qos.logback.classic.PatternLayout">
        <springProfile name="dev">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ----> [%thread] ---> %-5level %logger{50} - %msg%n</pattern>
        </springProfile>
        <springProfile name="!dev">
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} ==== [%thread] ==== %-5level %logger{50} - %msg%n</pattern>
        </springProfile>
    </layout>
</appender>
```

### 切换日志框架

```xml
<!--
切换slf4j+log4j的方式
按照slf4j的日志适配图，排除logback-classic和log4j-to-slf4j，然后添加slf4j-log4j12和log4j.properties
-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <artifactId>logback-classic</artifactId>
                <groupId>ch.qos.logback</groupId>
            </exclusion>
            <exclusion>
                <artifactId>log4j-to-slf4j</artifactId>
                <groupId>org.apache.logging.log4j</groupId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.21</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

```xml
<!--切换为log4j2-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <artifactId>spring-boot-starter-logging</artifactId>
                <groupId>org.springframework.boot</groupId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
</dependencies>
```
