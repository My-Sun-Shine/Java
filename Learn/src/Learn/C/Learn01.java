package Learn.C;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.Arrays;

/**
 * @Classname Learn01
 * @Date 2020/2/28 17:15
 * @Created by Falling Stars
 * @Description FileInputStream字节流读取数据
 */
public class Learn01 {
    public static void main(String[] args) {
        m1();
        System.out.println();
        m2();
    }

    /**
     * FileInputStream读取一个字节
     */
    private static void m1() {
        //1)在当前程序与操作的文件之间建立流通道
        //通过FileInputStream构造方法的参数指定要操作的文件
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:\\Users\\Falling Stars\\Desktop\\text.txt");

            //当前文件内容:abc

            //2)读取文件内容, read()可以从文件中读取一个字节, 并把读取到的字节返回, 讲到文件末尾返回-1
            int cc = fis.read();
            while (cc != -1) {
                //把读到的字节cc转换为字符打印到屏幕上, 因为当前文件中只有英文字符,一个英文字符占1字节
                System.out.print((char) cc);
                //继续读取,把读到的字节保存到cc中
                cc = fis.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //3) 关闭流通道
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 以字节数组进行读取
     */
    private static void m2() {
        //1)建立流通道
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("C:\\Users\\Falling Stars\\Desktop\\text.txt");

            //文件内容:abcdefabcdef

            byte[] bytes = new byte[8];            //定义长度为8的字节数组,

            //2)读取文件内容,保存到字节数组中, 返回读到的字节数,如果读到文件末尾返回-1
            //一次读取8个字节
            int len = fis.read(bytes);        //从文件中读取字节保存到bytes数组中, len返回值是读到的字节数组

            while (len != -1) {
                System.out.print(new String(bytes, 0, len));
                len = fis.read(bytes);
            }

            /*
            //以下代码是正常读取
            System.out.println(len);        //8, 从文件中读取了abcdefab这8个字节
            System.out.println(Arrays.toString(bytes));
            //[97, 98, 99, 100, 101, 102, 97, 98]
            //把读到的字节数组转换为字符串打印
            System.out.println(new String(bytes, 0, len));

            //新读取的四个字节将会覆盖的数组的前四个字节，所以数组的前四个字节发生变化，后面四个字节没有变化
            len = fis.read(bytes);            //读取文件中cdef这4个字节
            System.out.println(len);        //4
            System.out.println(Arrays.toString(bytes));
            //[99, 100, 101, 102, 101, 102, 97, 98]
            //把读到的4个字节转换为字符串打印
            System.out.println(new String(bytes, 0, len));
            */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //3) 关闭流通道
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
