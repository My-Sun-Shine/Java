package Learn.C;

/**
 * @Classname Learn16
 * @Date 2020/2/29 14:38
 * @Created by Falling Stars
 * @Description 死锁
 */
public class Learn16 {
    /**
     * 当多个线程进行同步时, 因为获得锁对象的顺序不一致,导致了线程相互等待,这就是死锁现象
     * 如何避免出现死锁??
     * 线程如果需要获得多个锁对象的话,保证锁对象的顺序一致
     *
     * @param args
     */
    public static void main(String[] args) {
        SubThread16 ta = new SubThread16();
        ta.setName("a");

        SubThread16 tb = new SubThread16();
        tb.setName("b");

        ta.start();
        tb.start();
    }
}

class SubThread16 extends Thread {

    @Override
    public void run() {
        if ("a".equals(Thread.currentThread().getName())) {
            synchronized ("资源1") {
                System.out.println("线程a获得了资源1, 还想获得 资源2");
                synchronized ("资源2") {
                    System.out.println("线程a同时获得了资源1与资源2, 可以执行");
                }
            }
        }

        if ("b".equals(Thread.currentThread().getName())) {
            synchronized ("资源2") {
                System.out.println("线程b获得了资源2, 还想获得 资源1");
                synchronized ("资源1") {
                    System.out.println("线程b同时获得了资源1与资源2, 可以执行");
                }
            }
        }
    }
}

