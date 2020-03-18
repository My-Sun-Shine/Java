# MyBatis主配置文件mybatis-config.xml
1. environments：连接数据库环境设置(加载驱动，连接信息)
2. properties：加载数据库的属性文件，在四个参数上直接使用${}传递参数
3. settings：设置程序与数据库关联的基本信息，这种设置主要是为了提高程序访问数据库的效率，比如一级缓存，二级缓存，加载策略等等等等
4. typeAliases：为mapper映射文件中的domain，起别名（因为使用全限定名比较长比较负责），两种方式指定起别名和批量定义别名
5. mappers：用来在核心配置文件中注册和加载映射文件，三种注册方式，其中class和package形式需要使用MyBatis的dao层动态代理

## mybatis-config.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <properties resource="db.properties"/>
    <!--
    <settings>
        <setting name="" value=""/>
    </settings>
    -->
    <!--
    <typeAliases>
        <!--<typeAlias type="org.mybatis.example.Blog" alias="blog"/>-->
        <package name="org.mybatis.example"/>
    </typeAliases>
    -->
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${jdbc.drive}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/mybatis/example/BlogMapper.xml"/>
        <!--<mapper class="org.mybatis.example.BlogMapper"/>-->
        <!--<package name="org.mybatis.example" />-->
    </mappers>
</configuration>
```
## db.properties
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/bjpow
jdbc.username=root
jdbc.password=123456
```



