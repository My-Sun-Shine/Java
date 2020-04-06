package spring.aop.learn05;


import org.aspectj.lang.annotation.After;
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
     * 注解@After：最终通知
     * 属性： value , 切入点表达式
     * 位置：方法定义上面
     * 特点：
     * 1.总是会被执行的，不管是否有异常
     * 2.在目标方法之后执行的。
     * <p>
     * try{
     * //业务方法
     * }catch(Exception e) {
     * //异常
     * }finally{
     * myAfter()
     * }
     */

    @After(value = "execution(* spring.aop.learn05.SomeServiceImpl.doThird(..))")
    public void myAfter() {
        System.out.println("最终通知：总是会被执行的。等同于finally{} ");
    }
}
