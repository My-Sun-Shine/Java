package Learn.A;

/**
 * @Classname Learn002
 * @Date 2020/2/14 18:50
 * @Created by Falling Stars
 * @Description 八种数据类型
 */
public class Learn02 {
    public static void main(String[] args) {

        System.out.println(10);//10
        System.out.println(010);//8 八进制以0开头，0后面的10是八进制形式
        System.out.println(0x10);//16 十六进制以0x开头，0x后面的10是十六进制形式

        //123这个整数型字面量是int类型，i变量声明的时候也是int类型，int类型的123赋值int类型变量i，不存在类型转换
        int i = 123;
        System.out.println(i);

        //456这个整数型字面量是int类型，x变量声明的时候也是long类型，int类型的456赋值long类型变量x，存在类型转换
        long x = 456;
        System.out.println(x);

        //long z=2147483648; // 这样写报错，因为在java中数字型字面量默认为int类型，但是2147483648超过int类型的范围
        long z = 2147483648L;// 后面添加L，表示这是long类型
        System.out.println(z);

        /*
        * 计算机中存储的是补码
        * 正数：原码 = 反码 = 补码
        * 负数：原码取反 = 反码（符号位不变）；反码 + 1 = 补码（符号位上的进位舍弃）
        * */
        // 原始数据:00000000 00000000 00000000 10000000
        // 转换数据:截取后八位10000000 结果为负数，所以补码需要转换为原码（取反之后-1）
        // 就是将int类型数据128强制性转换为byte类型数据-128
        System.out.println((byte) 128);
        //00000000 00000000 00000000 11001000
        System.out.println((byte) 200);//11001000 -->10111000 = -(8+16+32)
        System.out.println((byte) 64);//64

        double d = 5.1;
        // float f = 5.1; // 报错，因为5.1为double类型字面量，无法自动转换为float
        float f1 = (float) 5.1; // 强制类型转换
        float f2 = 5.1f;
        System.out.println(d);
        System.out.println(f1);
        System.out.println(f2);

        int f = 10 / 3;//3
        double d1 = 10 / 3;// 3.0
        double d2 = 10.0 / 3;//3.3333333333333335
        System.out.println(f);
        System.out.println(d1);
        System.out.println(d2);

        long g = 10;

        // byte h=(byte)(int)g/3; // 报错，g转换为int，在转换为byte，最后是byte类型的g和3进行运算，
        // 运算结果为int类型，将int类型数据赋值给byte类型出现精度损失问题

        byte h = (byte) (int) (g / 3);// 没问题，先运算得到long类型数据，再强制性转换
        System.out.println(h);

        char c = 'a';
        System.out.println(c);//a
        System.out.println((byte) c);//97
        int c1 = c + 100;
        System.out.println(c1);//197


    }
}
