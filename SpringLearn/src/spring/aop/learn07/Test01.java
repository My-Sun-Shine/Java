package spring.aop.learn07;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Classname Test
 * @Date 2020/4/4 13:55
 * @Created by Falling Stars
 * @Description
 */
public class Test01 {

    @Test
    public void test01() {
        String configLocation = "spring/aop/learn07/applicationContext.xml";
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        //从spring容器中获取目标对象，实际是代理
        SomeServiceImpl proxy = (SomeServiceImpl) ctx.getBean("someServiceTarget");
        // 目标类没有接口，使用cglib动态代理
        // proxy:spring.aop.learn07.SomeServiceImpl$$EnhancerBySpringCGLIB$$6764229c
        System.out.println("proxy:" + proxy.getClass().getName());
        //通过代理执行业务方法，实现功能增强
        proxy.doThird();
    }


}
