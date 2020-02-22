package Learn.A;

/**
 * @Classname Learn007
 * @Date 2020/2/20 14:59
 * @Created by Falling Stars
 * @Description 类的调用
 */
public class Learn07 {
    public static void main(String[] args) {
        Learn06 learn06 = new Learn06();
        learn06.setName("詹三");
        System.out.println(learn06.getName());

        learn06.setAge(100);
        System.out.println(learn06.getAge());
        learn06.setAge(190);
        System.out.println(learn06.getAge());
    }
}
