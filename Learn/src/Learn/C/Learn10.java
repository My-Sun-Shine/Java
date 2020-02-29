package Learn.C;

/**
 * @Classname Learn10
 * @Date 2020/2/29 11:57
 * @Created by Falling Stars
 * @Description 线程基础操作
 */
public class Learn10 {
    public static void main(String[] args) {
        //m1();
        //m2();
    }

    /**
     * Thread.currentThread()		返回当前线程
     * t1.setName("线程名称") 		设置线程名称
     * t1.getName()				返回线程名称
     */
    private static void m1() {
        Thread t1 = new Thread(new Runnable() {
            //在匿名内部类中重写接口的抽象方法
            @Override
            public void run() {
                //run()方法体中的代码就是 线程要执行的代码
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                }
            }
        });
        t1.setName("t1");        //设置线程名称
        t1.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "--> " + i);
                }
            }
        }).start();

        //当前线程是main线程
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " ====> " + i);
        }
    }

    /**
     * 守护线程
     * 不能独立运行, 当JVM中只有守护线程时, JVM就退出
     * t1.setDaemon(true)		设置守护线程
     */
    private static void m2() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                }
            }
        }, "线程名称a");

        thread.setDaemon(true);        //设置守护线程,

        thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                }
            }
        }).start();
    }

}
