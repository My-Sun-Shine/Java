# 配置文件

* SpringBoot使用一个全局的配置文件，配置文件名是固定的；
    * application.properties
    * application.yml
* 配置文件的作用：修改SpringBoot自动配置的默认值

# YAML(YAML Ain't Markup Language：不是一种标记语言)语法

### 基本语法

* k: v；特别:和v之间的空格必须有
* 以空格的缩进来控制层级关系，只要是左对齐的一列数据，都是同一个层级的
* 属性和值都是大小写敏感的

```yaml
key:
  value
```

### 值的写法

* 字面量：普通的值(数字、字符串、布尔)，k: v格式直接来写，字符串默认不用加上单引号或者双引号；
    * 双引号("")：转义字符串里面的特殊字符
    * 单引号('')：不会转义字符串里面的特殊字符
* 对象(属性和值)、Map(键值对)

```yaml
friends1:
  lastName: 张三
  age: 20
# 行内写法
friends2: { lastName:zhangsan,age:18 }
```

* 数组(List、Set)

```yaml
pets1:
  - cat
  - dog
  - pig
# 行内写法
pets2: [ cat,dog,pig ]
```

### 配置文件值注入

* 设置pom.xml

```xml
<!--导入配置文件处理器，配置文件进行绑定就会有提示-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

* 配置文件内容

```yaml
person:
  lastName: hello
  age: 18
  boss: false
  birth: 2017/12/12
  maps: { k1: v1,k2: 12 }
  lists:
    - lisi
    - zhaoliu
  dog:
    name: 小狗
    age: 12
```

* 新建类

```java
/**
 * @Component 声明组件
 * @ConfigurationProperties 表示本类中的所有属性和配置文件中相关的配置进行绑定
 * prefix = "person"：配置文件中person下面的所有属性进行一一映射
 */
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;
    private Map<String, Object> maps;
    private List<Object> lists;
    private Dog dog;
}

class Dog {
    private String name;
    private Integer age;
}
```

* 设置测试方法

```java

@SpringBootTest
class SpringBootLearnApplicationTests {
    @Autowired
    Person person;

    @Test
    void TestPerson() {
        System.out.printf(person.toString());
    }
}
```

### @Value获取值和@ConfigurationProperties获取值比较

* @ConfigurationProperties：person配置，Person类
    * 支持批量注入配置文件中的属性
    * 支持松散语法绑定，无论yml文件是last_name、last-name、lastName都可以匹配到
    * 支持复杂类型的封装
    * 支持JSR303数据校验
    * 不支持#{}语法
* @Value：person01配置，Person01类
    * 需要一个一个的指定
    * 不支持松散语法绑定，yml文件是last_name，使用lastName无法匹配到，但是可以使用last_name、last-name匹配到
    * 不支持复杂类型的封装
    * 不支持JSR303数据校验
    * 支持#{}语法，类似于#{10*2}，会自动算出
* 如果只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value
* 如果专门编写了一个javaBean来和配置文件进行映射，就直接使用@ConfigurationProperties
* 新加依赖，新增Person01类，新增person01配置，新增注解

```xml
<!--后台数据字段校验，JSR303数据校验-->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.0.18.Final</version>
</dependency>
```

```java
/**
 * @Validated 校验
 * @Email 邮箱校验
 */
@Component
@ConfigurationProperties(prefix = "person")
@Validated
public class Person {
    @Email
    private String lastName;
}
```

### @PropertySource：加载指定的配置文件

1. 注释掉application.yml和application.properties中person涉及的配置信息
2. 新建person.properties
3. 在Person类上标记@PropertySource(value = {"classpath:person.properties"})

```java
/**
 * @PropertySource 加载指定的一个或者多个配置文件，新建yml格式配置不起作用
 */
@Component
@ConfigurationProperties(prefix = "person")
@PropertySource(value = {"classpath:person.properties"})
@Validated
public class Person {
}
```

### @ImportResource和@Configuration、@Bean：导入组件

* @ImportResource：导入Spring的配置文件，并让配置文件里面的内容生效
* Spring Boot里面没有Spring的配置文件，编写的配置文件，无法自动识别，如果想让Spring的配置文件生效，则需要将@ImportResource标注在一个配置类上

1. 新建测试类Test01和Test02
2. 新建Spring配置文件beans.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="Test01" class="com.springbootlearn.bean.Test01"></bean>
</beans>
```

3. 在启动类SpringBootLearnApplication上标注@ImportResource(locations = {"classpath:beans.xml"})导入组件
4. 新建配置类AppConfig01，来使用@Configuration和@Bean导入组件

```java
/**
 * @Configuration 指明当前类是一个配置类，就是来替代之前的Spring配置文件，在配置文件中用<bean><bean/>标签添加组件
 */
@Configuration
public class AppConfig01 {
    /**
     * 使用@Bean给容器中添加组件 将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名
     * @return
     */
    @Bean
    public Test02 test02() {
        System.out.println("配置类@Bean为容器添加组件Test02");
        return new Test02();
    }
}
```

4. 测试是否组件导入成功

```java

@SpringBootTest
class SpringBootLearnApplicationTests {

    @Autowired
    ApplicationContext ioc;

    @Test
    void TestImportResource() {
        System.out.println(ioc.containsBean("Test01"));//true
    }

    @Test
    void TestConfigurationBean() {
        System.out.println(ioc.containsBean("test02"));//true
    }
}
```