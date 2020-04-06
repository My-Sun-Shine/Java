package spring.aop.learn01;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


/**
 * @Classname MyAspect
 * @Date 2020/4/4 13:30
 * @Created by Falling Stars
 * @Description
 */
@Aspect//该属性表示当前类时切面类，在类的上面进行定义
//切面类：是用来给目标方法增强功能的，在切面类中有多个切面，每个切面是一个通知，表示要增强的功能
public class MyAspect {

    //定义方法，表示增强的功能，也就是切面

    /**
     * 注解@Before:前置通知
     * 属性：value ，表示切入点表达式。表示切面加入的位置
     * 位置：在方法的上面
     * <p>
     * 特点：
     * 1.在目标方法之前，先执行的。
     * 2.不能影响目标方法的执行
     * 3.不会改变目标方法的执行结果。
     */
    @Before(value = "execution(* spring.aop.learn01.SomeServiceImpl.doSome())")
    public void myBefore() {
        System.out.println("myBefore 前置通知：在目标方法之前先执行的，例如输出日志");
    }


    @Before(value = "execution(* spring.aop.learn01.SomeServiceImpl.doSome(..))")
    public void myBefore2() {
        System.out.println("myBefore2 前置通知：在目标方法之前先执行的，例如输出日志");
    }

    @Before(value = "execution(* spring.aop.learn01.SomeServiceImpl.do*(..))")
    public void myBefore3() {
        System.out.println("myBefore3 前置通知：在目标方法之前先执行的，例如输出日志");
    }

    /**
     * 使用通知注解修饰的方法叫做通知方法，
     * 通知方法可以有参数，第一个参数JoinPoint, 表示连接点，也就是目标方法。
     * <p>
     * JoinPoint:表示切入点表达式中的每一个方法。
     * JoinPoint看做是jdk动态代理中的invoke(Method method),他的Method参数。
     */
    @Before(value = "execution(* spring.aop.learn01.SomeServiceImpl.doSome(..))")
    public void myBefore4(JoinPoint joinPoint) {
        //使用JoinPoint获取正在执行的方法信息，例如方法的名称，方法的参数等等
        System.out.println(joinPoint);
        String methodName = joinPoint.getSignature().getName();//方法名称
        //方法执行时的参数
        StringBuilder s = new StringBuilder();
        Object args[] = joinPoint.getArgs();
        for (Object arg : args) {
            s.append(arg).append(",");
        }
        //就是切面的功能代码，例如输出日志的代码
        System.out.println("myBefore4 前置通知：在目标方法之前先执行的，在方法：" + methodName + ",参数：" + s.toString() + ",例如输出日志");
    }
}
