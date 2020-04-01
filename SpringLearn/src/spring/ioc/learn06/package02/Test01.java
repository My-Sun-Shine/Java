package spring.ioc.learn06.package02;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Classname Test01
 * @Date 2020/4/1 21:31
 * @Created by Falling Stars
 * @Description
 */
public class Test01 {
    @Test
    public void test01() {
        String configLocation = "spring/ioc/learn06/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("MyStudent02");
        System.out.println("student" + student);
    }
}
