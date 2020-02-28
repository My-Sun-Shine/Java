package Learn.B;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @Classname Learn23
 * @Date 2020/2/28 13:09
 * @Created by Falling Stars
 * @Description Map集合操作
 */
public class Learn23 {
    public static void main(String[] args) {
        m1();
        m2();
    }

    /**
     * Map的基本操作
     */
    private static void m1() {
        //1)创建一个map集合,存储<员工姓名,员工工资> , Map是一个接口,需要赋值实现类对象
        //通过泛型指定键,值的数据类型
        Map<String, Integer> map = new HashMap<>();

        //2)添加数据
        map.put("feifei", 5000);
        map.put("yang", 6000);
        map.put("dubin", 8000);
        map.put("ming", 9000);

        //3)直接打印
        System.out.println(map);  //{dubin=8000, ming=9000, yang=6000, feifei=5000}

        //4) 添加<键,值>对时, 键已存在, 使用新值替换了原来的值, Map中的键不能重复
        map.put("yang", 4000);
        System.out.println(map);  //{dubin=8000, ming=9000, yang=4000, feifei=5000}

        //5) 判断
        System.out.println(map.isEmpty());              //false
        System.out.println(map.size());                 //4
        System.out.println(map.containsKey("feifei"));  //true
        System.out.println(map.containsKey("yong"));    //false
        System.out.println(map.containsValue(10000));   //false
        System.out.println(map.containsValue(8000));    //true
        System.out.println(map.get("dubin"));           //8000 返回键对应的值
        System.out.println(map.get("yong"));            //null  键不存在

        //6)删除
        map.remove("yong");    //删除与指定的键匹配的<键,值>对
        System.out.println(map);    //{dubin=8000, ming=90 00, yang=4000, feifei=5000}
        map.remove("dubin");
        System.out.println(map);    //{ming=9000, yang=4000, feifei=5000}
        map.remove("yang", 6000);   //在map中删除 <yang,6000>对
        System.out.println(map);    //{ming=9000, yang=4000, feifei=5000}

        //7)返回所有键的集合
        Set<String> keySet = map.keySet();
        System.out.println(keySet);    //[ming, yang, feifei]

        //8)返回所有值的集合
        Collection<Integer> values = map.values();
        System.out.println(values);    //[9000, 4000, 5000]

        //9)返回所有键,值对的集合
        Set<Entry<String, Integer>> entrySet = map.entrySet();
        for (Entry<String, Integer> entry : entrySet) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }

    /**
     * 统计字符串中字符出现的次数
     */
    private static void m2() {
        String text = "adfasfafdasfdasxzcvzdfsafdzcbvzfdsafdsfavcxzv";
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        System.out.println(map);
        Set<Entry<Character, Integer>> entries = map.entrySet();
        for (Entry<Character, Integer> entrie : entries) {
            System.out.println(entrie);
        }

    }

}
