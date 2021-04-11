# SpringBoot

## 引入资源

* com.springbootweb.dao文件夹，com.springbootweb.entity文件夹
* static文件夹中添加css、js、img文件夹，注意：必须使用asserts嵌套一层，不知原因
* templates文件夹添加html文件
* 新建配置类MyMvcConfig01，configurer01方法设置首页
* 从webjar中下载bootstrap，然后将html中的路径替换为th:href、th:src

```xml

<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>4.0.0</version>
</dependency>
```

```properties
# 链接前缀
server.servlet.context-path=/crud
```

## 国际化

1. 建立配置文件**i18n/login**，其中默认文件**login.properties**，其它语言文件login_zh_CN.properties、login_en_US.properties

```properties
# 注意配置文件中的键值对的键
btn=登陆~
password=密码~
remember=记住我~
tip=请登陆~
username=用户名~
```

2. application.properties设置spring.messages.basename属性

```properties
# 国际化，application.properties
spring.messages.basename=i18n.login
```

3. 使用ResourceBundleMessageSource管理国际化资源文件

```java
/**
 * 先调用MessageSource接口中的getMessage()方法(在AbstractMessageSource抽象类实现)，进而调用resolveCodeWithoutArguments()
 */
public class ResourceBundleMessageSource {
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
    }
}
```

4. SpringBoot自动配置好了管理国际化资源文件的组件，在**MessageSourceAutoConfiguration**配置类中

```java
//注解判断ResourceBundleCondition类，代码已经简化
@Conditional({MessageSourceAutoConfiguration.ResourceBundleCondition.class})
public class MessageSourceAutoConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.messages")
    public MessageSourceProperties messageSourceProperties() {
        return new MessageSourceProperties();
    }

    //返回MessageSource类，添加到容器里面
    @Bean
    public MessageSource messageSource(MessageSourceProperties properties) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();//注册ResourceBundleMessageSource管理国际化资源文件
        messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(properties.getBasename())));
        return messageSource;
    }

    protected static class ResourceBundleCondition extends SpringBootCondition {
        private static ConcurrentReferenceHashMap<String, ConditionOutcome> cache = new ConcurrentReferenceHashMap();

        //由于注解@Conditional({MessageSourceAutoConfiguration.ResourceBundleCondition.class})
        //SpringBootCondition类的matches()方法中调用ConditionOutcome outcome = this.getMatchOutcome(context, metadata);
        public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String basename = context.getEnvironment().getProperty("spring.messages.basename", "messages");
            ConditionOutcome outcome = (ConditionOutcome) cache.get(basename);
            outcome = this.getMatchOutcomeForBasename(context, basename);
            cache.put(basename, outcome);
            return outcome;
        }

        private ConditionOutcome getMatchOutcomeForBasename(ConditionContext context, String basename) {
            Builder message = ConditionMessage.forCondition("ResourceBundle", new Object[0]);
            String[] var4 = StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(basename));
            String name = var4[var6];
            Resource[] var8 = this.getResources(context.getClassLoader(), name);
        }

        private Resource[] getResources(ClassLoader classLoader, String name) {
            String target = name.replace('.', '/');//所有配置为i18n.login和i18n/login是一样的
        }
    }
}

//配置类使用的属性类
public class MessageSourceProperties {
    private String basename = "messages";
    private Charset encoding;
}
```

5. 修改index.html页面，注意#{password}一定和国际化配置文件中相同，若国际化配置文件是login.password，则页面上修改为${login.password}
6. 根据请求信息切换不同区域

```java
//默认的就是根据请求头带来的区域信息获取Locale进行国际化
public static class EnableWebMvcConfiguration {
    @Bean
    @ConditionalOnMissingBean(name = {"localeResolver"})
    public LocaleResolver localeResolver() {
    }
}

//自定义实现接口LocaleResolver，点击链接切换国际化
public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if (!StringUtils.isEmpty(l)) {
            String[] split = l.split("_");
            locale = new Locale(split[0], split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
```

