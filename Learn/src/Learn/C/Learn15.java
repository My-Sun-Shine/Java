package Learn.C;

/**
 * @Classname Learn15
 * @Date 2020/2/29 14:34
 * @Created by Falling Stars
 * @Description 同步实例方法，静态同步方法一样
 */
public class Learn15 {
    /**
     * * 同步方法
     * 当把整个方法体作为同步代码块时, 可以直接使用synchronized关键字修饰方法
     * 如果同步的是实例方法, 默认的锁对象是this对象
     * 如果同步的是静态方法,默认的锁对象是当前类的运行时类对象,
     *
     * @param args
     */
    public static void main(String[] args) {
        Learn15 obj = new Learn15();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.m1();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                obj.m2();
            }
        }).start();

    }

    //在m1()方法中, 把整个方法体作为同步代码块, 使用this作为锁对象
    public void m1() {
        synchronized (this) {
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "--> " + i);
            }
        }
    }

    //同步实例方法, 把整个方法体作为同步代码块, 默认的锁对象是this对象
    public synchronized void m2() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + "====> " + i);
        }
    }


}
