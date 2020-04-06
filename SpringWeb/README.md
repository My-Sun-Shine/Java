# 在web项目中使用Spring

### Spring作为一个容器，管理java对象，Spring可以管理service，dao和其他的工具类对象

### 步骤：
* 1.新建web project
* 2.拷贝SpringLearn中spring.mybatis中的libs的jar ,src的源代码，配置文件
* 3.新建jsp，提交参数name,age
* 4.新建Servlet, 在doGet中获取name,age；从Spring容器中获取Service，调用Service的方法，实现注册功能
* 5.新建表示结果的jsp
* 6.修改web.xml 注册Servlet，注册过滤器
* 7.在web.xml中注册Spring框架中的监听器ServletContextListener

### 在web项目中使用Spring的遇到的问题
* 1.容器对象创建多次
* 2.在不同的servlet中不能全局使用容器对象。

### 解决问题的思路
* 使用监听器，实现javax.servlet.ServletContextListener 
* 在监听器的初始方法中，1.创建容器对象。2.把创建好的容器放入到ServletContext作用域
```
private WebApplicationContext context;
public interface WebApplicationContext extends ApplicationContext
WebApplicationContext:是web应用中使用的容器类型
ApplicationContext是javase项目中使用的容器类型
1.创建容器的代码
if (this.context == null) {
    //使用反射机制，创建容器对象
	this.context = createWebApplicationContext(servletContext);
}
2.把容器对象放入到ServletContext
servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
key是WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE
```