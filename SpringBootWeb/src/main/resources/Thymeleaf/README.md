## Thymeleaf

### 1、引入thymeleaf；

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

```xml
<!--切换thymeleaf版本-->
<properties>
    <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
    <!-- 布局功能的支持程序  thymeleaf3主程序  layout2以上版本 -->
    <!-- thymeleaf2   layout1-->
    <thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
</properties>
```

### Thymeleaf配置类

```java
/**
 * 配置类
 * 只要把HTML页面放在classpath:/templates/，thymeleaf就能自动渲染；
 */
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");
    private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
}
```

### Thymeleaf的使用

```html
<!DOCTYPE html>
<!--导入thymeleaf的名称空间-->
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>成功！</h1>
<!--th:text 将div里面的文本内容设置为 -->
<div th:text="${hello}">这是显示欢迎信息</div>
</body>
</html>
```

```java
/**
 * 访问http://localhost:8080/thymeleaf/m01，访问上述页面
 */
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
    @GetMapping("/m01")
    public String m01(Map<String, Object> map) {
        map.put("hello", "Hello World!");
        return "thymeleaf01";
    }
}
```

### 语法规则

* [官方网站](https://www.thymeleaf.org/)
* [PDF语法手册](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.pdf)
* th：任意html属性：来替换原生属性的值
    * th:text:改变当前元素里面的文本内容

|优先级|描述|举例|
|----|----|----|
1|Fragment inclusion：片段包括，例如jsp:include|th:insert、th:replace|
2|Fragment iteration：遍历，c:forEach|th:each|
3|Conditional evaluation：条件判断，c:if|th:if、th:unless、th:switch、th:case|
4 |Local variable definition：声明便利，c:set|th:object、th:with|
5 |General attribute modification：任意属性的修改，支持prepend、append|th:attr、th:attrprepend、th:attrappend|
6 |Specific attribute modification：修改指定属性的默认值|th:value、th:href、th:src、...|
7 |Text (tag body modification) ：修改标签体内容|th:text、th:utext|
8 |Fragment specification：声明片段|th:fragment|
9 |Fragment removal|th:remove|

#### Simple expressions：表达式语法

* Variable Expressions: ${...}：获取OGNL变量值；获取对象的属性、调用方法；使用内置的基本对象

```
内置的基本对象：
#ctx : the context object.
#vars: the context variables.
#locale : the context locale.
#request : (only in Web Contexts) the HttpServletRequest object.
#response : (only in Web Contexts) the HttpServletResponse object.
#session : (only in Web Contexts) the HttpSession object.
#servletContext : (only in Web Contexts) the ServletContext object.
内置的一些工具对象：
#execInfo : information about the template being processed.
#messages : methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
#uris : methods for escaping parts of URLs/URIs
#conversions : methods for executing the configured conversion service (if any).
#dates : methods for java.util.Date objects: formatting, component extraction, etc.
#calendars : analogous to #dates , but for java.util.Calendar objects.
#numbers : methods for formatting numeric objects.
#strings : methods for String objects: contains, startsWith, prepending/appending, etc.
#objects : methods for objects in general.
#bools : methods for boolean evaluation.
#arrays : methods for arrays.
#lists : methods for lists.
#sets : methods for sets.
#maps : methods for maps.
#aggregates : methods for creating aggregates on arrays or collections.
#ids : methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
```

* Selection Variable Expressions: *{...}：选择表达式：和${}在功能上是一样

```html
<!--补充：配合 th:object="${session.user}：-->
<div th:object="${session.user}">
    <p>Name:<span th:text="*{firstName}">Sebastian</span>.</p>
    <p>Surname:<span th:text="*{lastName}">Pepper</span>.</p>
    <p>Nationality:<span th:text="*{nationality}">Saturn</span>.</p>
</div>
```

* Message Expressions: #{...}：获取国际化内容
* Link URL Expressions: @{...}：定义URL
    * @{http://localhost:8080/gtvg/order/details(orderId=${o.id})}等同于http://localhost:8080/gtvg/order/details?orderId=3
* Fragment Expressions: ~{...}：片段引用表达式

```
Literals（字面量）
  Text literals: 'one text' , 'Another one!' ,…
  Number literals: 0 , 34 , 3.0 , 12.3 ,…
  Boolean literals: true , false
  Null literal: null
  Literal tokens: one , sometext , main ,…
Text operations:（文本操作）
  String concatenation: +
  Literal substitutions: |The name is ${name}|
Arithmetic operations:（数学运算）
  Binary operators: + , - , * , / , %
  Minus sign (unary operator): -
Boolean operations:（布尔运算）
  Binary operators: and , or
  Boolean negation (unary operator): ! , not
Comparisons and equality:（比较运算）
  Comparators:> , < , >= , <= ( gt , lt , ge , le )
  Equality operators: == , != ( eq , ne )
Conditional operators:条件运算（三元运算符）
  If-then:(if) ? (then)
  If-then-else:(if) ? (then) : (else)
  Default:(value) ?: (defaultvalue)
Special tokens:
  No-Operation:_ 
```