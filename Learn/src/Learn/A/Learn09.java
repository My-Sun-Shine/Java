package Learn.A;

/**
 * @Classname Learn009
 * @Date 2020/2/21 16:19
 * @Created by Falling Stars
 * @Description 静态代码块和实例代码块
 */
public class Learn09 {

    /*
    静态代码块在类加载的时候执行，并且只执行一次，
    可以在一个类中编写多个，并遵循自上而下的顺序依次加载
     */
    static {
        System.out.println("类加载-0");
    }


    static {
        System.out.println("类加载-1");
    }

    /*
    实例代码块：在对象创建的时候执行，也可以编写多个，按照自上而下的方式执行
    在构造方法执行之前执行，构造方法每执行一次，实例代码块对应执行一次
     */

    {
        System.out.println("a");
    }


    {
        System.out.println("b");
    }

    public static void main(String[] args) {
        System.out.println("Main begin");
        new Learn09();
    }
}
