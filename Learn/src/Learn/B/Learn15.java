package Learn.B;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Classname Learn15
 * @Date 2020/2/27 12:05
 * @Created by Falling Stars
 * @Description String与正则表达式
 */
public class Learn15 {
    /**
     * String类中与正则 表达式相关的操作
     * 正则表达式就是一个模式串, 用于验证字符串是否匹配指定的格式
     * [abc]			匹配abc中的一个
     * .				任意字符
     * \d				数字
     * \s				空白符
     * \w				单词字符 [a-zA-Z_0-9]
     * X?				匹配0次或1次
     * X*				匹配任意次
     * X+				匹配至少1次
     * X{n}			    匹配n次
     * X{n,}			匹配至少n次
     * X{n,m}			匹配至少n次,最多m次
     */
    public static void main(String[] args) {
        //1)验证用户输入的是否符合邮箱格式
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入邮箱:");
        String email = sc.next();
        //使用matches方法判断用户的输入是否匹配指定的格式
        //邮箱格式  xxxx@163.com,  邮箱名至少5位
        if ("[a-zA-Z1-9]\\w{4,}@\\w{2,}\\.(com|cn|net)".matches(email)) {
            System.out.println("格式正确");
        } else {
            System.out.println("邮箱格式不正确");
        }

        //2) 替换
        String text = "bjpower1234isthebest";
        text = text.replaceAll("\\d", "*");        //把字符串的数字使用*替换
        System.out.println(text);//bjpower****isthebest

        //3) 分隔, 把字符串中的每个单词都分离出来
        text = "bjpowernode  is  the             best!  everyone love it.";
        String[] word = text.split("[\\s,.!]+");
        for (String string : word) {
            System.out.println(string);
        }

        //4) 把URL请求中的参数分离出来, 请求参数在问号后面
        String html = "https://www.gome.com.cn/?cmpid=dh_360_yx_mz&smtid=539077028z299wz1cymfz2o2z0z";
        //4.1 先把?后面的参数取出来
        int index = html.indexOf("?");
        String parameter = html.substring(index + 1);
        System.out.println(parameter);  //cmpid=dh_360_yx_mz&smtid=539077028z299wz1cymfz2o2z0z
        String[] params = parameter.split("[=&]");
        System.out.println(Arrays.toString(params));//按照=，&进行分割
    }
}
