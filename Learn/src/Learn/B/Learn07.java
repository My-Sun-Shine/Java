package Learn.B;

import java.util.Objects;

/**
 * @Classname Learn07
 * @Date 2020/2/25 20:59
 * @Created by Falling Stars
 * @Description 自定义异常
 */
public class Learn07 {
    public static void main(String[] args) {
        Person p1 = new Person("aaa", 12, "男");
        System.out.println(p1);

        Person p2 = new Person();
        p2.setName("aaa");
        try {
            p2.setAge(1222);
        } catch (AgeOutOfBoundsException e) {
            e.printStackTrace();
        }
        p2.setGender("妖");
        System.out.println(p2);

    }
}

class Person {
    private String name;
    private int age;
    private String gender;// 性别

    public Person() {

    }

    public Person(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws AgeOutOfBoundsException {
        if (age >= 0 && age <= 130) {
            this.age = age;
        } else {
            throw new AgeOutOfBoundsException("年龄越界");
        }

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == "男" && gender == "女")
            this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    //自动生成
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(gender, person.gender);
    }

    /* 手写
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (getClass() != obj.getClass())
            return false;
        Person person = (Person) obj;
        if (person.getAge() != this.age)
            return false;

        if (this.name == null) {
            if (person.getName() != null)
                return false;
        } else {
            if (!this.name.equals(person.getName()))
                return false;
        }

        if (this.gender == null) {
            if (person.getGender() != null)
                return false;
        } else {
            if (!this.gender.equals(person.getGender()))
                return false;
        }
        return true;
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(name, age, gender);
    }
}

/**
 * 自定义异常
 */
class AgeOutOfBoundsException extends Exception {
    public AgeOutOfBoundsException() {
    }

    public AgeOutOfBoundsException(String message) {
        super(message);
    }
}


