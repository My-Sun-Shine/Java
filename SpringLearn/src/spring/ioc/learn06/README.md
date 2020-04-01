# 使用注解创建对象，给属性赋值
* 支持注解的使用：spring-aop.jar
* 声明组件扫描器标签 <context:component-scan>

### 三种方式定义多个包
```
<!--1.多次指定component-scan-->
<!--2.使用分割符(;或者,)指定多个包-->
<!--<context:component-scan base-package="spring.ioc.learn06.package01;spring.ioc.learn06.package02"/>-->
<!--3.指定父包-->
<!--<context:component-scan base-package="spring.ioc.learn06"/>-->
```

### 注解@Component: 创建类的对象，等同于<bean>的作用，package01
* 属性：value，表示对象的名称，也就是<bean>的id
* 位置：在类的上面，表示Spring创建此类的对象，调用类的无参数构造方法
* 创建的对象默认是单例的
* 和@Component功能相同的其他注解
    * 1.@Repository：放在Dao层的实现类上，创建Dao对象
    * 2.@Service：放在Service层的实现类上面，创建Service对象
    * 3.@Controller：放在处理器类的上面，创建处理器对象的(简单理解Servlet)
```
//注解@Component(value="myStudent")等同于
<bean id="myStudent" class="spring.ioc.learn06.package01.Student" />
```

### 简单类型的属性赋值，使用@value，package02
* 属性：value，简单类型的属性值
* 位置：1.在类中属性定义的上面，这样使用无需定义set方法，2.在set方法上面
     
### 注解@Autowired：给引用类型赋值，package02
* 位置：1.在属性定义的上面，这样使用无需定义set方法，2.在set方法上面
* Autowired使用自动注入，默认为ByType形式
          

### 注解@Autowired：给引用类型赋值，package03
* 属性：required，boolean类型值，默认为true，
    * true表示该引用类型一定要赋值成功，如果赋值错误，则程序报错
    * false表示该引用类型如果赋值失败，程序不会报错，值设置为null
* ByName形式
    * 1.@Autowired
    * 2.@Qualifier(value="bean的id")：表示把指定的bean对象赋值给引用类型对象
     
### 注解@Resource：来自JDK中的注解，是给引用类型赋值的，package04
* 支持byName,byType，默认使用byName
* 位置：1.在属性定义上，无需定义set方法，2.在set方法上
* 虽然默认使用ByName赋值，但是如果ByName赋值失败，则使用ByType赋值
* 使用ByName属性赋值，需要使用属性name，它的值为bean的id
     
