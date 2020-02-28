package Learn.B;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @Classname Learn13
 * @Date 2020/2/26 15:30
 * @Created by Falling Stars
 * @Description String类
 */
public class Learn13 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //1) 直接赋值字符串字面量
        String s1 = "hello";

        //2)调用String()无参构造
        String s2 = new String();
        System.out.println(s2 == null);        //false
        /*
         * new运算符在堆中创建一个String对象, 把该对象的引用赋值给s2
         * s2是一个空串,长度为0的字符串, 相当于""
         */

        //3)String(byte[]) 根据字节数组创建字符串
        byte[] bytes = {65, 66, 67, 97, 98, 99};
        //把bytes字节数组中所有字节根据当前编码转换为字符串
        String s3 = new String(bytes);// ABCabc
        System.out.println(s3);

        s3 = new String(bytes, 3, 2);        //把bytes字节数组从3索引值开始的2个字节 转换为字符串
        System.out.println(s3);            //ab

        //字符串有getBytes()方法可以返回该字符串对应的字节数组
        //返回字符串在当前默认编码下对应的字节数组, UTF8编码一个汉字对应三个字节,
        bytes = "hello,中国".getBytes();
        System.out.println(Arrays.toString(bytes));
        //[104, 101, 108, 108, 111, 44, -28, -72, -83, -27, -101, -67]

        s3 = new String(bytes, 0, 6);
        System.out.println(s3); //hello,

        //把字节数组转换为字符串时,还可以指定编码格式
        //String s3 = new String(bytes);
        s3 = new String(bytes, "UTF-8");
        System.out.println(s3); //hello,中国

        s3 = new String(bytes, "GBK");        //在GBK中,一个汉字占2个字节
        System.out.println(s3); //hello,涓浗

        bytes = "hello,中国".getBytes("GBK");  //把字符串以指定的编码转换为字节数组
        System.out.println(Arrays.toString(bytes));
        //[104, 101, 108, 108, 111, 44, -74, -81, -63, -90, -67, -38, -75, -29]

        s3 = new String(bytes, "GBK");        //在GBK中,一个汉字占2个字节
        System.out.println(s3); //hello,中国

        //4) String(char[]) 根据字符数组转换为字符串
        char[] contents = {'a', 'A', '中', '国'};
        String s4 = new String(contents);    //把字符数组中的所有字符转换为字符串
        System.out.println(s4);
        s4 = new String(contents, 0, 2);
        System.out.println(s4);

        //5) String(String)
        String s5 = new String(s4);
        System.out.println(s5);
        System.out.println(s4 == s5);        //false
        System.out.println(s4.equals(s5));  //true

    }
}
