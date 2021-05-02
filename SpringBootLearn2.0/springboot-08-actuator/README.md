# Spring Boot与监控管理

* 通过引入spring-boot-starter-actuator，可以使用Spring Boot为我们提供的准生产环境下的应用监控和管理功能
* 可以通过HTTP，JMX，SSH协议来进行操作，自动得到审计、健康及指标信息等

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

* 监控和管理端点

| 端点名   | 描述 |
| ----         | ---- |
| autoconfig   | 所有自动配置信息 |
| auditevents  | 审计事件         |  
| beans        | 所有Bean的信息   |
| configprops  | 所有配置属性 |
| dump         | 线程状态信息 |
| env          | 当前环境信息 |
| health       | 应用健康状况 |
| info         | 当前应用信息 |
| metrics      | 应用的各项指标    |
| mappings     | 应用@RequestMapping映射路径 |
| shutdown     | 关闭当前应用（默认关闭）     | 
| trace        | 追踪信息（最新的http请求）   |

### SpringBoot1.x中配置文件

```properties
# 暴露所有端点
management.security.enabled=false
# 对单个端点的暴露
# endpoints.metrics.enabled=false
# 开启远程应用关闭功能
endpoints.shutdown.enabled=true
# redis
spring.redis.host=127.0.0.1
# 设置info端点信息
info.app.id=hello
info.app.version=1.0.0
# 定制端点信息，修改端点访问
#endpoints.beans.id=mybean
#endpoints.beans.path=/bean
#endpoints.beans.enabled=false
#endpoints.dump.path=/du
# 开启远程应用关闭功能
#endpoints.enabled=false
#endpoints.beans.enabled=true
# 定制端点访问根路径
management.context-path=/manage
# 修改端点的端口
management.port=8181
# 关闭Http端点
#management.port=-1
```

### 新版SpringBoot2.x

* 访问路径

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
    },
    "beans": {
      "href": "http://localhost:8080/actuator/beans",
      "templated": false
    },
    "caches-cache": {
      "href": "http://localhost:8080/actuator/caches/{cache}",
      "templated": true
    },
    "caches": {
      "href": "http://localhost:8080/actuator/caches",
      "templated": false
    },
    "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:8080/actuator/health/{*path}",
      "templated": true
    },
    "info": {
      "href": "http://localhost:8080/actuator/info",
      "templated": false
    },
    "conditions": {
      "href": "http://localhost:8080/actuator/conditions",
      "templated": false
    },
    "configprops": {
      "href": "http://localhost:8080/actuator/configprops",
      "templated": false
    },
    "env": {
      "href": "http://localhost:8080/actuator/env",
      "templated": false
    },
    "env-toMatch": {
      "href": "http://localhost:8080/actuator/env/{toMatch}",
      "templated": true
    },
    "loggers": {
      "href": "http://localhost:8080/actuator/loggers",
      "templated": false
    },
    "loggers-name": {
      "href": "http://localhost:8080/actuator/loggers/{name}",
      "templated": true
    },
    "heapdump": {
      "href": "http://localhost:8080/actuator/heapdump",
      "templated": false
    },
    "threaddump": {
      "href": "http://localhost:8080/actuator/threaddump",
      "templated": false
    },
    "metrics-requiredMetricName": {
      "href": "http://localhost:8080/actuator/metrics/{requiredMetricName}",
      "templated": true
    },
    "metrics": {
      "href": "http://localhost:8080/actuator/metrics",
      "templated": false
    },
    "scheduledtasks": {
      "href": "http://localhost:8080/actuator/scheduledtasks",
      "templated": false
    },
    "mappings": {
      "href": "http://localhost:8080/actuator/mappings",
      "templated": false
    }
  }
}
```

```properties
# SpringBoot2.x，暴露所有的端点
management.endpoints.web.exposure.include=*
# 启用端点 env
# management.endpoint.env.enabled=true
# 暴露端点 env 配置多个,隔开
# management.endpoints.web.exposure.include=env
# http://localhost:8181/manage/actuator
management.server.port=8181
management.server.base-path=/manage
# 展示health的详细信息
management.endpoint.health.show-details=always
```

* 自定义health

```java
/**
 * 与1.0相比，多出来getHealth()方法
 */
@Component
public class MyAppHealthIndicator implements HealthIndicator {
    @Override
    public Health getHealth(boolean includeDetails) {
        return health();
    }

    @Override
    public Health health() {
        //自定义的检查方法
        //Health.up().build()代表健康
        return Health.down().withDetail("msg", "服务异常").build();
    }
}

```






