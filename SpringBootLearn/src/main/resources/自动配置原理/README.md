### 不同位置的配置文件加载优先级

* SpringBoot启动时会扫描以下位置的application.properties或者application.yml文件作为SpringBoot的默认配置文件
* SpringBoot会从以下四个位置加载配置，优先级由高到底；存在相同配置时高优先级的配置会覆盖低优先级的配置；不同配置进行互补

```
# 分别在四个位置新建application.properties文件
–file:./config/
–file:./
–classpath:/config/
–classpath:/
```

* 命令行改变配置文件加载位置：java -jar springbootlearn-0.0.1-SNAPSHOT.jar --spring.config.location=D:/application.properties

### 外部配置加载顺序

* SpringBoot也可以从以下位置加载配置，优先级从高到低；存在相同配置时高优先级的配置会覆盖低优先级的配置；不同配置进行互补
* 所有支持的配置加载来源：
    * [1.5.9参考官方文档](https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#boot-features-external-config)
    * [2.2.4参考官方文档](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-external-config)

1. **命令行参数**
    * 所有的配置都可以在命令行上进行指定，多个配置用空格分开：--配置项=值
    * java -jar springbootlearn-0.0.1-SNAPSHOT.jar --server.port=8087 --server.context-path=/abc
2. 来自java:comp/env的JNDI属性
3. Java系统属性(System.getProperties())
4. 操作系统环境变量
5. RandomValuePropertySource配置的random.*属性值
6. **jar包外部的application-{profile}.properties或application.yml(带spring.profile)配置文件**
    * 由jar包外向jar包内进行寻找，优先加载带profile，再来加载不带profile
7. **jar包内部的application-{profile}.properties或application.yml(带spring.profile)配置文件**
8. **jar包外部的application.properties或application.yml(不带spring.profile)配置文件**
9. **jar包内部的application.properties或application.yml(不带spring.profile)配置文件**
10. @Configuration注解类上的@PropertySource
11. 通过SpringApplication.setDefaultProperties指定的默认属性

### 自动配置原理

* [配置文件能配置的属性参照](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#common-application-properties)

1. SpringBoot启动的时候加载主配置类SpringBootLearnApplication，开启了自动配置功能**@EnableAutoConfiguration**
2. @EnableAutoConfiguration作用：将类路径下 META-INF/spring.factories 里面配置的所有EnableAutoConfiguration的值加入到了容器中
    * 利用AutoConfigurationImportSelector给容器中导入一些组件
    * 可以查看selectImports()方法的内容(或者其中的getAutoConfigurationEntry方法)
    * 获取候选的配置：List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
    * SpringFactoriesLoader.loadFactoryNames()扫描所有jar包类路径下META-INF/spring.factories 把扫描到的这些文件的内容包装成properties对象
    * 从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后把他们添加在容器中

```properties
# Auto Configure 节选
# 每一个这样的 xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；用他们来做自动配置
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
...
```

3. 每一个自动配置类进行自动配置功能
4. 以**HttpEncodingAutoConfiguration(Http编码自动配置)**为例解释自动配置原理

```java
// @Configuration表示这是一个配置类，可以给容器中添加组件
@Configuration(proxyBeanMethods = false)
// @EnableConfigurationProperties 将配置文件中对应的值和ServerProperties绑定起来，并把ServerProperties加入到ioc容器中
@EnableConfigurationProperties({ServerProperties.class})
// Spring底层@Conditional注解(Spring注解版)，根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效    
// 判断当前应用是否是web应用，如果是，当前配置类生效
@ConditionalOnWebApplication(type = Type.SERVLET)
// 判断当前项目有没有这个类CharacterEncodingFilter(SpringMVC中进行乱码解决的过滤器)
@ConditionalOnClass({CharacterEncodingFilter.class})
// 判断配置文件中是否存在某个配置spring.http.encoding.enabled；如果不存在，判断也是成立的
@ConditionalOnProperty(prefix = "server.servlet.encoding", value = {"enabled"}, matchIfMissing = true)
public class HttpEncodingAutoConfiguration {
    // 和SpringBoot的配置文件映射了
    private final Encoding properties;

    // 只有一个有参构造器的情况下，参数的值就会从容器中拿
    public HttpEncodingAutoConfiguration(ServerProperties properties) {
        this.properties = properties.getServlet().getEncoding();
    }

    /**
     * 给容器中添加一个组件，这个组件的某些值需要从properties中获取
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.web.servlet.server.Encoding.Type.RESPONSE));
        return filter;
    }
}
```

5. 所有在配置文件中能配置的属性都是在xxxxProperties类中封装者‘；配置文件能配置什么就可以参照某个功能对应的这个属性类

```java
//从配置文件中获取指定的值和bean的属性进行绑定
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties {
}
```

6. 总结
    * **SpringBoot启动会加载大量的自动配置类**
    * **需要的功能有没有SpringBoot默认写好的自动配置类**
    * **这个自动配置类中到底配置了哪些组件(只要是用的组件有，就不需要再来配置了)**
    * **给容器中自动配置类添加组件的时候，会从properties类中获取某些属性，就可以在配置文件中指定这些属性的值**
    * xxxxAutoConfiguration：自动配置类，给容器中添加组件
    * xxxxProperties：封装配置文件中相关属性
7. @Conditional派生注解(Spring注解版原生的@Conditional作用)
    * 作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置里面的所有内容才生效
    * 通过在配置文件中**启用debug=true属性**，来让控制台打印自动配置报告，就可以知道哪些自动配置类生效

| @Conditional扩展注解                | 作用（判断是否满足当前指定条件）               |
| ------------------------------- | ------------------------------ |
| @ConditionalOnJava              | 系统的java版本是否符合要求                |
| @ConditionalOnBean              | 容器中存在指定Bean；                   |
| @ConditionalOnMissingBean       | 容器中不存在指定Bean；                  |
| @ConditionalOnExpression        | 满足SpEL表达式指定                    |
| @ConditionalOnClass             | 系统中有指定的类                       |
| @ConditionalOnMissingClass      | 系统中没有指定的类                      |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者这个Bean是首选Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否有指定的值                |
| @ConditionalOnResource          | 类路径下是否存在指定资源文件                 |
| @ConditionalOnWebApplication    | 当前是web环境                       |
| @ConditionalOnNotWebApplication | 当前不是web环境                      |
| @ConditionalOnJndi              | JNDI存在指定项                      |

```
# 节选一部分
============================
CONDITIONS EVALUATION REPORT
============================
Positive matches:
-----------------

   AopAutoConfiguration matched:
      - @ConditionalOnProperty (spring.aop.auto=true) matched (OnPropertyCondition)

   AopAutoConfiguration.ClassProxyingConfiguration matched:
      - @ConditionalOnMissingClass did not find unwanted class 'org.aspectj.weaver.Advice' (OnClassCondition)
      - @ConditionalOnProperty (spring.aop.proxy-target-class=true) matched (OnPropertyCondition)
      
   省略........   

Negative matches:
-----------------

   ActiveMQAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)

   AopAutoConfiguration.AspectJAutoProxyingConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required class 'org.aspectj.weaver.Advice' (OnClassCondition)
         
   省略........      
```


