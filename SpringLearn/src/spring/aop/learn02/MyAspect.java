package spring.aop.learn02;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
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
     * 注解@AfterReturning:后置通知
     * 属性：
     * 1.value ,表示切入点表达式，切面加入的位置
     * 2.returning,表示目标方法的执行结果， 是一个自定义的变量名，需要和通知方法的形参名一样。
     * 位置：
     * 在方法定义的上面
     * <p>
     * 特点：
     * 1.在目标方法之后执行的
     * 2.能够获取到目标方法的执行结果。
     * 3.不会影响目标方法的执行
     * <p>
     * 方法的参数：
     * Object result:表示目标方法的执行结果（返回值）
     * Object result = SomeServiceImpl.doOther(); //name:xxxx
     * JoinPoint jp: 表示连接点方法。 在通知方法中都可以加入这个参数。
     * 注意：JoinPoint如果使用，必须是参数列表中的第一个参数。
     * <p>
     * 后置通知的执行逻辑
     * 1）执行目标方法
     * Object result  =  SomeServiceImpl.doOther(..) //String
     * <p>
     * 2）执行通知方法
     * myAfterReturning( result )
     */

    @AfterReturning(value = "execution(* spring.aop.learn02.SomeService.doOther(..))", returning = "result")
    public void myAfterReturning(JoinPoint jp, Object result) {
        System.out.println(jp);
        StringBuilder sb = new StringBuilder();
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            sb.append(arg).append(",");
        }
        System.out.println("参数：" + sb);
        //修改目标方法的执行结果
        if (result != null) {
            String s = (String) result;
            result = s + "，结果数据修改";
        }
        System.out.println("后置通知：在目标方法之后执行的，目标方法执行结果是：" + result);
    }
}
