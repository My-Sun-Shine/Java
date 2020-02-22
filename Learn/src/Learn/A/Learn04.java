package Learn.A;

import java.util.Random;
import java.util.Scanner;

/**
 * @Classname Learn004
 * @Date 2020/2/18 16:17
 * @Created by Falling Stars
 * @Description 控制语句
 */
public class Learn04 {
    public static void main(String[] args) {
        /*
        选择语句：if, if..else, switch
        循环语句：for, while, do...while
        控制循环语句：break, continue
         */

        // getMain();

        /*
        switch语句的执行原理
        匹配成功的分支执行，分支中最后有break语句，整个switch语句终止
        匹配成功的分支执行，分支中最后没有break语句，直接进入下一个分支执行不进行匹配，称为case穿透现象
        所有分支都没有匹配成功，当有default的语句的话，会执行default语句

        switch后面和case语句后面只能是int或者string类型数据
        当然byte、short、char也可以直接写到switch和case语句后面，因为它们可以自动转换为int类型

        JDK6 switch和case语句只能检测int类型
        JDK7及以上 switch和case语句可以检测int和string类型

        case可以合并
        switch(i){
            case 1:case 2:case 3:break;
        }
         */

        // switch语句
        char c = 'A';
        switch (c) {
        }
        char cc = 97;
        switch (cc) {
        }

        Random random = new Random();
        int num = random.nextInt(8);

        String str = "";
        switch (num) {
            case 1:
            case 0://case合并
                str = "星期一";
                break;
            case 2:
                str = "星期二";
                break;
            case 3:
                str = "星期三";
                break;
            case 4:
                str = "星期四";
                break;
            case 5:
                str = "星期五";
                break;
            case 6:
                str = "星期六";
                break;
            case 7:
                str = "星期日";
            case 8://case穿透
                System.out.println("星期日");
                break;

            default:
                break;

        }
        System.out.println(str);

        c = 'A';
        //c = 65;
        switch (c) {
            case 'A':
                System.out.println("高级");
                break;
            case 'B':
                System.out.println("中级");
                break;
            case 'C':
                System.out.println("初级");
                break;
            default:
                System.out.println("error");
        }

        // for循环
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "*" + j + "=" + (i * j) + "\t");
            }
            System.out.println();
        }

        /*
        break：终止距离最近的for，while，do..while循环，可以跳出switch语句
        还可以设置终止某个特定循环，需要给循环起名

        continue：跳出本次循环，直接进入下一次循环，也可以指定for循环名称
         */
        for1:
        for (int i = 0; i <= 10; i++) {
            for2:
            for (int j = 0; j <= 10; j++) {
                if (j == 5)
                    break for1;//终止for1循环
                System.out.println("j==>" + j);
            }

        }


    }

    /**
     * if语句
     */
    private static void getMain() {
        // 创建键盘扫描器对象
        Scanner s = new Scanner(System.in);
        System.out.print("请输入你的成绩：");
        //调用Scanner的next()方法开始接受用户键盘输入
        int num = s.nextInt();
        String str;
        if (num < 0 || num > 100) {
            str = "error";
        } else if (num >= 90) {
            str = "A";
        } else if (num >= 80) {
            str = "B";
        } else if (num >= 60) {
            str = "C";
        } else {
            str = "D";
        }
        System.out.println(str);


    }
}
