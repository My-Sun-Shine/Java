package Learn.B;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @Classname Learn20
 * @Date 2020/2/27 22:29
 * @Created by Falling Stars
 * @Description List集合
 */
public class Learn20 {
    public static void main(String[] args) {
        m1();
        m2();
        m3();
        /*
         * ArrayList与Vector
         * 1) 底层都是数组
         * 2) 默认初始化大小: 10
         * 3) ArrayList扩容: 1.5倍大小 ;  Vector扩容: 2倍大小扩容
         * 4) Vector是线程安全的, ArrayList不是线程安全的
         * 		虽然Vector是线程安全的, 但是现在开发多线程程序,使用CopyOnWriterArrayList
         */
    }


    /**
     * List集合操作
     */
    private static void m1() {
        //1)创建List集合, List是一个接口, 需要赋值实现类对象
        //创建一个List集合,存储String字符串, 通过泛型指定存储元素的数据类型
        List<String> list = new ArrayList<>();

        //2)添加元素
        list.add("jj");
        list.add("mm");
        list.add("mm");
        list.add("dd");

        //3)直接打印, 打印的顺序与添加的顺序一样, 可以有重复的数据
        System.out.println(list);   //[jj, mm, mm, dd]

        //4)判断
        System.out.println(list.isEmpty());//false
        System.out.println(list.size());//4
        System.out.println(list.contains("jj"));//true
        System.out.println(list.contains("xx"));//false

        //5)删除第一个匹配的元素
        list.remove("jj");
        System.out.println(list);  //[mm, mm, dd]

        //6)迭代遍历
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            System.out.print(string + "\t");
        }
        System.out.println();

        //7)迭代删除 mm
        iterator = list.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            if ("mm".equals(string)) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }

    /**
     * List集合操作
     */
    private static void m2() {
        //创建一个存储String字符串的List集合
        List<String> list = new ArrayList<>();
        //添加元素
        list.add("jj");
        list.add("jj");
        list.add("dd");
        list.add("mm");
        list.add("mm");
        list.add("dd");
        System.out.println(list);        //[jj, jj, dd, mm, mm, dd]

        //1)在指定的位置添加元素, 注意索引值不能越界[0, list.size() ]
        list.add(0, "xx");
        System.out.println(list);        //[xx, jj, jj, dd, mm, mm, dd]

        list.add(list.size(), "yy");
        System.out.println(list);        //[xx, jj, jj, dd, mm, mm, dd, yy]

        //2)返回元素第一次出现的索引值
        System.out.println(list.indexOf("jj"));//1
        System.out.println(list.lastIndexOf("jj"));//2

        //3)删除指定位置的元素
        list.remove(3);
        System.out.println(list);        //[xx, jj, jj, mm, mm, dd, yy]

        //4)返回指定位置的元素
        System.out.println(list.get(0));//xx
        System.out.println(list.get(list.size() - 1));//yy

        //通过循环返回所有的元素
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + "\t");
        }

        System.out.println();
    }


    /**
     * 自定义对象List集合
     */
    private static void m3() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("aa", 12, "男"));
        list.add(new Person("bb", 25, "男"));
        list.add(new Person("cc", 10, "女"));
        list.add(new Person("dd", 21, "女"));
        Person ee = new Person("ee", 20, "男");
        list.add(ee);
        System.out.println(list);

        System.out.println(list.contains(ee));//true
        Person bb = new Person("bb", 25, "男");
        System.out.println(list.contains(bb));//true
        System.out.println(list.contains(new Person(null, 25, "男")));//false

        /*
         * 在List集合中, 判断集合中是否包含指定的对象时,即调用contains()方法时,需要调用对象的equals()方法
         * 如果List集合中存储的是自定义类型的对象, 自定义类型中需要重写equals()
         */

        //删除指定的Person对象,在List集合remove()删除时,先找到这个对象再删除, 在找相同的对象时,需要调用对象的equals()方法
        list.remove(new Person("bb", 25, "男"));
        System.out.println(list);

        System.out.println();
        //	迭代遍历List集合中所有的Person对象
        Iterator<Person> iterator = list.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            System.out.println(person);
        }
    }

}
