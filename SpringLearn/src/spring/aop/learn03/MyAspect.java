package spring.aop.learn03;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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
     * 注解 @throws Throwable
     * 注解 @Around:环绕通知
     * 属性：value , 切入点表达式
     * 位置：在方法定义的上面
     * 特点：
     * 1.在目标方法的前和后都能增强功能。
     * 2.能够改变目标方法的执行结果的，通过代理对象能获取到修改后的执行结果。
     * 3.控制目标方法是否执行。
     * <p>
     * 环绕通知方法的定义：
     * 1.方法由返回值 Object 类型
     * 2.方法有参数  ProceedingJoinPoint ， 表示连接点方法,等同于 Method
     * ProceedingJoinPoint extends org.aspectj.lang.JoinPoint
     */

    @Around(value = "execution(* spring.aop.learn03.SomeServiceImpl.doFirst(..))")
    public Object myAround(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕通知：在目标方法之前执行日志功能");
        //获取参数
        Object[] args = jp.getArgs();
        String name = null;
        if (args != null && args.length > 0) {
            name = (String) args[0];
        }
        Object proceed = null;
        if ("李四".equals(name)) {
            proceed = jp.proceed();
        } else {
            //不是李四,不会调用目标方法
        }


        if (proceed != null) {
            String str = (String) proceed;
            str += " Hello";
            proceed = str;
        }
        System.out.println("环绕通知：在目标方法之后，执行事务处理");
        return proceed;
    }
}
