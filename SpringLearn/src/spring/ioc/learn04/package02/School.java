package spring.ioc.learn04.package02;

/**
 * @Classname School
 * @Date 2020/3/31 20:03
 * @Created by Falling Stars
 * @Description
 */
public class School {
    private String name;
    private String address;


    public School() {
        super();
        System.out.println("School的无参数构造方法");
    }

    public School(String name, String address) {
        System.out.println("School的有参数构造方法");
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "School [name=" + name + ", address=" + address + "]";
    }
}
