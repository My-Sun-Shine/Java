package spring.aop.learn07;


import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


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

    @After(value = "mypt()")
    public void myAfter() {
        System.out.println("最终通知：总是会被执行的。等同于finally{} ");
    }


    @Before(value = "mypt()")
    public void myBefore() {
        System.out.println("myBefore 前置通知：在目标方法之前先执行的，例如输出日志");
    }

    /**
     * 注解@Pointcut:定义和管理切入点
     *    属性： value ，切入点表达式
     *    位置：在方法的上面。在一个自定义的方法上面， 这个方法的名称看做是切入点的别名。
     *         在其他的通知方法中， value属性可以使用方法名称
     */
    @Pointcut(value = "execution(* spring.aop.learn07.SomeServiceImpl.doThird(..))")
    private void mypt(){
        //无需代码
    }

}
