# SpringMVC的注解式应用，步骤：
* 1.新建Web project 
* 2.导入jar
    * 1）Spring的核心jar：spring-beans.jar,spring-core.jar,spring-context.jar, spring-expression.jar
    * 2) Spring Web相关的jar：spring-web.jar, spring-webmvc.jar(SpringMVC框架，模块的jar)
    * 3) 日志：commons-logging.jar, log4j.jar
    * 4) 使用注解的jar：spring-aop.jar
* 3.重点：在web.xml文件中注册SpringMVC的核心对象DispatcherServlet(中央调度器)
    * 1)DispatcherServlet是继承了HttpServlet, 所以它是一个Servlet
    * 2)DispatcherServlet也叫做前端控制器（front controller）
    * 3)DispatcherServlet主要作用：接收用户的请求，把处理结果显示给用户，完成请求的应答
* 4.新建jsp,发起请求
* 5.新建处理器类（Servlet），处理用户的请求
    * 1）在类的上面加入@Controller
    * 2）定义方法，在方法上面加入@RequestMapping(value="请求的uri地址")
* 6.新建结果的jsp
* 7.新建SpringMVC的配置文件，作为SpringMVC容器使用（web.xml）
    * 1）声明组件扫描器，指定@Controller注解所在的包名
    * 2）声明视图解析器
* 8.修改处理器方法，使用逻辑视图名称