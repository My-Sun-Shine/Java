## SpringBoot对静态资源的映射规则

```java
/**
 * 可以设置和静态资源有关的参数，缓存时间等
 * 不过在2.x版本中已经过时
 */
@Deprecated
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties extends Resources {
}
```

```java
/**
 * WebMvc自动配置类
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean({WebMvcConfigurationSupport.class})
@AutoConfigureOrder(-2147483638)
@AutoConfigureAfter({DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class, ValidationAutoConfiguration.class})
public class WebMvcAutoConfiguration {
    /**
     * 静态资源文件的映射
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        if (!this.resourceProperties.isAddMappings()) {
            logger.debug("Default resource handling disabled");
        } else {
            ServletContext servletContext = this.getServletContext();
            this.addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
            // getStaticPathPattern()就是获取的this.staticPathPattern = "/**";
            this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
                //getStaticLocations()是获取的下面这数组的数据
                //private static final String[] CLASSPATH_RESOURCE_LOCATIONS = new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};
                registration.addResourceLocations(this.resourceProperties.getStaticLocations());
                if (servletContext != null) {
                    registration.addResourceLocations(new Resource[]{new ServletContextResource(servletContext, "/")});
                }
            });
        }
    }

    /**
     * 欢迎页映射
     */
    @Bean
    public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext, FormattingConversionService mvcConversionService, ResourceUrlProvider mvcResourceUrlProvider) {
        WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext), applicationContext, this.getWelcomePage(), this.mvcProperties.getStaticPathPattern());
        welcomePageHandlerMapping.setInterceptors(this.getInterceptors(mvcConversionService, mvcResourceUrlProvider));
        welcomePageHandlerMapping.setCorsConfigurations(this.getCorsConfigurations());
        return welcomePageHandlerMapping;
    }

    /**
     * 配置图标，不过在新版中已经删除，下面两次提交进行删除
     * https://github.com/spring-projects/spring-boot/commit/9ac6485768a6561fd8d690a5247a7ed6e1578416#diff-3cabd1182c676e97c82925c60c115cddb993212758d785860998b50f8c02d2b7
     * https://github.com/spring-projects/spring-boot/commit/05e089601e9838a15cd1d083be76aea276b9df78#diff-3cabd1182c676e97c82925c60c115cddb993212758d785860998b50f8c02d2b7
     */
    @Configuration
    @ConditionalOnProperty(value = "spring.mvc.favicon.enabled", matchIfMissing = true)
    public static class FaviconConfiguration {
        private final ResourceProperties resourceProperties;

        public FaviconConfiguration(ResourceProperties resourceProperties) {
            this.resourceProperties = resourceProperties;
        }

        @Bean
        public SimpleUrlHandlerMapping faviconHandlerMapping() {
            SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
            mapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
            mapping.setUrlMap(Collections.singletonMap("**/favicon.ico", faviconRequestHandler()));
            return mapping;
        }

        @Bean
        public ResourceHttpRequestHandler faviconRequestHandler() {
            ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
            requestHandler.setLocations(this.resourceProperties.getFaviconLocations());
            return requestHandler;
        }
    }
}
```

* 所有的静态资源都是去classpath:/META-INF/resources/webjars/找资源
    * webjars：以jar包的方式引入静态资源，[参考网站](http://www.webjars.org/)

```xml
<!--
根据网站http://www.webjars.org上的描述，引入jquery-webjar
可以查看Maven中的org.webjars:jquery:3.6.0包
访问http://localhost:8080/webjars/jquery/3.6.0/jquery.js，就可以访问
-->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.6.0</version>
</dependency>
```

* WebMvcAutoConfiguration中addResourceHandlers对静态资源设置查询路径
    * Index.html、favicon.ico以及其它静态资源需要放置下面四个路径中

```
# "/**" 访问当前项目的任何资源，都去（静态资源的文件夹）找映射
"classpath:/META-INF/resources/", 
"classpath:/resources/",
"classpath:/static/", 
"classpath:/public/" 
```