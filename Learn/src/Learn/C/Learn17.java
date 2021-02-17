package Learn.C;

/**
 * @Classname Learn17
 * @Date 2020/2/29 14:57
 * @Created by Falling Stars
 * @Description 练习
 */
public class Learn17 {
    /**
     * wait()/notify()必须在同步代码块中由锁对象调用
     * notify/notifyAll() 的执行只是唤醒沉睡的线程，而不会立即释放锁，锁的释放要看代码块的具体执行情况
     * notify和wait的顺序不能错，如果A线程先执行notify方法，B线程在执行wait方法，那么B线程是无法被唤醒的。
     * <p>
     * 创建两个线程,实现奇数偶数的交替打印
     * 线程1 : 1
     * 线程2 : 2
     * 线程1 : 3
     * 线程2 : 4
     * 线程1 : 5
     */

    private int num = 0;

    public static void main(String[] args) {
        Learn17 learn17 = new Learn17();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    learn17.printOdd();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    learn17.printEven();
                }
            }
        }).start();
    }

    /**
     * 打印偶数
     */
    private synchronized void printEven() {
        if (num % 2 != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //偶数就打印
        System.out.println(Thread.currentThread().getName() + " : " + num);
        //数加1变为奇数
        num++;
        //通知打印奇数
        this.notify();
    }

    /**
     * 打印奇数
     */
    private synchronized void printOdd() {
        //当数是偶数时就等待
        while (num % 2 == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //奇数就打印
        System.out.println(Thread.currentThread().getName() + " : " + num);
        //数加1变为偶数
        num++;
        //通知打印偶数
        this.notify();
    }
}
