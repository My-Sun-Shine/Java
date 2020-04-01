package spring.ioc.learn06.package04;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Classname Student
 * @Date 2020/4/1 21:30
 * @Created by Falling Stars
 * @Description 使用注解给简单类型赋值
 */

@Component("MyStudent04")//可以省略value
public class Student {

    @Value(value = "詹三")
    private String name;

    @Value("25")
    private int age;

    /**
     * 引用类型
     * 注解@Resource：来自JDK中的注解，是给引用类型赋值的，支持byName,byType
     * 默认使用byName
     * <p>
     * 位置：1.在属性定义上，无需定义set方法
     * 2.在set方法上
     */
    //虽然默认使用ByName赋值，但是如果ByName赋值失败，则使用ByType赋值
    //下面是使用ByType赋值
    @Resource
    private School MySchool;


    //使用ByName属性赋值，属性name的值为bean的id
    @Resource(name = "MSchool")
    private MSchool MyMSchool;

    public Student() {
        super();
        System.out.println("Student04的无参数构造方法");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", MySchool=" + MySchool +
                ", MyMSchool=" + MyMSchool +
                '}';
    }
}
