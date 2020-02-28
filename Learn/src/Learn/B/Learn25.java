package Learn.B;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @Classname Learn25
 * @Date 2020/2/28 15:21
 * @Created by Falling Stars
 * @Description TreeMap集合操作
 */
public class Learn25 {
    public static void main(String[] args) {
        //创建TreeMap,存储<员工姓名,工资> , 指定Comparator比较器,根据姓名降序排序
        TreeMap<String, Integer> treeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        treeMap.put("lisi", 4000);
        treeMap.put("wang", 5000);
        treeMap.put("chen", 7000);
        treeMap.put("zhao", 2000);
        System.out.println(treeMap);

        //创建TreeMap时, 如果没有指定Comparator比较器, 要求键实现Comparable接口
        TreeMap<String, Integer> treeMap2 = new TreeMap<>();
        treeMap2.putAll(treeMap);
        System.out.println(treeMap2);

    }
}
