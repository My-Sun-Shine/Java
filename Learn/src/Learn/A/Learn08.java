package Learn.A;

/**
 * @Classname Learn008
 * @Date 2020/2/21 12:18
 * @Created by Falling Stars
 * @Description 静态方法和实例方法的相互调用
 */
public class Learn08 {

    int i = 0;

    public static void main(String[] args) {
        Learn08.method1();
        method1();

        Learn08 learn08 = new Learn08();
        learn08.method2();

        System.out.println();
        // 带有static可以使用类名的形式进行访问，也可以使用实例的方式
        // 但是使用实例去访问和实例对象没有关系
        learn08.method1();//这样执行和对象没有关系
        learn08 = null;
        learn08.method1();
    }

    private static void doSome() {
        System.out.println("doSome");
    }

    private void doOther() {
        System.out.println("doOther");
    }

    private static void method1() {
        System.out.println("------method1 start-----------");
        Learn08.doSome();
        doSome();

        System.out.println("---------");
        Learn08 learn08 = new Learn08();
        learn08.doOther();

        System.out.println("---------");
        System.out.println(learn08.i);
        System.out.println("------method1 end-----------");
    }

    private void method2() {
        System.out.println("------method2 start-----------");
        Learn08.doSome();
        doSome();

        System.out.println("---------");
        Learn08 learn08 = new Learn08();
        learn08.doOther();
        this.doOther();
        doOther();

        System.out.println("---------");
        System.out.println(this.i);
        System.out.println(i);
        System.out.println("------method2 end-----------");
    }
}


