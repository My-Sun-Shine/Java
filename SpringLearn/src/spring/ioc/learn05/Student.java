package spring.ioc.learn05;

/**
 * @Classname Student
 * @Date 2020/4/1 11:39
 * @Created by Falling Stars
 * @Description
 */
public class Student {
    private String name;
    private int age;

    //定义引用类型
    private School mySchool;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMySchool(School mySchool) {
        this.mySchool = mySchool;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", mySchool=" + mySchool +
                '}';
    }
}
