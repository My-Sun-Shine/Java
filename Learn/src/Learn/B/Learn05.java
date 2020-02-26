package Learn.B;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Classname Learn05
 * @Date 2020/2/25 10:16
 * @Created by Falling Stars
 * @Description 异常处理 try...catch...finally 语句 throws语句
 */
public class Learn05 {
    /*
    如何对受检异常进行预处理
    1)	try..catch捕获处理
    2)	throws抛出处理
     */

    public static void main(String[] args) {

        try {
            /*
            FileInputStream类对FileNotFoundException异常进行受检异常处理
            路径不存在，如果不加try...catch结构，会报错
             */
            System.out.println("aaa");
            FileInputStream fis = new FileInputStream("C:\\Users\\Falling Stars\\Desktop\\a.txt");
            System.out.println("bbb");
            fis.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // 如果产生了 IO 异常, 进行处理
            // FileNotFoundException继承了IOException,捕获的异常有继承关系, 应该先捕获子异常, 再捕获父异常
            e.printStackTrace();
        } finally {
            System.out.println("finally ");
        }

        System.out.println("ccc");

        int x = m1(10);
        System.out.println(x);//10

    }


    // 定义方法时，通过throw声明抛出一个异常，这个异常就是受检异常
    // 在编译前必须对受检异常进行预处理
    // 必须在调用该方法的时候，使用throws语句声明异常，抛出给调用者
    public static void m() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("C:\\Users\\Falling Stars\\Desktop\\a.txt");
    }


    private static int m1(int i) {
        try {
            return i;
        } finally {
            i += 10;
        }

        /*
         * 程序执行到return  i;时, 并没有执行return语句, 而是把i的值保存到临时变量中
         * 执行finally子句, 把i的值变为20
         * 再执行return语句, 返回的不是i现在的值, 而是返回临时变量的值
         */
    }
}
