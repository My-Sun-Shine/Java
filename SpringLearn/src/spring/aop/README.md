# AOP(Aspect Orient Programming)：面向切面编程
* **Aspect：切面**，表示给业务方法增强的功能，增强的任何功能都叫做切面，常见的切面有日志功能，事务，参数检查， 权限处理等等。切面是非业务功能
* AOP是基于动态代理的，把动态代理的技术进行了理论的升华，把动态代理的使用规范化 
* 动态代理可以使用JDK动态代理，也可以使用CGLIB的动态代理
* AOP是一种动态的编程思想，在程序执行过程中，给目标方法增加了一定的功能，它是对OOP的有效补充
* OOP:面向对象的编程，是一种静态的思想

### AOP重点的内容：
* 找出切面，从大量的业务方法中，找出共用的，重复的内容，把它们称为切面
* 把切面合理的加入到目标方法中

### AOP的相关概念
* **切面Aspect**：给业务方法增加的功能，例如日志，事务等等。 这是理论的概念，在代码中，使用通知表示切面
* **织入**：把切面功能加入到业务方法的过程
* **连接点JoinPoint**：表示业务方法，这个方法可以加入切面的功能，JoinPoint表示切面加入的具体位置，在某个业务方法加入切面功能
* **切入点Pointcut**：是一个或多个连接点JoinPoint的集合，表示多个方法可以加入切面的功能，切入点也是表示切面加入的位置
* **目标对象Target**：给某个类加入切面的功能，这个类就是目标类
* **通知Advice**：也叫做增强，它是切面在代码中的表示，通知表示切面加入到目标方法的时间，在目标方法之前，还是目标方法之后，或者是其他的时间
* 使用通知表示切面执行时间，使用切入点表达式表示切面执行的位置

### AOP相应的框架
* Spring框架实现了AOP的功能，但是Spring的实现比较繁琐，使用不方便
* AspectJ是一个独立的框架，只做切面的功能，是基于java语言的，功能强大，灵活

### 使用AspectJ框架实现AOP的开发
* AspectJ支持5种通知，5种表示切面5个执行的时间点
    * 前置通知：在目标方法之前
    * 后置通知：在目标方法之后
    * 环绕通知：在目标方法前和后
    * 异常通知：在目标方法抛出异常时执行的
    * 最终通知：总是会执行的
* AspectJ框架使用切入点表达式，表示切面加入的位置
```
语法：execution(
        方法的访问修饰符 
        返回值 
        包名类名
        方法名(方法的参数个数，数据类型) 
        方法抛出的异常 
        )
* 表示0至多个任意字符
.. 用在方法参数中，表示任意多个参数；用在包名中，表示当前包以及其子包路径
+ 用在类名后，表示当前类以及其子类；用在接口后，表示当前接口及其实现类
``` 
```
execution(public * *(..))
指定切入点为：任意公共方法
execution(* set*(..))
指定切入点为：任何一个以“set”开始的方法。
execution(* com.xyz.service.*.*(..))
指定切入点为：定义在 service 包里的任意类的任意方法。
execution(* com.xyz.service..*.*(..))
指定切入点为：定义在 service 包或者子包里的任意类的任意方法。“..”出现在类名中时，后面必须跟“*”，表示包、子包下的所有类。
execution(* *..service.*.*(..))
指定所有包下的 serivce 子包下所有类（接口）中所有方法为切入点
execution(* *.service.*.*(..))
指定只有一级包下的 serivce 子包下所有类（接口）中所有方法为切入点
execution(* *.ISomeService.*(..))
指定只有一级包下的 ISomeSerivce 接口中所有方法为切入点
execution(* *..ISomeService.*(..))
指定所有包下的 ISomeSerivce 接口中所有方法为切入点
execution(* com.xyz.service.IAccountService.*(..))
指定切入点为：IAccountService 接口中的任意方法。
execution(* com.xyz.service.IAccountService+.*(..))
指定切入点为：IAccountService 若为接口，则为接口中的任意方法及其所有实现类中的任意方法；若为类，则为该类及其子类中的任意方法。
execution(* joke(String,int)))
指定切入点为：所有的 joke(String,int)方法，且 joke()方法的第一个参数是 String，第二个参数是 int。
如果方法中的参数类型是 java.lang 包下的类，可以直接使用类名，否则必须使用全限定类名，如 joke( java.util.List, int)。
execution(* joke(String,*)))
指定切入点为：所有的 joke()方法，该方法第一个参数为 String，第二个参数可以是任意类型，如joke(String s1,String s2)和joke(String s1,double d2)都是，但joke(String s1,double d2,Strings3)不是。
execution(* joke(String,..)))
指定切入点为：所有的 joke()方法，该方法第一个参数为 String，后面可以有任意个参数且参数类型不限，如 joke(String s1)、joke(String s1,String s2)和 joke(String s1,double d2,String s3)都是。
execution(* joke(Object))
指定切入点为：所有的 joke()方法，方法拥有一个参数，且参数是 Object 类型，joke(Object ob)是，但joke(String s)与joke(User u)均不是。
execution(* joke(Object+)))
指定切入点为：所有的 joke()方法，方法拥有一个参数，且参数是 Object 类型或该类的子类，不仅 joke(Object ob)是，joke(String s)和 joke(User u)也是。
```
### 使用AspectJ框架实现AOP，使用注解
* AspectJ是一个切面框架，使用xml配置文件和注解实现AOP
* 步骤：
    * 新建javaProject
    * 导入jar
        * Spring的核心：spring-beans.jar,spring-core.jar,spring-context.jar,spring-expression.jar
        * 支持注解和aop的jar：spring-aop.jar
        * AspectJ框架自己jar: aspectjrt.jar,aspectjweaver.jar
        * 日志：commons-logging.jar
        * 可选的：log4j.jar, junit.jar
    * 定义目标类，有接口的目标类
    * 定义切面类
        * 在类的上面加入注解@Aspect
        * 在类中定义方法，方法的上面加入AspectJ框架的通知注解，例如@Before(value="切入点表达式")
    * 定义Spring的配置文件
        * 声明目标类对象
        * 声明切面类对象
        * 声明自动代理生成器，目的是生成目标对象的代理对象；这个代理对象放入到Spring的容器中，代理对象是由AspectJ框架生成的
    * 定义测试类，从Spring容器中获取目标对象（是由AspectJ修改后的代理对象）；通过代理对象执行业务方法，实现功能的增强
