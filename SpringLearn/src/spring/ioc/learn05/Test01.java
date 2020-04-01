package spring.ioc.learn05;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @Classname Test01
 * @Date 2020/3/31 22:20
 * @Created by Falling Stars
 * @Description 为应用指定多个Spring配置文件
 */
public class Test01 {


    /**
     * 提高配置文件的可读性与可维护性，可以将
     * Spring 配置文件分解成多个配置文件
     */
    @Test
    public void test01() {
        String configLocation = "spring/ioc/learn05/applicationContext.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        Student student = (Student) context.getBean("Student");
        System.out.println("student" + student);
    }


}
