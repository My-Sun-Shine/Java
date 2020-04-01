## 不使用xml配置
* 注解@Configuration放在类的上面，这个类就相当于xml配置文件
* 注解@Bean创建对象，放在方法上面，等同bean元素
    * 方法名称默认为bean元素的id
    * 可以使用name或者value属性自定义bean元素的id

