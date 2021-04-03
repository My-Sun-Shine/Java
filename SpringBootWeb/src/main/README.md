## 项目

### 引入资源

* com.springbootweb.dao文件夹，com.springbootweb.entity文件夹
* static文件夹中添加css、js、img文件夹，注意：必须使用asserts嵌套一层，不知原因
* templates文件夹添加html文件
* 新建配置类MyMvcConfig01，configurer01方法设置首页
* 从webjar中下载bootstrap，然后将html中的路径替换为th:href、th:src

```xml

<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>4.0.0</version>
</dependency>
```
```properties
# 链接前缀
server.servlet.context-path=/crud
```