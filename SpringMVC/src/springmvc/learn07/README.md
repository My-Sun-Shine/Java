### SpringMVC 请求转发
* forward:表示转发，实现request.getRequestDispatcher("xx.jsp").forward()
* 处理器方法返回ModelAndView实现转发到视图页面
   * 语法：mv.setViewName("forward:视图的完整路径")
   * forward:的特点是不和视图解析器一同工作
* 处理器方法返回String，使用forward转发到视图页面
   * 语法：return "forward:视图完整路径"
   * forward:的特点是不和视图解析器一同工作
* 转发到其他的处理器，由其他的处理器继续处理用户的请求