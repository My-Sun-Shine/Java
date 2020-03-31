package spring.ioc.learn03.package01;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Classname Test01
 * @Date 2020/3/31 22:20
 * @Created by Falling Stars
 * @Description 设值注入：由spring调用类的set方法，在set方法中给属性赋值
 */
public class Test01 {


    /**
     * 设值注入
     */
    @Test
    public void test01() {
        String configLocation = "spring/ioc/learn03/package01/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student");
        System.out.println("student" + student);
    }


}
