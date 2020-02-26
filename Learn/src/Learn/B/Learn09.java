package Learn.B;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Classname Learn09
 * @Date 2020/2/26 10:56
 * @Created by Falling Stars
 * @Description 对象数组
 */
public class Learn09 {
    public static void main(String[] args) {
        //创建班级
        MyClass class737 = new MyClass();

        //添加学生
        class737.add(new Student("lu", 22, 85));
        class737.add(new Student("huo", 18, 100));
        class737.add(new Student("wang", 23, 75));
        class737.add(new Student("wang1", 23, 89));
        class737.add(new Student("wang2", 23, 60));

        //显示班级学生信息
        class737.show();

        //判断是否存在指定姓名的同学
        System.out.println(class737.exist("lu"));
        System.out.println(class737.exist("chen"));

        class737.delete("lu");
        class737.show();

        System.out.println();
        //定义对象数组
        Student[] stuss = new Student[5];
        //给对象数组赋值
        stuss[0] = new Student("lisi", 28, 80);
        stuss[1] = new Student("wang", 18, 90);
        stuss[2] = new Student("zhao", 58, 20);
        stuss[3] = new Student("chen", 38, 40);
        stuss[4] = new Student("zhu", 8, 60);
        System.out.println(Arrays.toString(stuss));

        //对象数组排序, 可以通过sort方法的第二个参数指定一个比较规则
        //Comparator接口后面的<T>是 一个泛型, 指定比较对象的 数据类型
        Arrays.sort(stuss, new Comparator<Student>() {
            //在匿名内部类中重写接口抽象方法, 通过compare指定一个比较规则
            //如果第一个对象大返回正数就是升序排序, 如果第二个对象大返回正数就是降序排序
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getAge() - o2.getAge();        //根据年龄升序
            }
        });
        System.out.println(Arrays.toString(stuss));

        Arrays.sort(stuss, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        System.out.println(Arrays.toString(stuss));


    }
}

/**
 * 学生类
 */
class Student {
    private String name;
    private int age;
    private int score;

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getAge() {
        return age;
    }

    public Student(String name, int age, int score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }
}

/**
 * 班级类
 * 不判断学生重名的情况，默认不会重名
 */
class MyClass {
    /**
     * 学生集合
     */
    private Student[] stus;
    /**
     * 学生数量
     */
    int size;

    public MyClass() {
        stus = new Student[3];
    }

    /**
     * 添加学生
     *
     * @param s 学生对象
     */
    public void add(Student s) {
        //判断是否该进行扩容了
        if (size + 1 > stus.length) {
            stus = Arrays.copyOf(stus, stus.length * 2);
        }
        stus[size++] = s;
    }

    /**
     * 展示全部学生
     */
    public void show() {
        for (int i = 0; i < size; i++) {
            System.out.println(stus[i]);
        }
    }

    /**
     * 根据名称判断学生是否存在
     *
     * @param name 学生名称
     * @return
     */
    public boolean exist(String name) {
        for (int i = 0; i < size; i++) {
            if (stus[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据名称删除学生
     *
     * @param name 学生名称
     */
    public void delete(String name) {
        for (int i = 0; i < size; i++) {
            if (stus[i].getName().equals(name)) {
                stus[i] = stus[size - 1];
                size--;
            }
        }
    }


}


