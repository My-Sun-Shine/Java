package spring.ioc.learn06.package04;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname School
 * @Date 2020/4/1 21:58
 * @Created by Falling Stars
 * @Description
 */
@Component("MySchool04")
public class School {

    @Value("清华大学")
    private String name;
    @Value("在北京的海淀")
    private String address;


    public School() {
        super();
        System.out.println("School04的无参数构造方法");
    }


    @Override
    public String toString() {
        return "School [name=" + name + ", address=" + address + "]";
    }
}
