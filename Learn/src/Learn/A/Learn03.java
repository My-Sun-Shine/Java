package Learn.A;

/**
 * @Classname Learn003
 * @Date 2020/2/17 13:17
 * @Created by Falling Stars
 * @Description 运算符
 */
public class Learn03 {
    public static void main(String[] args) {
        int i = 10;
        int j = 3;
        System.out.println(i + j);//13
        System.out.println(i - j);//7
        System.out.println(i * j);//30
        System.out.println(i / j);//3
        System.out.println(i % j);//1

        int k = 10;
        k++;
        System.out.println(k);//11
        System.out.println(k++);//11
        System.out.println(k);//12
        System.out.println(++k);//13
        System.out.println(k);//13

        int a = 10;
        int b = 10;
        System.out.println(a > b);//false
        System.out.println(a >= b);//true
        System.out.println(a < b);//false
        System.out.println(a <= b);//true
        System.out.println(a == b);//true
        System.out.println(a != b);//false

        //逻辑与 &
        //逻辑或 |
        //逻辑非 !
        //逻辑异或 ^ 两边结果不一样，t返回rue
        //短路与 &&
        //短路或 ||

        System.out.println(true ^ false);//true
        System.out.println(false ^ false);//false

        int x = 10;
        int y = 8;
        System.out.println(x < y && x++ > y);//false,因为&&前面的表达式为false，不会执行&&后面的操作
        System.out.println(x);//10
        System.out.println(x > y && x++ > y);//true
        System.out.println(x);//11

        x = 10;
        y = 8;
        System.out.println(x < y & x++ > y);//false
        System.out.println(x);//11
        System.out.println(x > y & x++ > y);//true
        System.out.println(x);//12

        x = 10;
        y = 8;
        System.out.println(x > y || x-- > y);//true 因为||前面的表达式为true，不会执行后面的操作
        System.out.println(x);//10
        System.out.println(x < y || x-- > y);//true
        System.out.println(x);//9

        x = 10;
        y = 8;
        System.out.println(x > y | x-- > y);//true
        System.out.println(x);//9
        System.out.println(x < y | x-- > y);//true
        System.out.println(x);//8

        byte bb = 10;
        // bb+5是int类型，bb变量的数据类型是byte，需要强制性转换
        // bb = bb + 5;
        bb = (byte) (bb + 5);
        System.out.println(bb);
        bb = 10;
        bb += 5;//等同bb=(byte)(bb+5)
        System.out.println(bb);

        /*
        扩展运算符 (+=、-=、*=、/=、%=) 不改变结果类型
         */
        int k1 = 10;
        k1 += 1;//等同k=(int)(k1+1)
        System.out.println(k1);
        long l = 10L;
        int x1 = 20;
        l += x1;//等同l=(long)(l+x1)
        System.out.println(l);

        /*
        数字 + 数字 = 数字
        数字 + 字符串 = 字符串
        字符串 + 数字 = 字符串
        字符串 + 字符串 = 字符串
         */
        System.out.println(20 + 10 + 30);//60
        System.out.println(20 + 10 + "30");//3030 自左向右的顺序依次运算
        System.out.println(20 + (10 + "30"));//201030
        System.out.println("10 + 20 =" + 10 + 20);//10 + 20 =1020

        /*
        布尔表达式 ? 表达式1 : 表达式2
        如果布尔表达式为true，则返回表达式1
        如果布尔表达式为false，则返回表达式2
         */
        boolean sex = false;
        System.out.println(sex ? "男" : "女");


    }
}
