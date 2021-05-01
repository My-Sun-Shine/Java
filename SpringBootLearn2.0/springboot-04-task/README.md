# Spring Boot与任务

### 异步任务

* 在主程序中使用@EnableAsync注解开启异步注解功能
* 在需要使用异步操作的方法上加@Async注解

### 定时任务

* Spring为我们提供了异步执行任务调度的方式，提供TaskExecutor、TaskScheduler接口
* 在主程序中使用@EnableScheduling开启基于注解的定时任务
* 在需要使用定时操作的方法上加@Scheduled注解，需要些cron表达式

|  字段  | 允许值 | 允许的特殊字符 |
| ----  | ---- | ----    |
|  秒   | 0-59 | , - * / |
|  分   | 0-59 | , - * / |
|  小时 | 0-23 | , - * / |
|  日期 | 1-31 | , - * ? / L W C |
|  月份 | 1-12 | , - * / |
|  星期 | 0-7或SUN-SAT 0,7是SUN | , - * ? / L C # |

| 特殊字符 | 代表含义 |
| ---- | ---- |
|  , | 枚举 | 
|  - | 区间 | 
|  * | 任意 | 
|  / | 步长 | 
|  ? | 日/星期冲突匹配 | 
|  L | 最后   | 
|  W | 工作日 | 
|  C | 和calendar联系后计算过的值 |
| #  | 星期，4#2，第2个星期四     |

### 邮件任务

* pom.xml添加配置

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

* application.properties设置邮箱配置

```properties
spring.mail.username=1399237176@qq.com
# 登录第三方客户端时，密码框请输入“授权码”进行验证
spring.mail.password=
spring.mail.host=smtp.qq.com
spring.mail.properties.mail.smtp.ssl.enable=true
```
