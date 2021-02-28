### SpringMVC 自定义异常类
* 框架是把异常集中在一个地方处理，能够处理异常的类叫做异常处理器
* 框架把实现HandlerExceptionResolver接口的类叫做异常处理器
* 框架异常的处理方式有些类似AOP的思想
* 把处理器类中异常处理代码提取处理放到单独的类
* 自定义异常处理器使用步骤：
  * 定义类实现HandlerExceptionResolver接口
  * 在springmvc配置文件，声明自定义的异常处理器