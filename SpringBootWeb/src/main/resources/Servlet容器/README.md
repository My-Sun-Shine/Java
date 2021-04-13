# 定制错误响应

### 定制响应错误的页面

* **有模板引擎的情况下**：将错误页面命名为 错误状态码.html 放在模板引擎文件夹里面的error文件夹下，发生此状态码的错误就会来到对应的页面
    * 可以使用4xx和5xx作为错误页面的文件名来匹配这种类型的所有错误，精确优先（优先寻找精确的状态 码.html）
* **没有模板引擎**（模板引擎找不到这个错误页面），静态资源文件夹下找error/xxx.html
* 以上都没有错误页面，就是默认来到SpringBoot默认的错误提示页面error视图
* 页面能获取的信息DefaultErrorAttributes：
    * timestamp：时间戳
    * status：状态码
    * error：错误提示
    * exception：异常对象
    * message：异常消息

### 定制响应错误的JSON数据

* 自定义异常处理&返回定制json数据；

```java
//没有自适应效果，无论是浏览器访问还是其它客户端访问都是返回json数据
@ControllerAdvice
public class MyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "user.notExist");
        map.put("message", e.getMessage());
        return map;
    }
}
```

```java
//转发到/error进行自适应响应效果处理
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
        // Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notExist");
        map.put("message", e.getMessage());
        //将额外数据放到request域中
        request.setAttribute("ext", map);
        //转发到/error
        return "forward:/error";
    }
}
```

* 将自定义的定制错误数据携带出去
    * 系统出现错误以后，会来到/error请求，会被BasicErrorController处理，响应出去可以获取的数据是由getErrorAttributes()
      得到的（是AbstractErrorController（ErrorController）规定的方法）
    * 完全来编写一个ErrorController的实现类【或者是编写AbstractErrorController的子类】，放在容器中
    * 页面上能用的数据，或者是json返回能用的数据都是通过errorAttributes.getErrorAttributes()得到

```java
//给容器中加入自定义的ErrorAttributes
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        map.put("company", "奥特之星");
        //异常处理器携带的数据
        Map<String, Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", 0);
        map.put("ext", ext);
        return map;
    }
}
```

```json
{
  "timestamp": "2021-04-10T15:26:21.444+00:00",
  "status": 455,
  "error": "Http Status 455",
  "exception": "com.springbootweb.exception.UserNotExistException",
  "message": "用户不存在",
  "path": "/hello/m1",
  "company": "奥特之星",
  "ext": {
    "code": "user.notExist",
    "message": "用户不存在"
  }
}
```

## 配置嵌入式Servlet容器

### 定制和修改Servlet容器的相关配置：修改ServerProperties

* 通过配置文件修改

```properties
# 通用的Servlet容器设置
server.port=8081
# Tomcat的设置
server.tomcat.uri-encoding=utf-8
```

* 编写一个配置类，注入组件WebServerFactoryCustomizer自定义配置

```java
/**
 * ServletWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, Ordered
 * 需要实现WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>接口，并注入到容器中
 */
@Configuration
public class MyServerConfig {
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            factory.setPort(8083);
        };
    }
}
```

### 注册Servlet三大组件【Servlet、Filter、Listener】

```java
/**
 * 新建三个类
 * public class MyServlet extends HttpServlet
 * public class MyFilter implements Filter
 * public class MyListener implements ServletContextListener
 */
@Configuration
public class MyServerConfig {
    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/hello", "/myServlet"));
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener() {
        ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
        return registrationBean;
    }
}
```

* SpringBoot中DispatcherServletAutoConfiguration类会自动的注册SpringMVC的前端控制器DispatcherServlet

```java
public class DispatcherServletAutoConfiguration {
    protected static class DispatcherServletRegistrationConfiguration {
        @ConditionalOnBean(value = {DispatcherServlet.class}, name = {"dispatcherServlet"})
        public DispatcherServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet, WebMvcProperties webMvcProperties, ObjectProvider<MultipartConfigElement> multipartConfig) {
            DispatcherServletRegistrationBean registration = new DispatcherServletRegistrationBean(dispatcherServlet, webMvcProperties.getServlet().getPath());
            // 默认拦截： /  所有请求，包括静态资源，但是不拦截jsp请求；/* 会拦截jsp
            // 可以通过server.servletPath来修改SpringMVC前端控制器默认拦截的请求路径
            registration.setName("dispatcherServlet");
            registration.setLoadOnStartup(webMvcProperties.getServlet().getLoadOnStartup());
            multipartConfig.ifAvailable(registration::setMultipartConfig);
            return registration;
        }
    }
}
```

