package Learn.C;

/**
 * @Classname Learn07
 * @Date 2020/2/29 11:14
 * @Created by Falling Stars
 * @Description 创建线程的方式一继承Thread
 */
public class Learn07 {
    /**
     * 运行程序, 每次执行的结果可能都不一样
     * 可以简单的理解为, main线程和t1线程在抢CPU执行权, 抢到CPU执行权的线程在执行
     * 之前单核CPU中, CPU在某一时刻只能执行一个任务, 因为CPU执行速度非常快, 可以快速的在各个线程进行切换, 对于用户来说,感觉是在同时执行
     * 现在多核CPU中, 不同的厂商,不同型号,各个内核执行的算法是不一样的
     * 可以把键盘上104个按钮理解为键盘程序有104个线程, 每个人有10个手指头可以理解为CPU有10个物理内核
     *
     * @param args
     */
    public static void main(String[] args) {
        //创建线程对象
        SubThread07 subThread = new SubThread07();
        subThread.start();//启动线程，开启的新线程会自动执行run方法
        //subThread.run();//就是普通实例方法的调用，不会开启新线程

        //主线程程序
        for (int i = 0; i <= 100; i++) {
            System.out.println("Main Thread ---> " + i);
        }

    }


}

/**
 * 定义一个类继承Thread
 */
class SubThread07 extends Thread {
    //重写run方法
    @Override
    public void run() {
        //run方法就是子线程要执行的代码
        for (int i = 0; i <= 100; i++) {
            System.out.println("Sub Thread ---> " + i);
        }
    }
}
