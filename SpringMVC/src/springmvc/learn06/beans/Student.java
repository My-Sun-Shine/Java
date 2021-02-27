package springmvc.learn06.beans;

public class Student {

    //定义属性，需要和参数名一样
    private String name;
    private Integer age;


    public Student() {
        super();
        System.out.println("Student无参数构造方法");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName:" + name);
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        System.out.println("setAge:" + age);
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }


}
