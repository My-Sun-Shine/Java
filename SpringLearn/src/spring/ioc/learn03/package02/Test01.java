package spring.ioc.learn03.package02;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Classname Test01
 * @Date 2020/3/31 22:20
 * @Created by Falling Stars
 * @Description 构造注入：spring调用类的有参数构造方法，在构造方法中完成属性的赋值，同时创建对象
 */
public class Test01 {


    /**
     * 构造注入
     */
    @Test
    public void test01() {
        String configLocation = "spring/ioc/learn03/package02/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student");
        System.out.println("student" + student);
    }

    @Test
    public void test02() {
        String configLocation = "spring/ioc/learn03/package02/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student01");
        System.out.println("student" + student);
    }

    @Test
    public void test03() {
        String configLocation = "spring/ioc/learn03/package02/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student02");
        System.out.println("student" + student);
    }
}
