#

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