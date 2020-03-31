# 使用Spring的Ioc创建和管理对象。

## IoC:控制反转

* **控制**：创建对象， 给属性赋值，管理对象的从创建，赋值，销毁的整个过程
* **反转**：把开发人员在代码中创建对象的权利，转移给代码之外的容器实现（Spring容器就是其中的一个）
* **正转**：在代码中，使用new构造方法，由开发人员主动创建对象
* Tomcat创建Servlet , 这个就是IoC

## IoC的技术实现
* **依赖查找**：DL 
* **依赖注入**：DI, 程序中只需要提供使用的对象名称，如何创建对象，给属性赋值，从容器中获取对象，这些都由容器自己完成
* Spring使用的是DI，实现对象的创建和管理。spring的底层是反射机制

## 实现步骤：
* 新建 java project
* 导入jar(必须的)
    * **四个核心：spring-beans.jar、spring-core.jar、spring-expression、spring-context.jar**
    * **日志：commons-logging.jar**
* 导入jar(可选的)
    * **日志的实现：log4j.jar**
    * **单元测试： junit.jar**
* 定义类(接口和实体类)，和没有使用框架一样
* 定义spring的配置文件**applicationContext.xml**，在配置文件中定义的对象，是由spring创建和管理的
* 定义测试类，创建spring容器对象ApplicationContext，从容器中获取对象，调用对象的业务方法


## applicationContext.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--
    bean对象定义，声明
        id:自定义的名称，表示对象的名字，名称定义同java中变量的名称，是唯一值
        class:类的全限定名称，不能是接口，Class.forName("类的全限定名称")

        <bean>等同于
        SomeService someService = new spring.ioc.service.impl.SomeServiceImpl();
    -->
    <bean id="SomeService" class="spring.ioc.service.impl.SomeServiceImpl"></bean>
</beans>
        <!--
            加入约束文件：
            bean: 由spring创建和管理的java对象。叫做bean
        -->
```

测试类
```
public class Test01 {
    //创建spring的容器对象， 获取创建好的java对象
    public static void main(String[] args) {
        //1.定义变量保存配置文件的位置
        String configLocation = "applicationContext.xml";

        /*
        2.创建spring的容器对象， 根据配置文件的位置使用ApplicationContext接口的实现类
        如果配置文件是在类路径中（classpath）,需要使用ClassPathXmlApplicationContext
        ApplicationContext代表applicationContext.xml中所有定义的对象
        此时已经创建applicationContext.xml中所有定义的对象
        */
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);

        //3.从spring中获取对象，使用getBean("<bean>的id")
        SomeService service = (SomeService) context.getBean("SomeService");

        //4.调用对象的业务方法
        service.doSome();
        
        //获取容器中对象的个数
        int counts = context.getBeanDefinitionCount();
        System.out.println("容器中定义对象个数：" + counts);
        
        //输出对象的名称
        String names[] = context.getBeanDefinitionNames();
        for (String name : names) {
           System.out.println(name);
        }
    }
}
```
