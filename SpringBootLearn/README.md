# Spring Boot

* 简化Spring应用开发的一个框架，是整个Spring技术栈的一个大整合

### 微服务

* 将服务微小化，一个应用应该是一组小型服务，可以通过HTTP的方式进行互通
* 每一个功能元素最终都是一个可独立替换和独立升级的软件单元
* [详细参照微服务文档](https://martinfowler.com/articles/microservices.html#MicroservicesAndSoa)
* [中文文档](http://blog.cuicc.com/blog/2015/07/22/microservices/)

### 开发流程

* Maven设置：settings.xml配置文件添加profiles标签

```xml
<!--配置开发环境-->
<profile>
    <id>jdk-1.8</id>
    <activation>
        <activeByDefault>true</activeByDefault>
        <jdk>1.8</jdk>
    </activation>
    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
    </properties>
</profile>
```

* IDEA设置：修改使用的Maven配置，不要使用IDEA自带的
* 使用Spring Initializer快速创建Spring Boot项目
* 创建HelloController.java，浏览器发送请求，服务器接受请求并处理，响应Hello World字符串

````java
/**
 * 控制器
 * 注解@Controller和@ResponseBody可以使用@RestController代替
 */
@Controller
@ResponseBody
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
````

### POM文件解析

#### 父项目

```xml
<!--开发项目的父项目-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.9.RELEASE</version>
</parent>
```

* 上述代码的父项目

```xml
<!--
真正管理Spring Boot应用里面的所有的依赖版本
spring-boot-dependencies中的定义了每一个依赖的版本
如果没有在dependencies里面管理的依赖自然需要声明版本号
-->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>1.5.9.RELEASE</version>
    <relativePath>../../spring-boot-dependencies</relativePath>
</parent>
```

### 启动器

* Spring Boot将所有的功能场景都抽取出来，做成一个个的starters（启动器），只需要在项目里面引入这些starter，相关场景的所有依赖都会导入进来
* [各种启动器的文档](https://docs.spring.io/spring-boot/docs/2.4.4/reference/html/using-spring-boot.html#using-boot-starter)

```xml
<!--
spring-boot-starter-web：spring-boot场景启动器，导入了web模块正常运行所依赖的组件
-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### 打包插件

```xml
<!--
可以将应用打包成一个可执行的jar包
直接使用java -jar [jar包]的命令进行执行
-->
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

### 主程序类

```java
/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 *  说明这个类是SpringBoot的主配置类，SpringBoot运行这个类的main方法来启动应用
 */
@SpringBootApplication
public class SpringBootLearnApplication {
    public static void main(String[] args) {
        // Spring应用启动起来
        SpringApplication.run(SpringBootLearnApplication.class, args);
    }
}
```

```java
/**
 * @SpringBootConfiguration SpringBoot的配置类，标记在某个类上，表示这是一个SpringBoot的配置类
 * @EnableAutoConfiguration 开启自动配置功能
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
        excludeFilters = {@Filter(
                type = FilterType.CUSTOM,
                classes = {TypeExcludeFilter.class}
        ), @Filter(
                type = FilterType.CUSTOM,
                classes = {AutoConfigurationExcludeFilter.class}
        )}
)
public @interface SpringBootApplication {
}
```

```java
/**
 * @Configuration 配置类上标记该注解，配置类Configuration也是个容器，被注解@Component标记
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {
}
```

```java
/**
 *  @AutoConfigurationPackage 自动配置包
 *  @Import 给容器导入一个组件
 *
 *  @Import({AutoConfigurationImportSelector.class})
 *  AutoConfigurationImportSelector（导入组件的选择器）中执行方法getAutoConfigurationEntry，执行方法getCandidateConfigurations将所有需要导入的组件以全类名的方式返回，
 *  这些组件就会被添加到容器中，会为容器导入更多的自动配置类(xxAutoConfiguration)，这就是给容器导入该场景下所有的组件，并配置好这些组件
 *  注意：其中方法getCandidateConfigurations中的SpringFactoriesLoader.loadFactoryNames方法会从(spring-boot-autoconfigure包下)META-INF/spring.factories中获取需要导入的配置
 *  J2EE的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.5.9.RELEASE.jar
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
}
```

```java
/**
 * @Import({AutoConfigurationPackages.Registrar.class})
 * 将主配置类(被注解SpringBootApplication标记的类)的所在包及下面所有子包里面的所有组件扫描到Spring容器中
 * 即Registrar类中的registerBeanDefinitions方法
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({Registrar.class})
public @interface AutoConfigurationPackage {
}
```

### IDEA：使用 Spring Initializer快速创建项目

* resources文件夹中目录结构
    * static：保存所有的静态资源，包括js css images等
    * templates：保存所有的模板页面，Spring Boot默认jar包使用嵌入式的Tomcat，默认不支持JSP页面，但是可以使用模板引擎(freemarker、thymeleaf)
    * application.properties：Spring Boot应用的配置文件；可以修改一些默认设置