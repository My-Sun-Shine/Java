# SpringBoot与数据访问

## JDBC

#### 配置pom.xml和application.yaml

```xml
<!--pom.xml-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

```yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.15.22:3306/jdbc
    driver-class-name: com.mysql.jdbc.Driver
    schema:
      - classpath:department.sql
    initialization-mode: ALWAYS
```

```java
/**
 * SpringBoot 1.x 版本默认使用org.apache.tomcat.jdbc.pool.DataSource
 * SpringBoot 2.x 版本默认使用com.zaxxer.hikari.HikariDataSource
 *
 */
@SpringBootTest
class SpringbootwebApplicationTests {
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        //org.apache.tomcat.jdbc.pool.DataSource
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
```

##### 自动配置原理org.springframework.boot.autoconfigure.jdbc：

* 数据源的相关配置都在DataSourceProperties里面
* 参考DataSourceConfiguration，根据配置创建数据源，可以使用spring.datasource.type指定自定义的数据源类型
* SpringBoot默认可以支持**oracle.ucp.jdbc.PoolDataSource、org.apache.commons.dbcp2.BasicDataSource、com.zaxxer.hikari.
  HikariDataSource、org.apache.tomcat.jdbc.pool.DataSource**
* 自定义数据源类型

```java
/**
 * 自定义数据源类型
 * Generic DataSource configuration.
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(DataSource.class)
@ConditionalOnProperty(name = "spring.datasource.type")
static class Generic {
    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        //使用DataSourceBuilder创建数据源，利用反射创建响应type的数据源，并且绑定相关属性
        return properties.initializeDataSourceBuilder().build();
    }

}
```

* DataSourceInitializerInvoker继承ApplicationListener监听器，最终调用的是DataSourceInitializer
* DataSourceInitializer中的createSchema()进行创建表，initSchema()方法进行插入数据
* 默认只需要将文件命名为：schema.sql、schema-all.sql、data.sql、data-all.sql
* 使用yml配置文件设置spring.datasource.schema属性，设置一个list

```java
class DataSourceInitializerInvoker implements ApplicationListener<DataSourceSchemaCreatedEvent>, InitializingBean {
    public void afterPropertiesSet() {
        DataSourceInitializer initializer = this.getDataSourceInitializer();//获取DataSourceInitializer
        boolean schemaCreated = this.dataSourceInitializer.createSchema();//创建表
        if (schemaCreated) {//如果表创建成功，则去执行数据插入
            this.initialize(initializer);
        }
    }

    public void onApplicationEvent(DataSourceSchemaCreatedEvent event) {
        DataSourceInitializer initializer = this.getDataSourceInitializer();
        initializer.initSchema();//执行数据插入
    }
}

class DataSourceInitializer {
    boolean createSchema() {
        List<Resource> scripts = this.getScripts("spring.datasource.schema", this.properties.getSchema(), "schema");//获取schema文件
        if (!scripts.isEmpty()) {
            if (!this.isEnabled()) {
                return false;
            }
            this.runScripts(scripts, this.properties.getDataUsername(), this.properties.getDataPassword());//执行sql文件中的语句
        }
        return !scripts.isEmpty();
    }

    void initSchema() {
        List<Resource> scripts = this.getScripts("spring.datasource.data", this.properties.getData(), "data");//获取data文件
        if (!scripts.isEmpty()) {
            if (!this.isEnabled()) {
                return;
            }
            this.runScripts(scripts, this.properties.getDataUsername(), this.properties.getDataPassword());//执行sql文件中的语句
        }
    }

    private boolean isEnabled() {
        //判断initialization-mode配置，有三种：NEVER、EMBEDDED、ALWAYS，默认为EMBEDDED，需要设置为ALWAYS才会执行外来数据源的语句
        DataSourceInitializationMode mode = this.properties.getInitializationMode();
        if (mode == DataSourceInitializationMode.NEVER) {
            return false;
        } else {
            //DataSourceInitializationMode默认为EMBEDDED，只有用嵌入式的数据库这里才会返回true，
            return mode != DataSourceInitializationMode.EMBEDDED || this.isEmbedded();
        }
    }

    private List<Resource> getScripts(String propertyName, List<String> resources, String fallback) {
        if (resources != null) {
            //判断是否有配置文件设置文件路径，如果有就根据配置的文件路径获取sql文件
            return this.getResources(propertyName, resources, true);
        } else {
            String platform = this.properties.getPlatform();//默认为all
            List<String> fallbackResources = new ArrayList();
            //fallback参数为data或者schema
            fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
            fallbackResources.add("classpath*:" + fallback + ".sql");
            return this.getResources(propertyName, fallbackResources, false);
        }
    }
}
```

* 操作数据库：自动配置了JdbcTemplate操作数据库HelloController中test01()

## 整合Druid数据源

```xml
<!--引入druid数据源-->
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.8</version>
</dependency>
```

```yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/jdbc
    driver-class-name: com.mysql.jdbc.Driver
    schema:
      - classpath:department.sql
    initialization-mode: ALWAYS
    type: com.alibaba.druid.pool.DruidDataSource
    # 无法解析下面的属性配置，需要自定义配置文件
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    #  配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```

```java
/**
 * 导入druid数据源
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    //配置Druid的监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "123456");//设置账号密码
        initParams.put("allow", "");//默认就是允许所有访问
        initParams.put("deny", "192.168.15.21");//IP黑名单
        bean.setInitParameters(initParams);
        return bean;
    }

    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}

```

## 整合MyBatis

* 配置pom.xml，配置数据源相关属性

```xml
<!--引入mybatis数据源-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>
```

###### 注解版

```java
/**
 * 指定这是一个操作数据库的mapper
 * 新建controller中方法调用mapper中方法
 */
@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department(departmentName) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set departmentName=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}
```

```java
/**
 * 使用MapperScan批量扫描所有的Mapper接口
 */
@MapperScan(value = "com.springbootweb.mapper")
@SpringBootApplication
public class SpringbootwebApplication {
    @Autowired
    private MessageSource messageSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwebApplication.class, args);
    }

}
```

###### 配置文件版

* 在yaml中配置mybatis配置，新建mybatis配置文件mybatis-config.xml
* 新建EmployeeMapper和对应的EmployeeMapper.xml文件
* 编写HelloController中的方法
* [更多使用参照](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

```yaml
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml # 指定全局配置文件的位置
  mapper-locations: classpath:mybatis/mapper/*.xml  # 指定sql映射文件的位置
```

```java
/**
 * 自定义MyBatis的配置规则；给容器中添加一个ConfigurationCustomizer
 */
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {

            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
```

## 整合SpringData JPA

### SpringData简介

### 整合SpringData JPA：ORM（Object Relational Mapping）

1. 添加SpringData JPA依赖

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

2. 编写一个实体类（bean）和数据表进行映射，并且配置好映射关系；

```java
//使用JPA注解配置映射关系
@Entity //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "tbl_user") //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
public class User {
    @Id //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Integer id;

    @Column(name = "last_name", length = 50) //这是和数据表对应的一个列
    private String lastName;
    @Column //省略默认列名就是属性名
    private String email;
}
```

3. 编写一个Dao接口来操作实体类对应的数据表（Repository）

```java
//继承JpaRepository来完成对数据库的操作
public interface UserRepository extends JpaRepository<User, Integer>, QueryByExampleExecutor<User>, CrudRepository<User, Integer> {
}
```

4. 基本的配置JpaProperties

```yaml
spring:
  jpa:
    hibernate:
      # 更新或者创建数据表结构
      ddl-auto: update
    # 控制台显示SQL
    show-sql: true
```

5. 编写HelloController