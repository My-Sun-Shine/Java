#

## 搭建基本环境

* pom.xml引入cache，mysql，mybatis，redis相关依赖
* 执行springboot_cache.sql，建立数据库表department和employee
* 新建bean包，新建实体类Department.java和Employee.java
* 在application.properties中配置mysql数据源信息
* 新建mapper包，使用注解版MyBatis，新建EmployeeMapper和DepartmentMapper
* 在主程序类CacheApplication上使用@MapperScan(value = "com.springboot.cache.mapper")注解，指定需要扫描的mapper接口所在的包
* 新建service包，新建DeptService和EmployeeService
* 新建controller包，新建DeptController和EmployeeController