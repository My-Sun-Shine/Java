package Learn.B;

import java.util.*;

/**
 * @Classname Learn22
 * @Date 2020/2/28 11:54
 * @Created by Falling Stars
 * @Description set集合，无序不可重复 HashSet和TreeSet
 */
public class Learn22 {
    public static void main(String[] args) {
        m1();//HashSet集合
        m2();//TreeSet集合
    }

    /**
     * HashSet集合
     */
    private static void m1() {
        //1)创建Set集合,  Set是一个接口,需要赋值实现类对象
        Set<String> set = new HashSet<>();

        //2)添加元素
        set.add("666666");
        set.add("hehehehe");
        set.add("yingyingying");
        set.add("xixixixi");

        //3)打印, 存储顺序可能与添加的顺序不一样
        System.out.println(set);  //[hehehehe, xixixixi, 666666, yingyingying]

        //4)添加重复的数据
        set.add("666666");
        set.add("hehehehe");
        System.out.println(set);  //[hehehehe, xixixixi, 666666, yingyingying]

        //5)判断
        System.out.println(set.size());             //4
        System.out.println(set.isEmpty());          //false
        System.out.println(set.contains("666666")); //true

        //6)迭代
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String string = iterator.next();
            System.out.println(string);
        }

        //7)删除
        set.remove("666666");
        System.out.println(set);    //[hehehehe, xixixixi, yingyingying]
    }

    /**
     * TreeSet
     * 1) TreeSet底层是TreeMap
     * 2) 向TreeSet添加元素就是把该元素作为键添加到了TreeMap中
     * 3) TreeSet就是TreeMap键的集合
     * 4) TreeSet类实现了SortedSet接口, 可以根据元素自然排序, 要求集合中的元素必须是可比较的
     * (1) 在定义TreeSet时, 通过构造方法指定Comparator比较器
     * (2) 如果没有指定Comparator比较器, 要求集合中元素的类实现Comparable接口
     */
    private static void m2() {
         //1)创建TreeSet集合, 存储String字符串, 要求按字符串降序排序, 在创建TreeSet时指定Comparator比较器
        TreeSet<String> treeSet1 = new TreeSet<>(new Comparator<String>() {
            //在匿名内部类中重写接口抽象方法
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);        //按字符串降序排序
            }
        });

        treeSet1.add("aaa");
        treeSet1.add("bbb");
        treeSet1.add("ggg");
        treeSet1.add("zzz");
        System.out.println(treeSet1);   //[zzz, ggg, bbb, aaa]

        //2)如果创建TreeSet时,没有指定Comparator比较器,元素的类需要实现Comparable接口
        //TreeSet集合存储String字符串, String类实现了Comparable接口
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.addAll(treeSet1);        //把treeSet1中的所有元素添加到treeSet中
        System.out.println(treeSet);    //[aaa, bbb, ggg, zzz]
    }
}
