package Learn.C;

/**
 * @Classname Learn14
 * @Date 2020/2/29 14:23
 * @Created by Falling Stars
 * @Description 同步代码块
 */
public class Learn14 {
    public static void main(String[] args) {
        //m1();
        //m2();
        m3();
    }

    private static void m1() {
        PrintNum1 printNum = new PrintNum1();

        //分别创建两个线程调用m1()/m2(), 实现同步
        //即一个线程在调用m1()方法期间, 不允许第二个线程执行m2();  或者一个线程在执行m2()期间,不允许另一个线程执行m1()

        //创建一个线程调用m1()
        new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.m1();
            }
        }).start();

        //创建一个线程调用m2()
        new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.m2();
            }
        }).start();
    }


    private static void m2() {
        PrintNum2 printNum = new PrintNum2();

        //创建一个线程调用m1()
        new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.m1();
            }
        }).start();

        //创建一个线程调用m2()
        new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.m2();
                //下面的使用方法，不能同步，因为没有使用同一个锁对象
                //new PrintNum2().m2();
            }
        }).start();
    }

    private static void m3() {
        PrintNum3 printNum = new PrintNum3();

        //创建一个线程调用m1()
        new Thread(new Runnable() {
            @Override
            public void run() {
                printNum.m1();
            }
        }).start();

        //创建一个线程调用m2()
        new Thread(new Runnable() {
            @Override
            public void run() {
                //printNum.m2();
                //下面的使用方法，使用字节码文件作为锁，可以同步
                new PrintNum2().m2();
            }
        }).start();
    }

}


/**
 * * 线程要同步, 必须使用同 一个锁对象
 * 1) 可以使用一个常量 对象作为锁对象
 * 2) 有时也会使用this对象作为锁对象
 * 3) 有时也会使用当前类的运行时类对象作为锁对象
 */
class PrintNum1 {

    private static final Object OBJ = new Object();

    public void m1() {
        synchronized (OBJ) {        //使用一个常量对象作为锁对象
            //有人也称它为类锁
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
            }
        }
    }

    public void m2() {
        synchronized (OBJ) {
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
            }
        }
    }
}

class PrintNum2 {

    public void m1() {

        synchronized (this) {    //使用this对象作为锁对象, this对象就是调用该方法的对象
            //有人也称它为类锁
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
            }
        }
    }

    public void m2() {
        synchronized (this) {
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
            }
        }
    }
}

class PrintNum3 {


    public void m1() {
        //使用当前类的运行时类对象作为锁对象, 可以简单的理解为把当前类的字节码文件作为锁对象
        synchronized (PrintNum3.class) {
            //有人也称它为类锁
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
            }
        }
    }

    public void m2() {
        synchronized (PrintNum3.class) {
            for (int i = 1; i <= 100; i++) {
                System.out.println(Thread.currentThread().getName() + "-->" + i);
            }
        }
    }
}