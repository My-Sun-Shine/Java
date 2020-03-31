package spring.ioc.learn04.package02;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Classname Test01
 * @Date 2020/3/31 22:20
 * @Created by Falling Stars
 * @Description 引用类型属性 byType自动注入
 */
public class Test01 {


    @Test
    public void test01() {
        String configLocation = "spring/ioc/learn04/package02/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student");
        System.out.println("student" + student);
    }


}
