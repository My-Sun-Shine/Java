package Learn.C;

/**
 * @Classname Learn18
 * @Date 2020/2/29 15:09
 * @Created by Falling Stars
 * @Description 练习
 */
public class Learn18 {
    /**
     * wait()/notify()必须在同步代码块中由锁对象调用
     * <p>
     * 创建两个线程,一个线程打印100个数, 第二个线程打印10个数实现交替打印
     * 线程1 : 1
     * 线程1 : 2
     * ...	...
     * 线程1 : 100
     * 线程2 : 101
     * ... ...
     * 线程2 : 110
     * 线程1 : 111
     * ...	...
     * 线程1 : 210
     * 线程2 : 211
     * ...	...
     * 线程2 : 220
     */

    private int num = 1;
    private boolean a = false;
    private boolean b = true;
    private int num1 = 1;
    private int num2 = 1;
    private static int maxnum=500;

    public static void main(String[] args) {
        Learn18 learn18 = new Learn18();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= maxnum; i++) {
                    learn18.print100();
                }
                return;
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= maxnum; i++) {
                    learn18.print10();
                }
                return;
            }
        }).start();

    }

    /**
     * 打印100个数
     */
    private synchronized void print100() {
        if (a) {
            try {
                //System.out.println("100----------------》" + num);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " : " + num);
        num++;
        num1++;
        if (num1 > 100) {
            a = true;
            b = false;
            num1 = 1;
            System.out.println("----------------------------");
            this.notify();
        }

    }

    /**
     * 打印10个数
     */
    private synchronized void print10() {
        while (b) {
            try {
                //System.out.println("10----------------》" + num);
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //奇数就打印
        System.out.println(Thread.currentThread().getName() + " : " + num);
        //数加1变为偶数
        num++;
        num2++;
        if (num2 > 10) {
            b = true;
            a = false;
            num2 = 1;
            System.out.println("----------------------------");
            this.notify();
        }

    }
}