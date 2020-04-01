package spring.ioc.learn06.package01;

import org.springframework.stereotype.Component;

/**
 * @Classname Student
 * @Date 2020/4/1 21:30
 * @Created by Falling Stars
 * @Description 注解 @Component: 创建类的对象，等同于<bean>的作用
 * <p>
 * <p>
 * 注解 @Component: 创建类的对象，等同于<bean>的作用
 * 属性： value ,表示对象的名称，也就是<bean>的id
 * 位置：在类的上面，表示spring创建此类的对象，调用类的无参数构造方法，
 * 创建的对象默认是单例的。
 * <p>
 * 注解@Component(value="myStudent")等同于 <bean id="myStudent" class="spring.ioc.learn06.package01.Student" />
 * <p>
 * <p>
 * 和@Component功能相同的其他注解
 * 1.@Repository:放在Dao层的实现类上，创建Dao对象
 * 2.@Service:放在Service层的实现类上面，创建Service对象
 * 3.@Controller:放在处理器类的上面，创建处理器对象的（简单理解Servlet）
 */
//@Component(value = "Student")

@Component("MyStudent")//可以省略value

//不使用@Component指定对象名称，默认是对象首字符的小写student
public class Student {
    private String name;
    private int age;


    public Student() {
        super();
        System.out.println("Student的无参数构造方法");
    }

    public void setName(String name) {
        System.out.println("setName:" + name);
        this.name = name;
    }

    public void setAge(int age) {
        System.out.println("setAge:" + age);
        this.age = age;
    }

    //自定义set方法
    public void setSex(String sex) {
        System.out.println("setSex:" + sex.toUpperCase());
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}
