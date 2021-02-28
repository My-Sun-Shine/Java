### SpringMVC 请求重定向
* redirect: 表示重定向，实现 response.sendRedirect("xxx.jsp")
  * 不能访问/WEB-INF下面的资源
  * 框架可以把Model中简单类型的数据转为String，作为get请求的参数传递
  * 在目标的页面中，可以使用${param.参数名} 获取参数值
* 处理器方法返回ModelAndView实现重定向到视图页面
   * 语法：mv.setViewName("redirect:视图的完整路径")
   * redirect:的特点是不和视图解析器一同工作
  
* 处理器方法返回String，使用redirect重定向到视图页面
   * 语法：return "redirect:视图完整路径"
   * redirect:的特点是不和视图解析器一同工作
* 重定向到其他的处理器，由其他的处理器继续处理用户的请求