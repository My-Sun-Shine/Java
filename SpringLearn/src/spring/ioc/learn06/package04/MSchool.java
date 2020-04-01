package spring.ioc.learn06.package04;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname MSchool
 * @Date 2020/4/1 22:28
 * @Created by Falling Stars
 * @Description
 */
@Component("MSchool")
public class MSchool {
    @Value("中学")
    private String name;
    @Value("在上海")
    private String address;


    public MSchool() {
        super();
        System.out.println("MSchool的无参数构造方法");
    }

    @Override
    public String toString() {
        return "MSchool{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
