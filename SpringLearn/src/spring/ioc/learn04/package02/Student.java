package spring.ioc.learn04.package02;

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
    private School mySchool;


    public Student() {
        super();
        System.out.println("student的无参数构造方法");
    }

    public Student(String name, int age, School school) {
        System.out.println("student的有参数构造方法");
        this.name = name;
        this.age = age;
        this.mySchool = school;
    }

    public void setName(String name) {
        System.out.println("setName:" + name);
        this.name = name;
    }

    public void setAge(int age) {
        System.out.println("setAge:" + age);
        this.age = age;
    }

    public void setMySchool(School school) {
        System.out.println("setSchool:" + school);
        this.mySchool = school;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + mySchool +
                '}';
    }
}
