package Learn.A;

/**
 * @Classname Learn005
 * @Date 2020/2/19 16:11
 * @Created by Falling Stars
 * @Description 方法(定义, 重载, 递归)
 */
public class Learn05 {
    public static void main(String[] args) {
        getMain1();
        System.out.println();
        System.out.println(Sum(10));
    }


    /**
     * 求取1-100中的素数，1不是素数
     * 在大于1的自然数中，除了1和它本身以外不再有其他因数的自然数
     */
    private static void getMain1() {
        boolean isSuShu = true;
        int count = 0;
        for (int i = 2; i <= 100; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isSuShu = false;
                    break;
                }
            }
            if (isSuShu)//是素数
            {
                System.out.print(i + "\t");
                count++;
                if (count == 8) {
                    //每8个换一行
                    System.out.println();
                    count = 0;
                }
            }
            isSuShu = true;
        }
    }

    /*
    方法重载
    在同一个类中
    方法名相同
    参数列表不同：数量不同，数据类型不同，顺序不同

    方法重载和返回值类型无关
    方法重载和修饰列表无关

     */

    private static int Sum(int a, int b) {
        return a + b;
    }


    private static long Sum(long a, long b) {
        return a + b;
    }


    private static double Sum(double a, double b) {
        return a + b;
    }

    /*
    方法递归
     */
    private static int Sum(int a) {
        if (a == 1)
            return 1;
        return a + Sum(a - 1);
    }
}
