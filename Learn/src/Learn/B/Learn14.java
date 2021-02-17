package Learn.B;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @Classname Learn14
 * @Date 2020/2/27 11:17
 * @Created by Falling Stars
 * @Description 字符串的基本操作
 */
public class Learn14 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        //1) 逐个字符打印字符串
        String text = "hello,world";
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
        }
        System.out.println();

        //2) 字符串大小的比较, 根据第一个不相等字符的码值相减
        System.out.println("hello".compareTo("hehe"));    //4 这是l-h
        System.out.println("张三".compareTo("李四"));        //-2094 这是张-李

        //3)字符串判断是否相等,equals()
        System.out.println("hello".equals(text));        //false

        //4)format("格式串", 数据) 对数据进行格式化
        /* 格式符
         * 		%s		字符串
         * 		%d		整数
         * 		%b		布尔
         * 		%f		小数
         * 在格式串中,非格式符原样输出, 格式符使用数据代替, 格式符也称点位符
         */
        System.out.println(String.format("name:%s,age:%d,isMarried:%b,salary:%f", "aaa", 28, false, 666.6));
        //								   name:aaa,age:28,isMarried:false,salary:666.600000

        //5)getBytes()返回字符串在当前默认编码下对应的字节数组,,
        byte[] bytes = "hello,中国".getBytes();            //默认编码UTF8下对应的字节数组
        System.out.println(Arrays.toString(bytes));
        //[104, 101, 108, 108, 111, 44, -28, -72, -83, -27, -101, -67]

        bytes = "hello,中国".getBytes("GBK");            //在指定GBK编码下对应的字节数组
        System.out.println(Arrays.toString(bytes));
        //[104, 101, 108, 108, 111, 44, -42, -48, -71, -6]

        //6)indexOf(), lastIndexOf(),   subString()
        String path = "d:/Chapter04/src/com/chapter04/string/demo01/Test02.java";
        //把路径的文件夹, 文件名, 扩展名取出来
        int lastSlashIndex = path.lastIndexOf('/');
        int dotIndex = path.indexOf(".");
        String folder = path.substring(0, lastSlashIndex);  //  d:/Chapter04/src/com/chapter04/string/demo01/
        String name = path.substring(lastSlashIndex + 1, dotIndex); //  Test02
        String suffix = path.substring(dotIndex + 1); // java
        System.out.println(folder);
        System.out.println(name);
        System.out.println(suffix);

        //7) toCharArray() 把字符串转换为字符数组
        char[] contents = "hello".toCharArray();
        System.out.println(Arrays.toString(contents));  //[h, e, l, l, o]

        //8) toLowerCase() 大写转小写,  toUpperCase() 小写转大写
        String str = text.toUpperCase();
        System.out.println(str);//HELLO,WORLD

        str = "HELLO,WORLD".toLowerCase();    //
        System.out.println(str);      //hello,world

        //9)trim()去掉前后的空白字符
        text = "             hehe              heihei               ";
        System.out.println("begin" + text.trim() + "end"); //beginhehe              heiheiend

        //10) valueOf() 把其他类型转换为字符串
        int num = 123;
        text = "aa" + num;        //字符串与基本类型连接, 先把基本类型转换为字符串再连接
        System.out.println(text);//aa123

        text = String.valueOf(num);//把其他类型的数据转换为字符串
        System.out.println(text);//123
    }
}