### 替换为其他嵌入式Servlet容器

* 引入web模块默认就是使用嵌入式的Tomcat作为Servlet容器
* **TomcatServletWebServerFactory、JettyServletWebServerFactory、UndertowServletWebServerFactory
  继承AbstractServletWebServerFactory**

```xml
<!-- 引入web模块，并排除tomcat -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <artifactId>spring-boot-starter-tomcat</artifactId>
                <groupId>org.springframework.boot</groupId>
            </exclusion>
        </exclusions>
    </dependency>

    <!--引入其他的Servlet容器-->
    <dependency>
        <artifactId>spring-boot-starter-jetty</artifactId>
        <groupId>org.springframework.boot</groupId>
    </dependency>
</dependencies>
```

```xml
<!-- 引入web模块，并排除tomcat -->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <artifactId>spring-boot-starter-tomcat</artifactId>
                <groupId>org.springframework.boot</groupId>
            </exclusion>
        </exclusions>
    </dependency>

    <!--引入其他的Servlet容器-->
    <dependency>
        <artifactId>spring-boot-starter-undertow</artifactId>
        <groupId>org.springframework.boot</groupId>
    </dependency>
</dependencies>
```

### 嵌入式Servlet容器自动配置原理

* SpringBoot中ServletWebServerFactoryAutoConfiguration根据依赖的情况为容器添加相应的ServletWebServerFactory(
  TomcatServletWebServerFactory)
* 容器中某个组件要创建对象就会惊动后置处理器**WebServerFactoryCustomizerBeanPostProcessor**
* 后置处理器从容器中获取所有的**WebServerFactoryCustomizer**，调用定制器的customize

```java
/**
 * 嵌入式的Servlet容器自动配置
 * BeanPostProcessorsRegistrar：为容器注入组件
 */
@Import({ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class, EmbeddedTomcat.class, EmbeddedJetty.class, EmbeddedUndertow.class})
public class ServletWebServerFactoryAutoConfiguration {
}

@Configuration(proxyBeanMethods = false)
class ServletWebServerFactoryConfiguration {
    static class EmbeddedJetty {
    }

    static class EmbeddedUndertow {
    }

    @ConditionalOnClass({Servlet.class, Tomcat.class, UpgradeProtocol.class})//判断当前是否引入的Tomcat依赖
    //ServletWebServerFactory：嵌入式的Servlet容器工厂
    @ConditionalOnMissingBean(value = {ServletWebServerFactory.class}, search = SearchStrategy.CURRENT)
    static class EmbeddedTomcat {
        @Bean
        TomcatServletWebServerFactory tomcatServletWebServerFactory(ObjectProvider<TomcatConnectorCustomizer> connectorCustomizers, ObjectProvider<TomcatContextCustomizer> contextCustomizers, ObjectProvider<TomcatProtocolHandlerCustomizer<?>> protocolHandlerCustomizers) {
            //TomcatServletWebServerFactory、JettyServletWebServerFactory、UndertowServletWebServerFactory继承AbstractServletWebServerFactory
            //AbstractServletWebServerFactory继承ServletWebServerFactory
            TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
            return factory;
        }
    }
}

/**
 * 嵌入式的Servlet容器工厂
 * public class TomcatWebServer implements WebServer
 * public class UndertowWebServer implements WebServer
 * public class NettyWebServer implements WebServer
 * public class JettyWebServer implements WebServer
 */
@FunctionalInterface
public interface ServletWebServerFactory {
    WebServer getWebServer(ServletContextInitializer... initializers);
}

public class TomcatServletWebServerFactory extends AbstractServletWebServerFactory implements ConfigurableTomcatWebServerFactory, ResourceLoaderAware {
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        //创建一个Tomcat
        Tomcat tomcat = new Tomcat();
        //配置Tomcat
        File baseDir = this.baseDirectory != null ? this.baseDirectory : this.createTempDir("tomcat");
        tomcat.setBaseDir(baseDir.getAbsolutePath());
        Connector connector = new Connector(this.protocol);
        connector.setThrowOnFailure(true);
        tomcat.getService().addConnector(connector);
        this.customizeConnector(connector);
        tomcat.setConnector(connector);
        tomcat.getHost().setAutoDeploy(false);
        this.configureEngine(tomcat.getEngine());
        Iterator var5 = this.additionalTomcatConnectors.iterator();
        while (var5.hasNext()) {
            Connector additionalConnector = (Connector) var5.next();
            tomcat.getService().addConnector(additionalConnector);
        }
        this.prepareContext(tomcat.getHost(), initializers);
        //将配置好的Tomcat传入进去，返回一个EmbeddedServletContainer；并且启动Tomcat服务器
        return this.getTomcatWebServer(tomcat);
    }

    protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
        return new TomcatWebServer(tomcat, this.getPort() >= 0, this.getShutdown());
    }
}

/**
 * 注入webServerFactoryCustomizerBeanPostProcessor
 * 后置处理器：bean初始化前后（创建完对象，还没赋值赋值）执行初始化工作
 */
public static class BeanPostProcessorsRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (this.beanFactory != null) {
            this.registerSyntheticBeanIfMissing(registry, "webServerFactoryCustomizerBeanPostProcessor", WebServerFactoryCustomizerBeanPostProcessor.class, WebServerFactoryCustomizerBeanPostProcessor::new);
            this.registerSyntheticBeanIfMissing(registry, "errorPageRegistrarBeanPostProcessor", ErrorPageRegistrarBeanPostProcessor.class, ErrorPageRegistrarBeanPostProcessor::new);
        }
    }

}

/**
 * WebServerFactoryCustomizer：自定义定制器修改Servlet容器的配置
 */
public class WebServerFactoryCustomizerBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //初始化之前，如果当前初始化的是WebServerFactory
        if (bean instanceof WebServerFactory) {
            this.postProcessBeforeInitialization((WebServerFactory) bean);
        }
        return bean;
    }

    //获取所有的定制器，调用每一个定制器的customize方法来给Servlet容器进行属性赋值
    private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
        ((Callbacks) LambdaSafe.callbacks(WebServerFactoryCustomizer.class, this.getCustomizers(), webServerFactory, new Object[0]).withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)).invoke((customizer) -> {
            customizer.customize(webServerFactory);
        });
    }

    private Collection<WebServerFactoryCustomizer<?>> getCustomizers() {
        if (this.customizers == null) {
            //从容器中获取所有WebServerFactoryCustomizer类型的主键
            this.customizers = new ArrayList(this.getWebServerFactoryCustomizerBeans());
            this.customizers.sort(AnnotationAwareOrderComparator.INSTANCE);
            this.customizers = Collections.unmodifiableList(this.customizers);
        }
        return this.customizers;
    }

    private Collection<WebServerFactoryCustomizer<?>> getWebServerFactoryCustomizerBeans() {
        return this.beanFactory.getBeansOfType(WebServerFactoryCustomizer.class, false, false).values();
    }
}
```

