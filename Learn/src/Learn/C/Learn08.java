package Learn.C;

/**
 * @Classname Learn08
 * @Date 2020/2/29 11:30
 * @Created by Falling Stars
 * @Description 创建线程的方式二: 实现Runnable接口
 */
public class Learn08 {
    public static void main(String[] args) {
        Runnable08 runnable08 = new Runnable08();
        Thread t = new Thread(runnable08);//传递接口实现对象
        t.start();//启动线程

        //主线程程序
        for (int i = 0; i <= 100; i++) {
            System.out.println("Main Thread ---> " + i);
        }
    }
}

/**
 * 定义一个类继承Runnable
 */
class Runnable08 implements Runnable {
    @Override
    public void run() {
        //run方法就是子线程要执行的代码
        for (int i = 0; i <= 100; i++) {
            System.out.println("Sub Thread ---> " + i);
        }
    }
}
