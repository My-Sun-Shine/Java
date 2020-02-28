package Learn.B;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Classname Learn16
 * @Date 2020/2/27 12:05
 * @Created by Falling Stars
 * @Description String类的练习
 */
public class Learn16 {
    public static void main(String[] args) {
        m1();
        m2();
        m3();
    }


    /**
     * 从键盘上输入一个身份证号,  判断输入的字符串是否符合身份证的格式,
     * 根据身份证号判断这个人的出生日期, 判断性别. 102030199910201234
     * 1 02030 19991020 12 3 4
     */
    private static void m1() {
        System.out.println("请输入身份证号：");
        Scanner s = new Scanner(System.in);
        String id = s.next();
        while (!id.matches("[1-9]\\d{16}[0-9X]")) {
            System.out.println("身份证号格式不正确, 请重新输入：");
            id = s.next();
        }
        //从身份证号中把年月日与性别的数字取出来
        String year = id.substring(6, 10);
        String month = id.substring(10, 12);
        String day = id.substring(12, 14);
        System.out.println(year + "年" + month + "月" + day + "日出生");
        String sex = id.substring(16, 17);
        //判断性别
        int sexNum = Integer.parseInt(sex);
        if (sexNum % 2 == 0) {
            System.out.println("性别：女");
        } else {
            System.out.println("性别：男");
        }

    }

    /**
     * 把字符串”bjpwernode   is.the  best”中的每个单词的首字母大写
     */
    private static void m2() {
        String text = "Bjpwernode   is.the  best";
        //如果某个字母的前一个字符如果不是英文字母,该字母就是单词的首字母
        char prev = ' ';        //保存前一个字符

        StringBuilder sb = new StringBuilder();

        //遍历字符串中的每个字符,如果不是单词的首字母就正常输出, 如果是单词的首字母转大写后再输出
        for (int i = 0; i < text.length(); i++) {
            char cc = text.charAt(i);

            //isLetter()判断是不是字母
            //如果cc是字母, cc的前一个字符不是字母, 那么cc就是单词首字母
            if (Character.isLetter(cc) && !Character.isLetter(prev)) {
                //cc是单词的首字母,如果cc是小写字母就转换为大写字母
                if (Character.isLowerCase(cc)) {
                    //首字母是小写
                    sb.append(Character.toUpperCase(cc));
                } else {
                    //首字母不是小写
                    sb.append(cc);
                }
            } else {
                //不是首字母
                sb.append(cc);
            }

            prev = cc;        //当前这个字符就是下个字符的前一个
        }

        System.out.println(sb);
    }

    /**
     * 有字符串”01,li,98;02,wang,85;03,chen,74”存储学生的学号,姓名与成绩.
     * 要求:
     * 把字符串的学生信息分离出来
     * 创建学生对象
     * 把学生对象保存到数组中
     */
    private static void m3() {
        String text = "01,li,98;02,wang,85;03,chen,74";
        //分离字符串中的信息
        String[] words = text.split("[,;]");

        System.out.println(Arrays.toString(words));
        //[01, li, 98, 02, wang, 85, 03, chen, 74]
    }
}