### 嵌入式Servlet容器启动原理：IOC容器启动创建嵌入式的Servlet容器

1. SpringBoot应用启动运行**SpringApplication.run()**方法
2. **refreshContext(context)**：SpringBoot刷新IOC容器【创建IOC容器对象，并初始化容器，创建容器中的每一个组件

```java
// 执行前面的createApplicationContext()方法，通过webApplicationType判断
public interface ApplicationContextFactory {
    ApplicationContextFactory DEFAULT = (webApplicationType) -> {
        try {
            switch (webApplicationType) {
                case SERVLET:
                    return new AnnotationConfigServletWebServerApplicationContext();
                case REACTIVE:
                    return new AnnotationConfigReactiveWebServerApplicationContext();
                default:
                    return new AnnotationConfigApplicationContext();
            }
        } catch (Exception var2) {
            throw new IllegalStateException("Unable create a default ApplicationContext instance, you may need a custom ApplicationContextFactory", var2);
        }
    };
}
```

3. **this.refresh((ApplicationContext)context)**：刷新刚才创建好的ioc容器

```java
public abstract class AbstractApplicationContext {
    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");
            this.prepareRefresh();
            ConfigurableListableBeanFactory beanFactory = this.obtainFreshBeanFactory();
            this.prepareBeanFactory(beanFactory);
            try {
                this.postProcessBeanFactory(beanFactory);
                StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
                this.invokeBeanFactoryPostProcessors(beanFactory);
                this.registerBeanPostProcessors(beanFactory);
                beanPostProcess.end();
                this.initMessageSource();
                this.initApplicationEventMulticaster();
                this.onRefresh();
                this.registerListeners();
                this.finishBeanFactoryInitialization(beanFactory);
                this.finishRefresh();
            } catch (BeansException var10) {
                if (this.logger.isWarnEnabled()) {
                    this.logger.warn("Exception encountered during context initialization - cancelling refresh attempt: " + var10);
                }
                this.destroyBeans();
                this.cancelRefresh(var10);
                throw var10;
            } finally {
                this.resetCommonCaches();
                contextRefresh.end();
            }
        }
    }
}

```

