package Learn.A;

/**
 * @Classname Learn006
 * @Date 2020/2/20 14:52
 * @Created by Falling Stars
 * @Description 类
 */
public class Learn06 {
    /**
     * 构造方法
     * 无参数的构造方法默认存在
     * 但是有参数的构造方法存在的时候，如果不显式定义无参构造方法，则就无法调用无参构造函数
     */
    public Learn06() {
        // 在构造方法中使用this调用另一个构造方法
        // 而且必须是第一行
        this("aa", 23);
    }

    public Learn06(int age) {
        this.age = age;
    }

    public Learn06(String name) {
        this.name = name;
    }

    public Learn06(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            System.out.println("年龄不合法");
            return;
        }
        this.age = age;
    }


}


