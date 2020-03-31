package spring.ioc.learn02;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Classname Test02
 * @Date 2020/3/31 12:33
 * @Created by Falling Stars
 * @Description
 */
public class Test01 {
    String configLocation = "spring/ioc/learn02/applicationContext.xml";

    /**
     * Bean的装配
     * 默认装配方式：Spring调用Bean类的无参构造函数，创建空值的实例对象
     */
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        SomeService service = (SomeService) context.getBean("SomeService01");
        service.doSome();
    }

    /**
     * 单例模式：在容器创建的时候创建的
     */
    @Test
    public void test02() {

        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        SomeService service1 = (SomeService) context.getBean("SomeService02");
        System.out.println(service1);
        SomeService service2 = (SomeService) context.getBean("SomeService02");
        System.out.println(service2);
        if (service1 == service2) {
            System.out.println("同一个对象");
        }
    }

    /**
     * 原型模式：在执行getBean的时候创建对象
     */
    @Test
    public void test03() {
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        SomeService service1 = (SomeService) context.getBean("SomeService03");
        System.out.println(service1);
        SomeService service2 = (SomeService) context.getBean("SomeService03");
        System.out.println(service2);
        if (service1 != service2) {
            System.out.println("不是同一个对象");
        }
    }
}
