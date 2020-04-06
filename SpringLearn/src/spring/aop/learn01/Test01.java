package spring.aop.learn01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @Classname Test
 * @Date 2020/4/4 13:55
 * @Created by Falling Stars
 * @Description
 */
public class Test01 {

    @Test
    public void test01() {
        String configLocation = "spring/aop/learn01/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);

        /**
         *
         * ClassPathXmlApplicationContext(){
         *   //构造方法
         *   SomeService someServiceTarget = new SomeServiceImpl();
         *   MyAspect myAspect  = new MyAspect();
         *
         *   <aop:aspectj-autoproxy />:执行aspectj框架内部的功能，在Spring容器中遍历对象，
         *   查找注解（AspectJ框架的注解），寻找@Aspect ，在@Aspect所在的类中，找通知注解（例如@Before）
         *   根据切入点表达式，找到切面加入的位置，生成代理对象。
         *   这个代理对象是改造的内存中的目标对象。 目标对象就是代理
         *
         * }
         */

        //从spring容器中获取目标对象，实际是代理
        SomeService proxy = (SomeService) ctx.getBean("someServiceTarget");
        //目标对象有接口，使用jdk动态代理： com.sun.proxy.$Proxy6
        System.out.println("proxy:" + proxy.getClass().getName());
        //通过代理执行业务方法，实现功能增强
        proxy.doSome();
    }


    @Test
    public void test02() {
        String configLocation = "spring/aop/learn01/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        SomeService proxy = (SomeService) ctx.getBean("someServiceTarget");
        System.out.println("proxy:" + proxy.getClass().getName());
        proxy.doSome("张总",12);
    }
}