7. 将实现的接口添加到容器中

```java

@Configuration
public class MyMvcConfig01 implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
```

8. Index.html修改语言切换链接

```html
<a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
<a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
```

## 登陆

1. 禁用缓存

```properties
# 禁用缓存
spring.thymeleaf.cache=false
```

2. 修改Index页面

```html
<!--th:if判断提示是否存在，存在则显示-->
<form class="form-signin" action="dashboard.html" th:action="@{/user/login}" method="post">
    <p style="color: red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
</form>
```

3. 新建控制器

```java

@Controller
@RequestMapping("/user")
public class UserController {
    @PostMapping("/login")
    public String login(String username, String password, Map<String, Object> map, HttpSession session) {
        if (StringUtils.isNotEmpty(username) && StringUtils.equals("123456", password)) {
            //登陆成功，防止表单重复提交，可以重定向到主页
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            //登陆失败
            map.put("msg", "用户名密码错误");
            return "index";
        }
    }
}
```

4. 自定义拦截器

```java
public class LoginHandlerInterceptor implements HandlerInterceptor {
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");
        if (user == null) {
            //未登陆，返回登陆页面
            request.setAttribute("msg", "没有权限请先登陆");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        } else {
            return true;//已登陆，放行请求
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
```

5. 添加重定向路径映射，已经注册拦截器

```java

@Configuration
public class MyMvcConfig01 implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer configurer01() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //添加自定义的拦截器，并且添加拦截路径，以及排除不需要拦截的路径
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html", "/", "/user/login");
            }
        };
        return webMvcConfigurer;
    }
}
```

6. 在dashboard.html页面显示登陆账号

```html
<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="http://getbootstrap.com/docs/4.0/examples/dashboard/#"
   th:text="${session.loginUser}">Company name</a>
```

## 提取公共页

```html
<!--1、抽取公共片段-->
<div th:fragment="copy">
    &copy; 2011 The Good Thymes Virtual Grocery
</div>

<!--2、引入公共片段-->
<div th:insert="~{footer :: copy}"></div>
<!--
~{templatename::selector}：模板名::选择器
~{templatename::fragmentname}:模板名::片段名
-->

<!--
3、默认效果：
insert的公共片段在div标签中
如果使用th:insert等属性进行引入，可以不用写~{}：
行内写法可以加上：[[~{}]];[(~{})]
-->
```

* 三种引入公共片段的th属性：
    * **th:insert**：将公共片段整个插入到声明引入的元素中
    * **th:replace**：将声明引入的元素替换为公共片段
    * **th:include**：将被引入的片段的内容包含进这个标签中

```html

<footer th:fragment="copy">
    &copy; 2011 The Good Thymes Virtual Grocery
</footer>

<!--引入方式-->
<div th:insert="footer :: copy"></div>
<div th:replace="footer :: copy"></div>
<div th:include="footer :: copy"></div>

<!--效果-->
<div>
    <footer>
        &copy; 2011 The Good Thymes Virtual Grocery
    </footer>
</div>

<footer>
    &copy; 2011 The Good Thymes Virtual Grocery
</footer>

<div>
    &copy; 2011 The Good Thymes Virtual Grocery
</div>
```

* 引入片段的时候传入参数

```html
<!--bar.html-->
<li class="nav-item">
    <a class="nav-link active"
       th:class="${activeUri=='main.html'?'nav-link active':'nav-link'}"
       href="#" th:href="@{/main.html}">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
             class="feather feather-home">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
            <polyline points="9 22 9 12 15 12 15 22"></polyline>
        </svg>
        Dashboard <span class="sr-only">(current)</span>
    </a>
</li>

<!--引入侧边栏;传入参数，list.html-->
<div th:replace="commons/bar::#sidebar(activeUri='emps')"></div>
```

## SpringBoot默认的错误处理机制

