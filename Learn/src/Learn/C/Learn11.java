package Learn.C;

/**
 * @Classname Learn11
 * @Date 2020/2/29 13:19
 * @Created by Falling Stars
 * @Description 线程优先级
 */
public class Learn11 {
    /**
     * 线程优先级
     * 1) 每个线程都有一个优先级, 所有线程默认的优先级: 5
     * 2) 可以通过setPriority()设置线程的优先级, 优先级取值范围: 1 ~ 10
     * 3) 线程优先级越高 获得CPU执行权的机率越大
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    System.out.println(Thread.currentThread().getName() + "-->" + i);
                }
            }
        });
        t1.setPriority(1);        //设置优先级
        t1.start();
        System.out.println(t1.getPriority());

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    System.out.println(Thread.currentThread().getName() + "====>" + i);
                }
            }
        });
        t2.start();
        System.out.println(t2.getPriority());//5

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 1000; i++) {
                    System.out.println(Thread.currentThread().getName() + "********>" + i);
                }
            }
        });
        t3.setPriority(10);        //设置优先级
        t3.start();
        System.out.println(t3.getPriority());

    }
}
