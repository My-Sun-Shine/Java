## SSM整合开发 SSM:SpringMVC + Spring + MyBatis(IBatis)
* SSM整合的思路：把对象交给容器来管理，视图层的对象交给SpringMVC容器，service和dao对象交给Spring容器
* 视图层的对象交给SpringMVC，定义在SpringMVC配置文件：dispatcherServlet.xml
   * 处理器对象（Controller）,使用注解@Controller
   * 声明组件扫描器，指定@Controller注解所在的包名
   * 视图解析器，指定前缀和后缀。
   * 声明注解驱动 < mvc:annotation-driven />
* service和dao对象交给spring容器，定义在Spring的配置文件：applicationContext.xml
   * 使用@Service创建业务层对象
   * 声明组件扫描器，指定@Service注解所在的包名  
   * 声明数据源DataSource,访问数据库
   * 声明SqlSessionFactoryBean，创建SqlSessionFactory
   * 声明MyBatis的扫描器，创建Dao接口的实现类对象，即Dao对象
   * 其他的工具类对象
### SSM整合中有两个容器SpringMVC , Spring容器
* 用户请求的处理过程：用户发起请求 =====> Controller =====> Service =====> Dao =====> MySQL
* SpringMVC和Spring容器的关系：
   * SpringMVC看做是Spring的子容器，子容器能够访问到父容器，SpringMVC是子容器，Spring是父容器
   * SpringMVC容器中的Controller能够访问父容器中的Service，反过来Service是不能访问Controller的
* SpringMVC和Spring容器关系的建立是通过SpringMVC的一个属性parent完成的，parent属性值是Spring容器对象
```
FrameworkServlet.java中initWebApplicationContext()
cwac.setParent(rootContext);
```
### 步骤：
1. 新建web project
2. 导入jar：
   * spring的相关：spring-beans.jar,spring-core.jar,spring-context.jar,spring-expression.jar,spring-aop.jar,spring-jdbc.jar,spring-tx.jar,spring-web.jar,spring-webmvc.jar 
   * mybatis相关：mybatis-3.4.5.jar mybatis-spring-1.3.1.jar
   * jackson的jar：jackson的三个jar
   * 数据库相关的jar：druid.jar, mysql的驱动
   * 日志：commons-logging.jar
3. 修改web.xml
   * 注册Spring的监听器ContextLoaderListener：目的是创建Spring的容器对象，加载Spring的配置文件（创建service、dao对象）
   * 注册SpringMVC中央调度器DispatcherServlet：目的是创建SpringMVC容器对象，加载SpringMVC配置文件（创建Controller,视图解析器）
   * 注册字符集过滤器，解决post请求乱码的问题
4. 创建包，把实体类、Dao、Service、Controller的包都创建好
5. 编写配置文件
   * SpringMVC配置文件，创建Controller
   * Spring配置文件，创建Service、Dao
   * 数据库属性配置文件
   * mybatis主配置文件
6.  新建实体类，Dao接口和sql映射文件，Service接口和实现类，Controller类
7. 新建jsp，作为视图
8. ssm2相对于ssm，少一个Spring的配置文件applicationContext.xml，将applicationContext.xml的配置信息放在SpringMVC配置文件：dispatcherServlet.xml


