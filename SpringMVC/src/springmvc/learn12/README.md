### SpringMVC 多个拦截器
* 两个拦截器  
```
1.preHandler()=true , 2.preHandler()=true
拦截器1 MyInterceptor的preHandle()
拦截器2 MyInterceptor的preHandle()
执行了MyController处理器的doSome()
拦截器2 MyInterceptor的postHandle() 
拦截器1 MyInterceptor的postHandle() 
拦截器2 MyInterceptor的afterCompletion()
拦截器1 MyInterceptor的afterCompletion()

1.preHandler()=true , 2.preHandler()=false
拦截器1 MyInterceptor的preHandle()
拦截器2 MyInterceptor的preHandle()
拦截器1 MyInterceptor的afterCompletion()

1.preHandler()=false , 2.preHandler()=true|false
拦截器1 MyInterceptor的preHandle()
```
* 拦截流程
1. DispatcherServlet中的doDispatch()方法
2. 执行到mappedHandler.applyPreHandle(processedRequest, response)
```
//获取程序中的所有拦截器对象
HandlerInterceptor[] interceptors = getInterceptors();
org.springframework.web.servlet.handler.AbstractUrlHandlerMapping$PathExposingHandlerInterceptor
springmvc.learn12.interceptors.MyInterceptor
springmvc.learn12.interceptors.MyInterceptor2

//从数组中获取拦截器对象
HandlerInterceptor interceptor = interceptors[i];

//拦截器的执行：拦截器对象调用自己的方法
interceptor.preHandle(request, response, this.handler)
输出：
拦截器1 MyInterceptor的preHandle()
拦截器2 MyInterceptor的preHandle()
this.interceptorIndex //2
```
3. 执行到mv = ha.handle(processedRequest, response, mappedHandler.getHandler());输出"执行了MyController处理器的doSome()"
4. 执行到mappedHandler.applyPostHandle(processedRequest, response, mv);
```
因为this.interceptorIndex值，倒序执行postHandle()
拦截器2 MyInterceptor的postHandle()
拦截器1 MyInterceptor的postHandle()
```
5. 执行到processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
    * 判断有无异常，如果有异常调用方法mv = processHandlerException(request, response, handler, exception);方法处理异常，使用的HandlerExceptionResolver来处理异常
    * 执行到render(mv, request, response);
        * 执行到view = resolveViewName(mv.getViewName(), mv.getModelInternal(), locale, request);
        * 创建视图对象：调用视图解析器（ViewResolver）,视图对象是InternalResourceView
        * 执行到view.render(mv.getModelInternal(), request, response); 视图对象调用自己的render()方法
        * view.render()方法(AbstractView)把Model中的数据放入到request作用域.执行request.setAttribute();并执行执行forward()
    * 执行到mappedHandler.triggerAfterCompletion(request, response, null);
```
执行到mappedHandler.triggerAfterCompletion(request, response, null);
拦截器2 MyInterceptor的afterCompletion()
拦截器1 MyInterceptor的afterCompletion()
```
### 拦截器和过滤器的对比
1. 对象类型
   * 过滤器是Servlet中的对象
   * 拦截器是SpringMVC中的对象，是由springmvc创建和管理的
2. 过滤类型
   * 过滤器是对request,response做过滤的，设置对象的属性，参数的
   * 拦截器是获取到request，做判断处理，能截断请求 
3. 创建者
   * 过滤器是tomcat创建并调用执行的。
   * 拦截器是在中央调度器中，从数组中获取到每个拦截器，通过对象调用自己的方法
4. 执行时间点
   * 过滤器是请求之前先执行的，有一个执行时间点
   * 拦截器是三个执行时间点：在请求之前（preHandler）、在请求之后（postHandler）、在请求的结束（afterCompletion）
5. 过滤的请求类型   
   * 过滤器是能够过滤所有的请求（servlet， jsp等等）
   * 拦截器只能拦截动态资源的请求，例如*.do ,不能拦截jsp