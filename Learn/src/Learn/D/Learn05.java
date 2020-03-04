package Learn.D;

/**
 * @Classname Learn05
 * @Date 2020/3/4 20:39
 * @Created by Falling Stars
 * @Description Annotation注解类型
 */

/**
 * 作用：标注的作用，标记的作用，标记给“其它机制”参考的。
 * <p>
 * java.lang包下自带了3个注释类型：
 * java.lang.Override
 * java.lang.Deprecated
 * java.lang.SuppressWarnings
 */
@SuppressWarnings("all")
public class Learn05 {
    /**
     * @param args
     */
    public static void main(String[] args) {

    }


    // 重写父类方法

    /**
     * java.lang.Override，这个注解类型专门用来标注方法,只能用来标注方法
     * 被标注的方法必须是重写父类的方法，
     * 该方法上有这个标记之后，编译器开始检测该方法，当该方法不是重写父类的方法，编译器会报错
     *
     * @return
     */
    @Override
    public String toString() {
        return "";
    }

    /**
     * java.lang.Deprecated：该注解标注的元素已过时
     */
    @Deprecated
    public static final String str = "";

    @Deprecated
    public static void m1() {
    }

    @Deprecated
    public String str1 = "";

    /**
     * java.lang.SuppressWarnings注解：该注解的作用是去除警告信息
     *
     * @注释类型名(属性名=属性值,属性名=属性值,属性名=属性值....)
     */
    @SuppressWarnings(value = "all")
    public String str2 = "";

    @SuppressWarnings(value = {"all"})
    public String str3;
}

@Deprecated
class Learn050 {
    @SuppressWarnings(value = "all")
    public String str2 = "";

    @SuppressWarnings(value = {"all"})
    public String str3;
}