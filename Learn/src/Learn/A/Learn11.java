package Learn.A;

/**
 * @Classname Learn011
 * @Date 2020/2/21 22:47
 * @Created by Falling Stars
 * @Description final关键字
 */
public class Learn11 {

    //final 修饰的实例变量，必须手动赋值(定义变量的时候赋值，构造方法中赋值)，不能采用系统默认值

    final int age = 10;

    final int num;

    public Learn11() {
        this.num = 10;
    }

    public static void main(String[] args) {
        sum(1, 2);
    }

    // final修饰的方法形参，不能在方法中再次被赋值
    public static void sum(int x, final int y) {
        x = 0;
        //y = 0;//y不能重新被赋值
    }


}


