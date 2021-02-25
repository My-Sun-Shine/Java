## SpringMVC框架内部请求的处理流程
1. 浏览器提交请求到中央调度器DispatherServlet，**中间调度器可以有多个**
2. DispatherServlet拿到请求，直接将请求转给处理器的映射器
   * **处理器的映射器**：实现了SpringMVC框架中的HandlerMapping接口类，映射器在框架中有多个
   * **处理器的映射器作用**：根据请求的信息获取到对应的处理器对象，执行ApplicationContext ctx;ctx.getBean(请求的URL地址)，获取到处理器对象
3. 处理器映射器会根据请求，找到处理该请求的处理器，并将其封装为处理器执行链后，返回给中央调度器DispatherServlet
4. 中央调度器DispatherServlet根据处理器执行链中的处理器controller，找到能够执行该处理器的处理器适配器
   * **处理器适配器**：实现了SpringMVC框架中HandlerAdapter接口的类，适配器类有多个 
   * **处理器适配器作用**：根据处理器对象找到对应的适配器对象，执行controller中handlerRequest
5. 处理器适配器调用执行处理器controller
6. 处理器将处理结果及要跳转的视图封装到一个对象 ModelAndView 中，并将其返回给处理器适配器
7. 处理器适配器直接将结果返回给中央调度器
8. 中央调度器DispatherServlet调用视图解析器，将 ModelAndView 中的视图名称封装为视图对象。
9. 视图解析器将封装了的视图对象返回给中央调度器
10. 中央调度器DispatherServlet调用视图对象，让其自己进行渲染，即进行数据填充，形成响应对象。
11. 中央调度器DispatherServlet响应浏览器

## 源代码
1. 创建容器
   * DispatherServlet在init的时候调用FrameworkServlet中的initWebApplicationContext()
   * wac = createWebApplicationContext(rootContext); 创建springmvc的容器对象并读取contextConfigLocation的配置文件springmvc.xml
   * getServletContext().setAttribute(attrName, wac); 把容器对象放入到ServletContext中
2. 读取DispatherServlet的配置文件DispatherServlet.properties
   * 创建系统的处理器映射器、处理器的适配器，视图解析器
3. 处理请求
   * 接收请求，使用doService()中的doDispatch(request, response)
   * HandlerExecutionChain：处理器执行链
   * 执行处理器的映射器：mappedHandler = getHandler(processedRequest);
   * 处理器映射器中，使用 handler = getApplicationContext().getBean(handlerName); 根据请求获取处理器对象Controller
   * 找到适配器HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
   * 执行处理器方法 mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
```
处理器映射器
org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping
org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping

适配器
org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter
org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter
org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter
```

  
