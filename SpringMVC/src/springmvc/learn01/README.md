### SpringMVC的配置式应用 在SpringMVC配置文件中，使用<bean>声明处理器对象
### 步骤：
1. 新建Web project 
2. 导入jar
    * Spring的核心jar：spring-beans.jar,spring-core.jar,spring-context.jar, spring-expression.jar
    * Spring Web相关的jar：spring-web.jar, spring-webmvc.jar(SpringMVC框架，模块的jar)
    * 日志：commons-logging.jar, log4j.jar
3. 重点：在web.xml文件中注册SpringMVC的核心对象DispatcherServlet(中央调度器)
    * DispatcherServlet是继承了HttpServlet, 所以它是一个Servlet
    * DispatcherServlet也叫做前端控制器（front controller）
    * DispatcherServlet主要作用：接收用户的请求，把处理结果显示给用户，完成请求的应答
4. 新建jsp,发起请求
5. 新建处理器类（Servlet），处理用户的请求
    * 在配置式开发中，处理器类需要实现SpringMVC框架中的接口，使用Controller
    * 在接口的方法中，实现请求的处理
    * 这个处理器类也叫做后端处理器（控制器）（back controller）
6. 新建结果的jsp
7. 新建SpringMVC的配置文件，作为SpringMVC容器使用（web.xml）
    * 声明处理器对象，让它接收某个请求
    * 声明视图解析器
8. 修改处理器方法，使用逻辑视图名称