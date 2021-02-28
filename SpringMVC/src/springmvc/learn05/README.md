### SpringMVC
1. 处理器方法的形参名和请求中参数名一样，按名称对应接收参数
2. 如果请求中参数名和处理器方法的形参名不一样，需要在处理器方法形参前面加入@RequestParam(value="请求中参数名")  
3. 使用java对象接收请求的多个参数：要求请求中参数名和对象的属性名必须一样，按名称对应给属性赋值
4. 在web.xml中注解字符集过滤器，解决post请求中乱码的问题：会调用CharacterEncodingFilter类中doFilterInternal方法处理编码
```
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <!-- spring-web.jar -->
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <!-- 设置项目使用的字符编码 -->
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>

        <!-- 强制请求(request)对象，使用encoding的字符编码 -->
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>

        <!-- 强制应答（response）对象，使用encoding的字符编码 -->
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```