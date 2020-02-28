package Learn.B;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @Classname Learn19
 * @Date 2020/2/27 20:54
 * @Created by Falling Stars
 * @Description Collection集合
 */
public class Learn19 {
    public static void main(String[] args) {
        m1();
        m2();

    }

    /**
     * 创建Collection集合
     */
    private static void m1() {
        //1)创建Collection集合, Collection是一个接口,需要赋值实现类对象
        //一般情况下, 在集合中存储的元素都是同一类型的元素,在创建集合时, 可以通过泛型指定集合 中元素的数据类型
        //如创建一个存储String字符串的集合, 通过泛型指定集合的元素是String类型
        Collection<String> collection = new ArrayList<>();

        //2)向集合中添加元素
        //注意,在创建集合中通过泛型指定集合存储元素的类型是String, 只能添加String字符串
        collection.add("gg");
        collection.add("jj");
        collection.add("mm");
        collection.add("dd");
        collection.add("mm");
        //泛型的好处就是在编译时可以进行数据类型的检查,
        //collection.add( 456 ); 		//如果添加的数据不是String类型, 语法错误

        //3)直接打印, collection引用的是ArrayList对象, 打印时,会调用ArrayList对象的toString()
        System.out.println(collection);    //[gg, jj, mm, dd, mm]

        //4)判断
        System.out.println(collection.size());//5
        System.out.println(collection.isEmpty());//false
        System.out.println(collection.contains("gg"));//true
        System.out.println(collection.contains("mm"));//true

        //5)删除第一个匹配的元素
        collection.remove("mm");
        System.out.println(collection);        //[gg, jj, dd, mm]

        //6) addAll()
        Collection<String> collection22 = new ArrayList<>();
        collection22.addAll(collection);        //把collection集合中的所有元素添加到collection22集合中

        //7)清空
        collection.clear();
        System.out.println(collection);//[]

        System.out.println(collection22);//[gg, jj, dd, mm]

    }

    /**
     * 迭代遍历集合
     */
    private static void m2() {
        Collection<String> collection = new ArrayList<>();
        collection.add("gg");
        collection.add("jj");
        collection.add("mm");
        collection.add("dd");
        collection.add("dd");
        collection.add("mm");
        System.out.println(collection);    //[gg, jj, mm, dd, mm]


        //迭代遍历集合中的内容
        //h获取集合的迭代器
        Iterator<String> iterator = collection.iterator();
        /* 在iterator迭代器中,有一个游标, 开始游标指向第一个元素的前面
         * iterator迭代器,有一个hasNext()方法,判断是否还有没访问的元素
         * 迭代器有next()方法, 返回下个元素, 把游标下移
         */
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();
        //while循环完后,这个迭代器的游标已经指向最后了, 这个iterator迭代器就不能再使用了
        //如果还想迭代的话, 需要重新调用iterator()方法,获得一个新的迭代器对象

        //Collection集合的remove( o )可以删除第一个匹配的元素
        collection.remove("jj");
        System.out.println( collection );  //[jj, dd, dd, mm, mm]

        //需求:删除所有的dd,
        //遍历集合中的所有元素,如果该元素与dd相同就删除
        iterator = collection.iterator(); 			//重新获得迭代器
        while (iterator.hasNext()) {
            String string =  iterator.next();
            if ( "dd".equals(string) ) {
                //collection.remove("dd");
                // 在迭代过程中,不能使用Collection集合的add()/remove()添加/删除元素
                iterator.remove(); 			//迭代删除
            }
        }
        System.out.println(collection);  		//[jj, mm, mm]
    }
}
