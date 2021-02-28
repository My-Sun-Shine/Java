### SpringMVC 使用注解处理异常
* 框架是把异常集中在一个地方处理，能够处理异常的类叫做异常处理器
* 框架把实现HandlerExceptionResolver接口的类叫做异常处理器
* 框架异常的处理方式有些类似AOP的思想
* 把处理器类中异常处理代码提取处理放到单独的类
* 自定义异常处理器使用步骤：
  * 定义类实现HandlerExceptionResolver接口
  * 在异常处理类上面加@ControllerAdvice，目的是给处理器类中的方法，增强功能，一般是增强异常处理  
  * 注解处理异常：使用@ExceptionHandler, 放在处理器方法的上面，表示这个方法可以处理异常