* 针对浏览器，会自动返回一个默认的错误页面；因为浏览器的请求头中包含text/html
* 针对其它客户端，会默认返回一个json数据

### 原理

* **ErrorMvcAutoConfiguration：** 框架默认的错误处理的自动化配置，为容器添加一下组件
* **DefaultErrorAttributes：** 添加错误信息
* **BasicErrorController：** 处理默认的/error请求，针对不同的情况执行不同的方法
* **ErrorPageCustomizer：** 系统出现错误以后来到error请求进行处理
* **DefaultErrorViewResolver：** 响应页面

```java
//一旦系统出现4xx或者5xx之类的错误，ErrorPageCustomizer就会生效，就会来到/error请求，被BasicErrorController处理，具体如何响应页面由DefaultErrorViewResolver处理
public class DefaultErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = this.getErrorAttributes(webRequest, options.isIncluded(Include.STACK_TRACE));
        if (Boolean.TRUE.equals(this.includeException)) {
            options = options.including(new Include[]{Include.EXCEPTION});
        }
        if (!options.isIncluded(Include.EXCEPTION)) {
            errorAttributes.remove("exception");
        }
        if (!options.isIncluded(Include.STACK_TRACE)) {
            errorAttributes.remove("trace");
        }
        if (!options.isIncluded(Include.MESSAGE) && errorAttributes.get("message") != null) {
            errorAttributes.put("message", "");
        }
        if (!options.isIncluded(Include.BINDING_ERRORS)) {
            errorAttributes.remove("errors");
        }
        return errorAttributes;
    }
}

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class BasicErrorController extends AbstractErrorController {
    @RequestMapping(produces = {"text/html"})
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        //获取状态码  
        HttpStatus status = this.getStatus(request);
        //getErrorAttributes获取错误信息
        Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        //resolveErrorView中获取所有ErrorViewResolve得到ModelAndView
        ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
        //如何没有找到任何视图，就去找error视图，即在ErrorMvcAutoConfiguration中@Bean(name = {"error"})
        return modelAndView != null ? modelAndView : new ModelAndView("templates/error", model);
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity(status);
        } else {
            Map<String, Object> body = this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.ALL));
            return new ResponseEntity(body, status);
        }
    }
}

/**
 * 根据ErrorPageCustomizer类中的registerErrorPages方法中获取路径this.properties.getError().getPath()
 * 系统出现错误以后来到error请求进行处理；（web.xml注册的错误页面规则）
 */
public class ErrorProperties {
    @Value("${error.path:/error}")
    private String path = "/templates/error";
}

public class DefaultErrorViewResolver implements ErrorViewResolver, Ordered {
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = this.resolve(String.valueOf(status.value()), model);
        if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
            modelAndView = this.resolve((String) SERIES_VIEWS.get(status.series()), model);
        }
        return modelAndView;
    }

    private ModelAndView resolve(String viewName, Map<String, Object> model) {
        // 默认SpringBoot可以去找到一个页面？error/404
        String errorViewName = "templates/error/" + viewName;
        //模板引擎可以解析这个页面地址就使用模板引擎解析
        TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(errorViewName, this.applicationContext);
        //模板引擎可用的情况下返回到errorViewName指定的视图地址，若不可用，就在静态资源文件夹中找errorViewName对应的页面 error/404.html
        return provider != null ? new ModelAndView(errorViewName, model) : this.resolveResource(errorViewName, model);
    }

    private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
        String[] var3 = this.resources.getStaticLocations();
        int var4 = var3.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            String location = var3[var5];
            try {
                Resource resource = this.applicationContext.getResource(location);
                resource = resource.createRelative(viewName + ".html");
                if (resource.exists()) {
                    return new ModelAndView(new DefaultErrorViewResolver.HtmlResourceView(resource), model);
                }
            } catch (Exception var8) {
            }
        }
        return null;
    }
}
```

## 定制错误响应

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
















