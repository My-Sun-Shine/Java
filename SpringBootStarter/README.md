# 自定义starter

1. 新建自定义starter的自动配置模块，以及启动器模块

2. 自动配置模块pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.stringboot.starter</groupId>
    <artifactId>spring-boot-starter-autoconfigurer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-boot-starter-autoconfigurer</name>
    <description>Demo project for Spring Boot</description>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <!--引入spring-boot-starter；所有starter的基本配置-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

3. 启动器模块pom.xml，引入自动配置模块

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.example</groupId>
    <artifactId>com.springboot.starter</artifactId>
    <version>1.0-SNAPSHOT</version>
    <!--启动器-->
    <dependencies>
        <!--引入自动配置模块-->
        <dependency>
            <groupId>com.stringboot.starter</groupId>
            <artifactId>spring-boot-starter-autoconfigurer</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
    </dependencies>
</project>
```

4. 在自动配置模块新建HelloProperties，HelloService，HelloServiceAutoConfiguration

```java
//@ConfigurationPropertie结合相关xxxProperties类来绑定相关的配置
@ConfigurationProperties(prefix = "springboot.hello")
public class HelloProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

public class HelloService {

    HelloProperties helloProperties;

    public HelloProperties getHelloProperties() {
        return helloProperties;
    }

    public void setHelloProperties(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHello(String name) {
        return helloProperties.getPrefix() + "-" + name + helloProperties.getSuffix();
    }
}

@Configuration //指定这个类是一个配置类
//@ConditionalOnXXX  //在指定条件成立的情况下自动配置类生效
@ConditionalOnWebApplication //web应用才生效
//@AutoConfigureAfter  //指定自动配置类的顺序
//@EnableConfigurationProperties //让xxxProperties生效加入到容器中
@EnableConfigurationProperties(HelloProperties.class)
public class HelloServiceAutoConfiguration {

    @Autowired
    HelloProperties helloProperties;

    @Bean //给容器中添加组件
    public HelloService helloService() {
        HelloService service = new HelloService();
        service.setHelloProperties(helloProperties);
        return service;
    }
}

```

5. 在自动配置模块中的resources/META-INF下添加spring.factories

```properties
# 自动配置类要能加载
# 将需要启动就加载的自动配置类，配置在META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.stringboot.starter.HelloServiceAutoConfiguration
```

6. 添加测试模块spring-boot-starter-test

7. 测试模块引入依赖

```xml
<!--引入自定义的starter-->
<dependency>
    <groupId>org.example</groupId>
    <artifactId>com.springboot.starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

8. 测试

```java

@SpringBootTest
class SpringBootStarterTestApplicationTests {
    @Autowired
    HelloService helloService;

    @Test
    void contextLoads() {
        System.out.println(helloService.sayHello("世界"));
    }
}
```

# [更多SpringBoot整合示例](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples)















