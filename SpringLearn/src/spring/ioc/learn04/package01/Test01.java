package spring.ioc.learn04.package01;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.ioc.learn04.package01.Student;


/**
 * @Classname Test01
 * @Date 2020/3/31 22:20
 * @Created by Falling Stars
 * @Description 引用类型属性 byName自动注入
 */
public class Test01 {


    @Test
    public void test01() {
        String configLocation = "spring/ioc/learn04/package01/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student");
        System.out.println("student" + student);
    }


}
