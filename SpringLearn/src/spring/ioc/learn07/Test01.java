package spring.ioc.learn07;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Classname Test01
 * @Date 2020/4/1 21:31
 * @Created by Falling Stars
 * @Description
 */
public class Test01 {
    @Test
    public void test01() {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SomeService service = (SomeService) context.getBean("mySomeService");
        System.out.println("SomeService" + service);
        service.doSome();
    }

    @Test
    public void test02() {

        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SomeService service = (SomeService) context.getBean("SomeService01");
        System.out.println("SomeService" + service);
        service.doSome();
    }
}
