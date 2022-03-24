# Sentinel的Feign支持

* Sentinel适配了Feign组件，如果想使用，除了引入spring-cloud-starter-alibaba-sentinel的依赖
* 配置文件打开Sentinel对Feign的支持：feign.sentinel.enabled=true

```xml

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

# Sentinel持久化Nacos

### pom.xml引入依赖

```xml

<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

### Sentinel结合Nacos持久化配置

* sentinel持久化支持的类型，详情见：com.alibaba.cloud.sentinel.datasource.config.DataSourcePropertiesConfiguration

```java
public class DataSourcePropertiesConfiguration {
    private FileDataSourceProperties file;
    private NacosDataSourceProperties nacos;
    private ZookeeperDataSourceProperties zk;
    private ApolloDataSourceProperties apollo;
    private RedisDataSourceProperties redis;
    private ConsulDataSourceProperties consul;
}
```

### yml配置

* server-addr：nacos的地址
* dataId：配置列表的dataId
* groupId：配置列表的groupId
* data-type：表示Converter类型，Spring Cloud Alibaba Sentinel默认提供两个内置的值，分别是json(默认)和xml
    * 如果不想使用内置的json或xml这两种Converter，可以填写custom表示自定义Converter，然后再配置converter-class配置项，该配置项需要写类的全路径名(比如
      spring.cloud.sentinel.datasource.ds1.file.converter-class=com.alibaba.cloud.examples.JsonFlowRuleListConverter)
* rule-type：表示该数据源中的规则属于哪种类型的规则：authority(授权规则)、degrade(降级规则)、flow(流控规则)、param(热点规则)、system(系统规则)五种规则持久化到Nacos中;
  另外还增加网关的两个(api分组，限流)

```yaml
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848 # 指定nacos控制台地址,配置注册ip:端口，注意即使是80端口也不可能省略
    sentinel:
      transport:
        dashboard: 192.168.110.206:8099 # sentinel服务端地址
      eager: true # 取消延迟加载
      # sentiel 持久化到nacos
      datasource:
        ds1:
          nacos:
            server-addr: ${spring.cloud.nacos.discovery.server-addr}
            dataId: sentinel-flow
            groupId: DEFAULT_GROUP
            data-type: json
            rule-type: flow # 限流
        ds2:
          nacos:
            server-addr: ${spring.cloud.nacos.discovery.server-addr}
            dataId: sentinel-degrade
            groupId: DEFAULT_GROUP
            data-type: json
            rule-type: degrade # 熔断
```

### Nacos增加配置文件

* 限流规则：data-id名称为：sentinel-flow，group名称为：DEFAULT_GROUP，配置格式：选择JSON
    * resource：资源名称
    * limitApp：来源应用
    * grade：阈值类型，0表示线程数，1表示QPS
    * count：单机阈值
    * strategy：流控模式，0表示直接，1表示关联，2表示链路
    * controlBehavior：流控效果，0表示快速失败。1表示Warm Up，2表示排队等待
    * clusterMode：是否集群

```json
[
  {
    "resource": "/getAccount",
    "limitApp": "default",
    "grade": 1,
    "count": 1,
    "strategy": 0,
    "controlBehavior": 0,
    "clusterMode": false
  }
]
```

* 熔断降级规则：data-id名称为：sentinel-degrade，group名称为：DEFAULT_GROUP，配置格式：选择JSON
    * resource：资源名，即规则的作用对象
    * grade：熔断策略，支持慢调用比例/异常比例/异常数策略，默认慢调用比例
    * count：慢调用比例模式下为慢调用临界RT（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值
    * timeWindow：熔断时长，单位为s
    * minRequestAmount：熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入），默认5
    * statIntervalMs：统计时长（单位为ms），如 60*1000 代表分钟级（1.8.0 引入），默认1000 ms
    * slowRatioThreshold：慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入）

```json
[
  {
    "resource": "/getAccount",
    "grade": 0,
    "count": 1,
    "timeWindow": 20,
    "minRequestAmount": 2
  }
]
```

* 热点规则
    * resource：资源名
    * count：限流阈值
    * grade：限流模式，QPS模式
    * durationInSec：统计窗口时间长度（单位为秒），1.6.0 版本开始支持，默认1s
    * controlBehavior：流控效果（支持快速失败和匀速排队模式），1.6.0 版本开始支持，默认快速失败
    * maxQueueingTimeMs：最大排队等待时长（仅在匀速排队模式生效），1.6.0 版本开始支持，默认0ms
    * paramIdx：热点参数的索引，必填，对应 SphU.entry(xxx, args) 中的参数索引位置
    * paramFlowItemList：参数例外项，可以针对指定的参数值单独设置限流阈值，不受前面count阈值的限制。仅支持基本类型和字符串类型
    * clusterMode：是否是集群参数流控规则，默认false -*clusterConfig：集群流控相关配置
* 系统规则
    * highestSystemLoad：对应页面的Load(系统的load1作为启发指标，进行自适应系统保护。当系统load1超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护(BBR阶段),
      系统容量由系统的maxQps*minRt估算得出。设定参考值一般是CPU cores*2.5)
    * avgRt：对应页面的RT(当单台机器上所有入口流量的平均RT达到阈值即触发系统保护，单位是毫秒)
    * maxThread：对应页面的线程数(当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护)
    * qps：对应入口QPS(当单台机器上所有入口流量的QPS达到阈值即触发系统保护)
    * highestCpuUsage：对应CUP使用率(当系统CPU使用率超过阈值即触发系统保护（取值范围0.0-1.0），比较灵敏)

```json
[
  {
    "id": 12,
    "app": "SPRING-CLOUD-SENTINEL",
    "ip": "192.168.170.1",
    "port": 8720,
    "highestSystemLoad": -1.0,
    "avgRt": -1,
    "maxThread": -1,
    "qps": 1.0,
    "highestCpuUsage": -1.0,
    "gmtCreate": null,
    "gmtModified": null
  }
]
```