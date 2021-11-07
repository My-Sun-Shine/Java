package utils;

import java.util.BitSet;

/**
 * @Classname Eratosthenes
 * @Date 2021/11/7 22:15
 * @Created by FallingStars
 * @Description 埃拉托斯特尼筛法
 */
public class Eratosthenes {

    public static void main(String[] s) {
        int n = 2000000;
        long start = System.currentTimeMillis();
        int count = getCount(n);
        long end = System.currentTimeMillis();
        System.out.println(count + " primes");
        System.out.println((end - start) + " milliseconds");
    }

    /**
     * 获取2-n中质数的数量
     * <p>
     * 质数又称素数。指在一个大于1的自然数中，除了1和此整数自身外，没法被其他自然数整除的数
     * <p>
     * 厄拉多塞是一位古希腊数学家，他在寻找素数时，采用了一种与众不同的方法：先将2－N的各数放入表中，然后在2的上面画一个圆圈，然后划去2的其他倍数；
     * 第一个既未画圈又没有被划去的数是3，将它画圈，再划去3的其他倍数；
     * 现在既未画圈又没有被划去的第一个数是5，将它画圈，并划去5的其他倍数……依次类推，一直到所有小于或等于N的各数都画了圈或划去为止
     * <p>
     * 而其实迭代系数i不需要遍历到n-1为止，只需到√(n-1)即可。反证法：
     * <p>
     * 如果n-1不是质数，那么n-1可以化解成两个整数因子相乘，n-1=d1×d2。
     * 如果d1和d2均大于√(n-1)，则有：n-1＝d1×d2 > √(n-1)×√(n-1)＝n-1
     * 则n-1必有因子d1或d2小于等于√(n-1)，从而n-1可以被小于或等于√(n-1)的某整数的遍历到。
     * 小于n-1的整数如果是质数必然会被遍历到。
     * <p>
     * 如果N是合数，则一定存在大于1小于N的整数d1和d2，使得N=d1×d2。
     * 如果d1和d2均大于√N，则有：N＝d1×d2>√N×√N＝N。而这是不可能的，
     * 所以，d1和d2中必有一个小于或等于√N。
     *
     * @param n
     * @return 质数的数量
     */
    private static int getCount(int n) {
        BitSet bitSet = new BitSet(n + 1);
        int count = 0;
        int i;
        for (i = 2; i <= n; i++)
            bitSet.set(i);//设置为true
        i = 2;
        while (i * i <= n) {
            if (bitSet.get(i)) {
                count++;
                int k = 2 * i;
                while (k <= n) {
                    bitSet.clear(k);//设置为false
                    k += i;
                }
            }
            i++;
        }
        while (i <= n) {
            if (bitSet.get(i)) count++;
            i++;
        }
        return count;
    }


}
