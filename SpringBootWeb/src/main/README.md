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
