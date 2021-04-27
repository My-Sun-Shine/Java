# Spring Boot与缓存

## 搭建基本环境

* pom.xml引入cache，mysql，mybatis相关依赖
* 执行springboot_cache.sql，建立数据库表department和employee
* 新建bean包，新建实体类Department.java和Employee.java
* 在application.properties中配置mysql数据源信息
* 新建mapper包，使用注解版MyBatis，新建EmployeeMapper和DepartmentMapper
* 在主程序类CacheApplication上使用@MapperScan(value = "com.springboot.cache.mapper")注解，指定需要扫描的mapper接口所在的包
* 新建service包，新建DeptService和EmployeeService
* 新建controller包，新建DeptController和EmployeeController

## 使用Cache

* 开启基于注解的缓存：在主程序类上设置@EnableCaching注解

### 基本概念

* Cache：缓存接口，定义缓存操作，实现有：RedisCache、EhCacheCache、ConcurrentMapCache等
* CacheManager：缓存管理器，管理各种缓存（Cache）组件
* @Cacheable：主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
* @CacheEvict：清空缓存
* @CachePut：保证方法被调用，又希望结果被缓存。
* @EnableCaching 开启基于注解的缓存
* keyGenerator：缓存数据时key生成策略
* serialize：缓存数据时value序列化策略

### 自动配置类CacheAutoConfiguration

* 默认生效的配置类：SimpleCacheConfiguration
* SimpleCacheConfiguration给容器中注册了一个CacheManager(ConcurrentMapCacheManager)
* ConcurrentMapCacheManager可以获取和创建ConcurrentMapCache类型的缓存组件，它的作用将数据保存在ConcurrentMap中
* 新建MyCacheConfig类，自定义keyGenerator，并在方法上指定使用该keyGenerator

```java

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CacheManager.class)
@ConditionalOnBean(CacheAspectSupport.class)
@ConditionalOnMissingBean(value = CacheManager.class, name = "cacheResolver")
@EnableConfigurationProperties(CacheProperties.class)
@AutoConfigureAfter({CouchbaseDataAutoConfiguration.class, HazelcastAutoConfiguration.class, HibernateJpaAutoConfiguration.class, RedisAutoConfiguration.class})
@Import({CacheConfigurationImportSelector.class, CacheManagerEntityManagerFactoryDependsOnPostProcessor.class})
public class CacheAutoConfiguration {

    static class CacheConfigurationImportSelector implements ImportSelector {
        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            CacheType[] types = CacheType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = CacheConfigurations.getConfigurationClass(types[i]);
            }
            return imports;
            // 返回下面的自动配置类
            // org.springframework.boot.autoconfigure.cache.GenericCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.JCacheCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.EhCacheCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.HazelcastCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.InfinispanCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.CouchbaseCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.CaffeineCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.GuavaCacheConfiguration
            // org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration【默认】
            // org.springframework.boot.autoconfigure.cache.NoOpCacheConfiguration
        }
    }
}
```

## 整合redis作为缓存

* pom.xml中引入redis的依赖，使用的配置就会变成RedisCacheConfiguration
* RedisCacheConfiguration会为容器添加RedisCacheManager组件
* RedisCacheManager会创建RedisCache作为缓存组件，可以通过RedisCache来操作缓存
* RedisCacheConfiguration还会自动注入RedisAutoConfiguration配置组件
* RedisAutoConfiguration配置会注入StringRedisTemplate和RedisTemplate<Object, Object>的组件来操作redis

#### 自定义redis

* 使用RedisTemplate存储对象时，框架默认使用jdk的序列化机制，使用自定义RedisTemplate，改成Jackson2JsonRedisSerializer
* 使用自定义RedisCacheManager，开启默认会将CacheName作为key的前缀

