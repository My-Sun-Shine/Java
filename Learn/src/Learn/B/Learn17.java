package Learn.B;

/**
 * @Classname Learn17
 * @Date 2020/2/27 13:38
 * @Created by Falling Stars
 * @Description 字符串的不变性
 */
public class Learn17 {

    public static void main(String[] args) {
        main1();
        main2();
        main3();


    }


    /**
     * 判断字符串的相等
     */
    private static void main1() {
        String s1 = "hello";
        String s2 = "hello";

        System.out.println(s1.equals(s2));        //true
        System.out.println(s1 == s2);            //true，判断变量s1和s2中保存的值是否相等
        /*
         * 1) Java中使用双引号引起来的字符串字面量都保存在字符串常量池中
         * 		字符串常量池在JDK7前,我们说在方法区,
         * 		从JDK7开始,字符常量池存储在堆区中,现在不需要纠结存储在哪个区,只需要知道有一个字符串常量池即可
         * 2)当执行s1 = "hello"时, 系统在字符串常量池中创建一个"hello"对象 , 把该字符串对象的引用保存到s1变量中
         * 	执行s2 = "hello"时,  直接把字符串常量池中的"hello"对象的引用赋值给s2变量
         * 	s1与s2都保存字符串常量 池中同一个"hello"对象的引用
         */

        String s3 = new String("hello");
        System.out.println(s1.equals(s3));        //true
        System.out.println(s1 == s3);            //false
        System.out.println(s1 == s3.intern());    //true
        //intern()方法返回该字符串对应的字符串常量
    }

    /**
     * String对象创建
     */
    private static void main2() {
        String s1 = "hello";
        s1 = "world";
        /*
         * 在上面两行中"hello"与"world"才是String对象,   s1是String类型的变量
         * s1 = "hello"执行时, 把字符串常量池中"hello"对象的引用保存到s1中
         * 当执行s1 = "world"时, 是在字符串常量池中新添加一个"world"对象, 把该对象的引用保存到s1中
         * 	并不是把"hello"对象变为"world"对象, 而是在常量池中又创建了一个新的"world"对象
         */

        String s2 = "hello";
        s2 = s2 + s1;// 新创建一个对象

        //以下两行共创建了多少个String对象?  2个,  一个常量 :"hello",  一个new出来的对象
        String s3 = "hehe";
        String s4 = new String("hehe");

        //以下两行共创建了多少个String对象?  1个,
        s3 = "haha";
        s4 = "ha" + "ha";    //javac编译器会对字符串字面量的连接进行优化, 即把 "ha"+"ha"优化为"haha"

        //以下两行共创建了多少个String对象?  1个,
        s3 = "ying666";
        s4 = "ying" + 666;        //javac编译器会对字符串字面量与整数字面量的连接进行优化

        //以下两行共创建了多少个String对象? 4个, 三个字面量: "xi","xixi","xixixi",  一个连接后的对象
        s3 = "xi";
        s4 = s3 + "xi" + "xixi" + "xixixi";//前面有变量，不进行优化

        //如果频繁进行字符串连接时, 不建议使用String字符串,而是使用StringBuilder/StringBuffer
        String text = "";
        for (int i = 1; i <= 10; i++) {
            text += i;            //每次连接时,都会创建一个新的String对象
        }
        System.out.println(text);        //12345678910

    }

    /**
     * 创建可变字符串对象
     */
    private static void main3() {
        //创建可变字符串对象
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= 10; i++) {
            sb.append(i);
        }
        System.out.println(sb);

        StringBuffer sb2 = new StringBuffer();
        sb2.append("dh");
    }
}
