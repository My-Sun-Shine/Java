# SpringMVC的静态资源访问
### web.xml
* url-pattern标签值为*.do的时候，可以访问静态资源
* url-pattern标签值改为/之后，无法访问静态资源，下面通过设置springmvc.xml来访问静态资源
```
    <!-- 在Tomcat中，有一个专门用于处理静态资源访问的 Servlet – DefaultServlet。
         其<servlet-name/>为default可以处理各种静态资源访问请求
         该Servlet注册在Tomcat服务器的web.xml中
         在Tomcat安装目录/conf/web.xml-->
    <!-- 处理静态资源,把静态资源的请求交给default这个Servlet -->
	<mvc:default-servlet-handler/>
	<!-- 
	     default-servlet-handler 和@RequestMapping有冲突的，导致静态资源可以访问
	     但动态资源不能访问， 解决方式，加入注解驱动
	 -->
	 <mvc:annotation-driven />
```
```
    <!-- 处理静态资源，由框架自己处理静态资源的访问
	     location:静态资源的目录，web应用中的目录，不要使用/WEB-INF
	     mapping：对静态资源访问的uri地址，可以使用通配符 "**" ,表示任意的目录和文件
	 -->
    <mvc:resources location="/html/" mapping="/html/**" />
    <mvc:resources location="/images/" mapping="/images/**" />

    <!-- 声明注解驱动 -->
    <mvc:annotation-driven />
```
