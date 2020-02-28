package Learn.B;

/**
 * @Classname Learn18
 * @Date 2020/2/27 14:15
 * @Created by Falling Stars
 * @Description 包装类对象
 */
public class Learn18 {
    public static void main(String[] args) {
        m1();
        System.out.println();
        m2();
        System.out.println();
        m3();

    }

    /**
     * 创建包装类对象
     */
    private static void m1() {
        //1) 都可以根据基本类型创建包装类对象
        Boolean b1 = new Boolean(true);
        System.out.println(b1);
        Character c1 = new Character('A');
        System.out.println(c1);
        Integer i1 = new Integer(123);
        System.out.println(i1);
        Double d1 = new Double(3.14);
        System.out.println(d1);

        //2) 除了Character外,都可以根据字符串创建包装类对象
        Integer i2 = new Integer("789");
        System.out.println(i2);
        Double d2 = new Double("78.456");
        System.out.println(d2);
        //注意, 字符串格式要符合对应的数字格式
        //i2 = new Integer("abc"); 		//java.lang.NumberFormatException
        d2 = new Double("213e+2");        //科学计数法
        System.out.println(d2);//21300
        //d2 = new Double("2323dsafaf");   //java.lang.NumberFormatException

        Boolean b2 = new Boolean("true");
        System.out.println(b2);
        //Boolean可以把非"true"的字符串都转换为false
        b2 = new Boolean("asdfasfdasfasfasfda");
        System.out.println(b2);    //false
    }

    /**
     * 包装类对象的使用
     */
    private static void m2() {
        Integer i1 = new Integer(123);

        //1)从Number继承的方法, byteValue(), shortValue(), intValue(), floatValue(), doubleValue()
        byte bb = i1.byteValue();
        System.out.println(bb);
        double dd = i1.doubleValue();
        System.out.println(dd);

        //2) 包装类都实现了Comparable接口,重写comapareTo()方法,可以比较两个包装类对象的大小
        //在包装类中都有一个value字段保存对应基本类型的数据, 在比较大小时,根据value字段大小比较
        Integer i2 = new Integer("789");
        System.out.println(i1.compareTo(i2));      //-1

        //3) 重写equals()方法, 根据value字段的值判断
        System.out.println(i1.equals(i2));        //false

        //4)包装类有parseXXX( text ) 可以把字符串转换为基本类型数据
        int num = Integer.parseInt("369");
        double dd2 = Double.parseDouble("3.14");
        //注意, 参数字符串要符合数字格式
        //num = Integer.parseInt("3.14");  // java.lang.NumberFormatException

        //5)重写了toString()方法
        String text = i1.toString();
        System.out.println(text); //123

        //6) valueOf() 可以把基本类型/String字符串转换为包装类对象
        Integer i3 = Integer.valueOf(147);
        i3 = Integer.valueOf("258");
        System.out.println(i3); //258
    }

    /**
     * 装箱:
     * 把基本类型转换为包装类对象
     * 拆箱:
     * 把包装类对象转换为基本类型
     * <p>
     * 在Java中, 装箱与拆箱可以自动进行
     */
    private static void m3() {
        Integer i1 = 123;        //装箱
        int num = i1;            //自动拆箱

        Integer i2 = 456;        //根据基本类型数据456创建一个包装类对象, 把该对象的引用赋值给i2
        Integer i3 = 456;        //创建了一个新包装类对象
        System.out.println(i2.equals(i3));    //true
        System.out.println(i2 == i3);        //false,比较的是对象地址

        i2 = 123;        //自动装箱
        i3 = 123;
        System.out.println(i2 == i3);        //true
        /*
         * Java认为-128 ~ 127 之间的整数是使用最频繁的, 所以该范围的整数装箱后的包装类对象存储在共享池中
         * 在执行 i2 = 123时, 根据123创建Integer对象,存储 在共享池中
         * 执行 i3 = 123时, 直接把共享池中的包装类对象的引用返回赋值给i3
         */

        Long gg1 = 66L;
        Long gg2 = 66L;
        System.out.println(gg1 == gg2);        //true
    }
}
