package Learn.B;

import java.util.Scanner;

/**
 * @Classname Learn02
 * @Date 2020/2/24 16:07
 * @Created by Falling Stars
 * @Description 键盘上输入一个日期, 打印该日期对应这一年的第几天
 */
public class Learn02 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("请输入一个日期");
        int year = s.nextInt();
        int month = s.nextInt();
        int day = s.nextInt();
        System.out.println(year + "年" + month + "月" + day + "日，是" + year + "年的第" + sumDay(year, month, day) + "天");

    }

    private static int sumDay(int year, int month, int day) {
        if (month > 12 || month <= 0) {
            return 0;
        }
        int[] days = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            days[1]++;
        }
        if (day > days[month - 1]) {
            return 0;
        }
        int sumNum = 0;
        for (int i = 0; i < month - 1; i++) {
            sumNum += days[i];
        }
        sumNum += day;
        return sumNum;
    }
}
