package spring.ioc.learn03.package01;

/**
 * @Classname Student
 * @Date 2020/3/31 19:27
 * @Created by Falling Stars
 * @Description
 */
public class Student {
    private String name;
    private int age;

    //定义引用类型
    private School school;


    public Student() {
        super();
        System.out.println("student的无参数构造方法");
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

    public void setSchool(School school) {
        System.out.println("setMySchool:" + school);
        this.school = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }
}
