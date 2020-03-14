package Learn.E;

/**
 * @Classname Learn01
 * @Date 2020/3/14 11:24
 * @Created by Falling Stars
 * @Description 单例模式
 */
public class Learn01 {

    public static void main(String[] args) {
        Test_1.m();
        Test_1 test_11 = Test_1.getTest_1();
        Test_1 test_12 = Test_1.getTest_1();
        System.out.println(test_11);
        System.out.println(test_12);
        Test_1.m();

        System.out.println("----------------------");

        Test_2.m();
    }

}


/**
 * 单例设计模式
 */
class Test_1 {
    private Test_1() {
    }

    private static Test_1 test_1 = null;

    public static Test_1 getTest_1() {
        if (test_1 == null) {
            test_1 = new Test_1();
        }
        return test_1;
    }

    public static void m() {
        System.out.println("m--Test_1");
    }
}

/**
 * 单例设计模式
 */
class Test_2 {
    private Test_2() {
    }

    private static Test_2 test_2 = new Test_2();

    public static Test_2 getTest_2() {
        return test_2;
    }

    public static void m() {
        System.out.println("m--Test_2");
    }
}