4. **this.onRefresh()**：web的ioc容器重写了onRefresh方法
5. **this.createWebServer()**：WebIoc容器会创建嵌入式的Servlet容器
6. **ServletWebServerFactory factory = this.getWebServerFactory()**：获取嵌入式的Servlet容器工厂

```java
public class ServletWebServerApplicationContext {
    protected ServletWebServerFactory getWebServerFactory() {
        //tomcatServletWebServerFactory
        String[] beanNames = this.getBeanFactory().getBeanNamesForType(ServletWebServerFactory.class);
        //从IOC容器中获取tomcatServletWebServerFactory组件，创建对象会导致后置处理器，来获取所有的定制器WebServerFactoryCustomizer来定制该Servlet容器的相关配置
        return (ServletWebServerFactory) this.getBeanFactory().getBean(beanNames[0], ServletWebServerFactory.class);
    }
}
```

7. **this.webServer = factory.getWebServer(new ServletContextInitializer[]{this.getSelfInitializer()})**
   ：使用容器工厂获取嵌入式的Servlet容器
8. **factory.getWebServer()**：嵌入式的Servlet容器创建对象并启动Servlet容器
9. 先启动嵌入式的Servlet容器，再将ioc容器中剩下没有创建出的对象获取出来AbstractApplicationContext中的**this.finishBeanFactoryInitialization(
   beanFactory)**

### 使用外置的Servlet容器

* 嵌入式Servlet容器：应用打成可执行的jar
    * 优点：简单、便携
    * 缺点：默认不支持JSP、优化定制比较复杂（使用定制器【ServerProperties、自定义WebServerFactoryCustomizer】
* 外置的Servlet容器：外面安装Tomcat---应用war包的方式打包

#### 步骤

1. 必须创建一个war项目（利用idea创建好目录结构）
2. 将嵌入式的Tomcat指定为provided

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```

3. 必须编写一个**SpringBootServletInitializer**的子类，并调用configure方法

```java
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //传入SpringBoot应用的主程序SpringBootWebJspApplication
        return application.sources(SpringBootWebJspApplication.class);
    }
}
```

4. 使用Tomcat启动服务器就可以使用

#### 原理

* jar包：执行SpringBoot主类的main方法，启动ioc容器，创建嵌入式的Servlet容器
* war包：启动服务器，**服务器启动SpringBoot应用【SpringBootServletInitializer】**，启动ioc容器
* servlet3.0（Spring注解版）规则：
    * 服务器启动（web应用启动）会创建当前web应用里面每一个jar包里面ServletContainerInitializer实例
    * ServletContainerInitializer的实现放在jar包的META-INF/services文件夹下，有一个名为javax.servlet.
      ServletContainerInitializer的文件，内容就是ServletContainerInitializer的实现类的全类名
    * 还可以使用@HandlesTypes，在应用启动的时候加载其它类；

#### 流程：启动Servlet容器，再启动SpringBoot应用

1. 启动Tomcat
2. **org.springframework.web.SpringServletContainerInitializer**
3. SpringServletContainerInitializer将@HandlesTypes(WebApplicationInitializer.class)
   标注的所有这个类型的类都传入到onStartup方法的Set<Class<?>>；并为这些WebApplicationInitializer类型的类创建实例
4. **每一个WebApplicationInitializer都调用自己的onStartup，在被创建对象的时候调用**
5. SpringBootServletInitializer实例执行onStartup()的时候会执行**createRootApplicationContext()创建容器**
6. Spring的应用就启动并且创建IOC容器

```java
public abstract class SpringBootServletInitializer implements WebApplicationInitializer {
    protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
        //创建SpringApplicationBuilder
        SpringApplicationBuilder builder = this.createSpringApplicationBuilder();
        builder.main(this.getClass());
        //省略。。。
        builder.initializers(new ApplicationContextInitializer[]{new ServletContextApplicationContextInitializer(servletContext)});

        //调用configure方法，子类重写了这个方法，将SpringBoot的主程序类传入了进来
        builder = this.configure(builder);
        builder.listeners(new ApplicationListener[]{new SpringBootServletInitializer.WebEnvironmentPropertySourceInitializer(servletContext)});
        //使用builder创建一个Spring应用
        SpringApplication application = builder.build();
        //省略。。。
        application.setRegisterShutdownHook(false);
        //启动Spring应用
        return this.run(application);
    }
}
```
















