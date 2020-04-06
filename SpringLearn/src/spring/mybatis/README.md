# Spring整合MyBatis

##Spring整合MyBatis的思路
* 使用Spring的IOC核心技术，把MyBatis的对象交给Spring统一创建和管理
* 交给Spring的对象
    * 数据源DataSource，访问数据库
    * SqlSessionFactory对象
    * MyBatis动态代理，根据DAO接口，创建接口实现类对象，也就是DAO对象

## 步骤：
* 使用MySQL，创建一个新库springdb. 创建表student(id int auto increment, name varchar(50), age int)
* 新建java project
* 导入jar：
    * 1）Spring的核心：spring-beans.jar,spring-core.jar,spring-context.jar,spring-expression.jar
    * 2）Spring的aop的jar:spring-aop.jar(Spring整合MyBatis，事务是自动提交的，Spring框架中事务是使用环绕通知实现的)
    * 3）Spring有关数据库的jar：spring-jdbc.jar, spring-tx.jar
    * 4）MyBatis的核心：mybatis-3.4.5.jar
    * 5）MyBatis和Spring整合的jar： mybatis-spring-1.3.1.jar, 目的是能够在Spring项目中创建MyBatis对象
    * 6）MySQL的驱动
    * 7）数据源，使用连接池，druid-1.1.9.jar
    * 8）日志，单元测试
* 定义实体类，Student
* 定义Dao接口：StudentDao
* 定义Sql映射文件：StudentDao.xml
* 定义MyBatis的主配置文件
* 定义Service接口和他的实现类
* 定义Spring配置文件，重要的
    * 1）声明Druid的数据源
    * 2）声明SqlSessionFactoryBean,使用这个对象创建SqlSessionFactory
    * 3）声明MyBatis的扫描器对象， 目的是创建Dao接口的实现类对象
    * 4）声明Service对象， 把3）步骤中的Dao注入给Service属性
* 定义测试类，从Spring容器中获取Service对象。 调用他的方法，访问Dao
  
  
  