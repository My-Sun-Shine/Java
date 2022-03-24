# nacos读取配置中心配置

### yml中的不同配置

* 使用默认配置，规则是**
  ${spring.cloud.nacos.config.prefix}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}**

```yaml
spring:
  application:
    name: cloud-config
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        enabled: true
        server-addr: 127.0.0.1:8848
        prefix: cloud-config
        file-extension: yml
  profiles:
    active: dev
```

* 使用**shared-dataids**属性和**refreshable-dataids**属性，自定义配置文件格式（已废弃）

```yaml
spring:
  application:
    name: cloud-config
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        enabled: true
        server-addr: 127.0.0.1:8848
        prefix: cloud-config-dev
        file-extension: yml
        shared-dataids: ${spring.cloud.nacos.config.prefix}.${spring.cloud.nacos.config.file-extension}
        refreshable-dataids: ${spring.cloud.nacos.config.prefix}.${spring.cloud.nacos.config.file-extension}
```

* 通过**spring.cloud.nacos.config.shared-configs[n].data-id**支持多个共享Data Id的配置

```yaml
spring:
  application:
    name: cloud-config
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        enabled: true
        server-addr: 127.0.0.1:8848
        shared-configs[0]:
          data-id: cloud-config-dev.yml
          group: DEFAULT_GROUP
          refresh: true  
```

* 通过**spring.cloud.nacos.config.extension-configs[n].data-id**的方式支持多个扩展Data Id的配置

```yaml
spring:
  application:
    name: cloud-config
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
      config:
        enabled: true
        server-addr: 127.0.0.1:8848
        extension-configs[0]:
          data-id: cloud-config-dev.yml
          group: DEFAULT_GROUP
          refresh: true  
```

### 不同方式配置加载优先级

* A: 通过spring.cloud.nacos.config.shared-configs[n].data-id支持多个共享Data Id的配置
* B: 通过spring.cloud.nacos.config.extension-configs[n].data-id的方式支持多个扩展Data Id的配置
* C: 通过内部相关规则(spring.cloud.nacos.config.prefix、spring.cloud.nacos.config.file-extension、spring.cloud.nacos.config.group)
  自动生成相关的Data Id配置
* 当三种方式共同使用时，他们的一个优先级关系是:A < B < C；先读取C,再读取B，最后读取A