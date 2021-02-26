## 前置通知：在目标方法之前
* 使用注解 **@Before**
* 属性：value，表示切入点表达式，表示切面加入的位置
* 位置：在方法的上面
* 特点：
    * 1.在目标方法之前，先执行的
    * 2.不能影响目标方法的执行
    * 3.不会改变目标方法的执行结果
* 使用通知注解修饰的方法叫做通知方法
    * 通知方法可以有参数，第一个参数JoinPoint, 表示连接点，也就是目标方法
    * JoinPoint:表示切入点表达式中的每一个方法\
    * JoinPoint看做是jdk动态代理中的invoke(Method method),他的Method参数
## 添加<aop:aspectj-autoproxy />
* 声明自动代理生成器，创建代理对象
* 作用：把Spring容器中，符合条件的目标对象，加入切面的功能后，生成代理对象
* 生成方式：把容器中的目标对象，进行修改，加入切面的功能，所有目标类实际就是代理对象
* 执行aspectj框架内部的功能，在Spring容器中遍历对象，查找注解(AspectJ框架的注解)，寻找@Aspect，在@Aspect所在的类中，找通知注解(例如@Before)，根据切入点表达式，找到切面加入的位置，生成代理对象。

