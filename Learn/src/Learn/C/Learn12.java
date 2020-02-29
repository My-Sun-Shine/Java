package Learn.C;

/**
 * @Classname Learn12
 * @Date 2020/2/29 13:28
 * @Created by Falling Stars
 * @Description 线程调度
 */
public class Learn12 {
    public static void main(String[] args) {
        //m1();
        //m2();//线程睡眠
        //m3();//线程合并
        //m4();//线程终止
        //m5();//线程终止
        //m6();//线程中断
        m7();//线程让步
    }

    /**
     * 返回线程的状态
     */
    private static void m1() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + " : " + i);
                }
            }
        });
        System.out.println("11:" + t1.getState());
        t1.start();
        System.out.println("22:" + t1.getState());

    }

    /**
     * 线程睡眠 sleep方法，单位毫秒，状态转为WAITING等待状态
     */
    private static void m2() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " ---> " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * 线程合并，状态由运行状态转为WAITING等待状态
     */
    private static void m3() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " ===> " + i);
                }
            }
        }, "线程t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " > " + i);
                    if (i == 30) {
                        try {
                            t1.join();
                            //加入t1线程,  当前线程(t2)转为等待状态,等到t1线程执行完后再转为就绪状态
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "线程t2");

        t1.start();
        t2.start();
    }

    /**
     * 终止线程，状态转变为TERMINATED终止状态
     */
    private static void m4() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 300; i++) {
                    System.out.println(Thread.currentThread().getName() + " ===> " + i);
                }
            }
        }, "线程T1");
        t1.start();

        //main线程
        for (int i = 0; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " > " + i);
        }
        //main线程结束之后，终止t1
        t1.stop();
    }

    /**
     * 终止线程
     * 现在一般让run()运行结束来终止一个线程
     * 可以添加一个线程运行标志/结束标志
     */
    private static void m5() {
        SubThread12 t1 = new SubThread12();
        t1.start();

        //main线程
        for (int i = 0; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " > " + i);
        }

        //main线程结束之后，终止t1
        t1.isrunning = false;

    }

    /**
     * * 线程中断
     * t1.interrupt() ; 		中断t1线程
     * 一般情况下, 是把处于睡眠/等待中的线程给中断
     */
    private static void m6() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " ===> " + i);
                    if (i == 50) {
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            //如果处于睡眠的线程被中断,就会抛出InterruptedException异常
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, "线程T1");
        t1.start();

        //main线程
        for (int i = 0; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + " > " + i);
        }
        //处于睡眠/等待中的线程给中断
        t1.interrupt();

    }

    /**
     * 线程让步,Thread.yield();  线程让步就是把运行中的线程转换为RUNNABLE就绪状态
     */
    private static void m7() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " ===> " + i);
                    if (i % 10 == 0) {
                        Thread.yield();
                    }
                }
            }
        }, "线程t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " > " + i);
                }
            }
        }, "线程t2");

        t1.start();
        t2.start();
    }

}

class SubThread12 extends Thread {
    boolean isrunning = true;

    @Override
    public void run() {
        for (int i = 0; i <= 300 && isrunning; i++) {
            System.out.println(Thread.currentThread().getName() + " ------> " + i);
        }
    }
}

