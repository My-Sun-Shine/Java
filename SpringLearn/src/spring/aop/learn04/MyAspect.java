package spring.aop.learn04;


import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;


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
     * 注解@AfterThrowing:异常通知
     * 属性：
     * 1.value ，切入点表达式
     * 2.throwing ,自定义的变量，表示目标方法抛出的异常对象，
     * 变量名必须和通知方法的参数名一样，参数是Throwable类型的
     * 位置：方法定义上面
     * <p>
     * 特点：
     * 1.在目标方法抛出异常时执行的。
     * 2.不是异常处理方法。异常还是抛出的。
     * 3.可以看做是目标方法监控程序。
     */

    @AfterThrowing(value = "execution(* spring.aop.learn04.SomeServiceImpl.doSecond(..))", throwing = "ex")
    public void myAfterThrowing(Throwable ex) {
        //可以对发生的异常进行记录或者发生通知,并不能处理异常
        System.out.println("异常通知：在目标方法抛出异常时执行的：" + ex.getMessage());
    }
}
