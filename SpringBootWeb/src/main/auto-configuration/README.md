### [Spring MVC auto-configuration](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-auto-configuration)

* Spring Boot provides auto-configuration for Spring MVC that works well with most applications.
* Spring Boot会自动配置好SpringMVC
* The auto-configuration adds the following features on top of Spring’s defaults:
* 以下是SpringBoot对SpringMVC的默认配置
* Inclusion of **ContentNegotiatingViewResolver** and **BeanNameViewResolver** beans.
    * 自动配置ViewResolver（视图解析器：根据方法的返回值得到视图对象View，视图对象决定如何渲染，是转发还是重定向）
    * ContentNegotiatingViewResolver：把所有的视图解析器组合起来
    * 可以自定义一个视图解析器，SpringBoot会自动将其组合进来
* Support for serving static resources, including support for WebJars (see below).
    * 静态资源文件夹路径,webjars
* Static *index.html* support.
    * 静态首页访问
* Custom *Favicon* support (see below).
    * favicon.ico，高版本已删除该配置，默认
* Automatic registration of **Converter**, **GenericConverter**, **Formatter** beans.
    * Converter：类型转换器
    * Formatter：时间格式化
* Support for **HttpMessageConverters** (see below).
    * HttpMessageConverters：SpringMVC用来转换Http请求和响应
* Automatic registration of **MessageCodesResolver** (see below).
* 定义错误代码生成规则
* Automatic use of a ConfigurableWebBindingInitializer bean (see below).
* If you want to keep Spring Boot MVC features, and you just want to add additional **MVC configuration** (interceptors,
  formatters, view controllers etc.) you can add your own **@Configuration** class of type **WebMvcConfigurerAdapter**,
  but without **@EnableWebMvc**. If you wish to provide custom instances of **RequestMappingHandlerMapping**,
  **RequestMappingHandlerAdapter** or **ExceptionHandlerExceptionResolver** you can declare a **
  WebMvcRegistrationsAdapter**
  instance providing such components. If you want to take complete control of Spring MVC, you can add your own
  **@Configuration** annotated with **@EnableWebMvc**.
* 自定义配置类时，如果在配置类上加 **@EnableWebMvc** 注解，就会导致所有的SpringMVC的自动配置都失效了
*
@EnableWebMvc注解的核心是导入DelegatingWebMvcConfiguration组件，但是如果DelegatingWebMvcConfiguration组件存在就会导致WebMvcAutoConfiguration失效

```java

@Import({DelegatingWebMvcConfiguration.class})
public @interface EnableWebMvc {
}

@Configuration(proxyBeanMethods = false)
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
}

@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
//容器中没有这个组件的时候，这个自动配置类才生效
@ConditionalOnMissingBean({WebMvcConfigurationSupport.class})
@AutoConfigureOrder(-2147483638)
@AutoConfigureAfter({DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class})
public class WebMvcAutoConfiguration {
}
```