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
    <p style="color: #ff0000" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
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
