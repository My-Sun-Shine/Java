package spring.ioc.learn06.package02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname Student
 * @Date 2020/4/1 21:30
 * @Created by Falling Stars
 * @Description 使用注解给简单类型赋值
 */

@Component("MyStudent02")//可以省略value
public class Student {

    @Value(value = "詹三")
    private String name;

    @Value("25")
    private int age;

    /**
     * 注解@Autowired：给引用类型赋值
     * 位置：1.在属性定义的上面，这样使用无需定义set方法
     * 2.在set方法上面
     * <p>
     * Autowired使用自动注入，默认为ByType形式
     */
    @Autowired
    private School MySchool02;


    /**
     * 简单类型的属性赋值，使用@value
     * 注解@Value
     * 属性：value，简单类型的属性值
     * <p>
     * 位置：1.在类中属性定义的上面，这样使用无需定义set方法
     * 2.在set方法上面
     */
    public Student() {
        super();
        System.out.println("Student02的无参数构造方法");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", MySchool02=" + MySchool02 +
                '}';
    }
}
