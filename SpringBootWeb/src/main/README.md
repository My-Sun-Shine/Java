## 项目

### 引入资源

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

### 国际化

1. 建立配置文件i18n/login，其中默认文件login.properties，其它语言文件login_zh_CN.properties、login_en_US.properties

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

4. SpringBoot自动配置好了管理国际化资源文件的组件，在MessageSourceAutoConfiguration配置类中

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

### 登陆

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

### 提取公共页

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

### SpringBoot默认的错误处理机制

* 针对浏览器，会自动返回一个默认的错误页面；因为浏览器的请求头中包含text/html
* 针对其它客户端，会默认返回一个json数据

#### 原理

* ErrorMvcAutoConfiguration：框架默认的错误处理的自动化配置，为容器添加一下组件
* DefaultErrorAttributes：添加错误信息
* BasicErrorController：处理默认的/error请求，针对不同的情况执行不同的方法
* ErrorPageCustomizer：系统出现错误以后来到error请求进行处理
* DefaultErrorViewResolver：响应页面

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

### 定制错误响应

#### 定制响应错误的页面

* 有模板引擎的情况下：将错误页面命名为 错误状态码.html 放在模板引擎文件夹里面的error文件夹下，发生此状态码的错误就会来到对应的页面
    * 可以使用4xx和5xx作为错误页面的文件名来匹配这种类型的所有错误，精确优先（优先寻找精确的状态 码.html）
* 没有模板引擎（模板引擎找不到这个错误页面），静态资源文件夹下找error/xxx.html
* 以上都没有错误页面，就是默认来到SpringBoot默认的错误提示页面error视图
* 页面能获取的信息DefaultErrorAttributes：
    * timestamp：时间戳
    * status：状态码
    * error：错误提示
    * exception：异常对象
    * message：异常消息

#### 定制响应错误的JSON数据

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





































