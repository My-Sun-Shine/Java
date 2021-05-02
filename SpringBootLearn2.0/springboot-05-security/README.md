# Spring Boot与安全

1. 引入SpringSecurity

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

2. 引入thymeleaf

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

3. 编写SpringSecurity的配置类

```java

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
}
```

4. 控制请求的访问权限：protected void configure(HttpSecurity http)
5. 定义认证规则：protected void configure(AuthenticationManagerBuilder auth)
6. 开启自动配置的登陆功能：http.formLogin()
7. 注销：http.logout().logoutSuccessUrl("/")
8. 记住我：http.rememberMe().rememberMeParameter("remember")
9. 在前端页面中使用，权限判断

```xml
<!--引入依赖-->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity4</artifactId>
    <version>3.0.2.RELEASE</version>
</dependency>
```

```html
<!DOCTYPE html>
<!--引入模板权限模块-->
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
<!--使用hasRole判断是否有该权限-->
<div sec:authorize="hasRole('VIP1')">
</div>
<div sec:authorize="hasRole('VIP2')">
</div>
<div sec:authorize="hasRole('VIP3')">
</div>
</html>
```