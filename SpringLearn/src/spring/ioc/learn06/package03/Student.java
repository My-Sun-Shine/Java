package spring.ioc.learn06.package03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname Student
 * @Date 2020/4/1 21:30
 * @Created by Falling Stars
 * @Description 使用注解给简单类型赋值
 */

@Component("MyStudent03")//可以省略value
public class Student {

    @Value(value = "詹三")
    private String name;

    @Value("25")
    private int age;

    /**
     * 注解@Autowired：给引用类型赋值
     * <p>
     * 属性：required，boolean类型值，默认为true，表示引用类型一定赋值成功，否则赋值错误
     * 赋值为false时，该引用类型如果赋值失败，程序不会报错，值设置为null
     * <p>
     * 位置：1.在属性定义的上面，这样使用无需定义set方法
     * 2.在set方法上面
     * <p>
     * Autowired使用自动注入，默认为ByType形式
     * <p>
     * ByName形式
     * 1.@Autowired
     * 2.@Qualifier(value="bean的id")：表示把指定的bean对象赋值给引用类型对象
     */
    @Autowired
    @Qualifier("MySchool03")
    /*@Autowired(required = false)
    @Qualifier("aaaaaa")//aaaaaa无法找到对应的引用类型*/
    private School MySchool;


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
        System.out.println("Student03的无参数构造方法");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", MySchool=" + MySchool +
                '}';
    }
}
