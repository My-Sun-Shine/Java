### SpringMVC 处理器的返回值
1. 处理器方法返回的String可以指定逻辑视图名，通过视图解析器解析InternalResourceViewResolver可以将其转换为物理视图地址
2. 处理器方法返回的String可以指定资源的物理视图名，此时就不需要再在视图解析器中再配置前辍与后辍
3. 处理器方法返回void, 没有数据也没有视图，可以通过HttpServletResponse输出数据到浏览器
4. 处理器方法返回java object, object表示数据，和视图无关
### 框架的功能实现逻辑：
1. 实现把某个java对象转为某种格式的数据（比如json，xml，文本的，二进制等等），是通过HttpMessageConverter实现的(适配器)
   * **HttpMessageConverter接口**：提供了把java对象转为json,xml等数据格式的功能，具体的实现有接口的实现类完成的
   * HttpMessageConverter接口有很多的实现类，默认框架有他的四个实现类
   * 如果想使用更多的实现类，比如要处理json的数据，需要在springmvc配置文件中加入<mvc:annotation-driven>, 加入了注解驱动，会在springmvc项目中创建HttpMessageConverter接口的7个实现类对象，其中的MappingJackson2HttpMessageConverter实现java对象到json的转换。
```
0 = {ByteArrayHttpMessageConverter@5156} 
1 = {StringHttpMessageConverter@5157} 
2 = {ResourceHttpMessageConverter@5158} 
3 = {SourceHttpMessageConverter@5159} 
4 = {AllEncompassingFormHttpMessageConverter@5160} 
5 = {Jaxb2RootElementHttpMessageConverter@5161} 
6 = {MappingJackson2HttpMessageConverter@5162} 
```   
2. 处理器方法返回String，实现类StringHttpMessageConverter；处理器方法返回对象，要转为json格式，实现类MappingJackson2HttpMessageConverter
3. 把数据输出到浏览器：在处理器方法的定义上面，加入注解@ResponseBody, 作用把处理器方法的返回值输出到浏览器，也就是输出到应答体
4. 处理器方法返回Object,表示数据，一般的开发步骤：
   * SpringMVC配置文件加入， 注解驱动<mvc:annotation-driven>
   * 在处理器方法的定义上面，加入注解@ResponseBody
   * 大多数对象被转为json格式，需要使用json的工具库，默认使用Jackson，需要在项目中加入jackson的jar