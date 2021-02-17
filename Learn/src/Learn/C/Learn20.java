package Learn.C;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 * @Classname Learn20
 * @Date 2020/2/29 16:24
 * @Created by Falling Stars
 * @Description 反射
 */
public class Learn20 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        m1();
        m2();
        m3();
        m4();
    }

    /**
     * 获取class对象
     *
     * @throws ClassNotFoundException
     */
    private static void m1() throws ClassNotFoundException {
        //1)每个类都有class属性
        Class<?> class1 = String.class;
        //2)每个对象都有getClass()
        Class<?> class2 = "hello".getClass();
        //3) Class.forName(完整类名)
        Class<?> class3 = Class.forName("java.lang.String");

        //创建Class对象,可以简单的理解为获得字节码文件
        System.out.println(class1 == class2); //true
        System.out.println(class2 == class3); //true


        Class<?> class4 = int.class;
        Class<?> class5 = Integer.class;
        System.out.println(class4 == class5);//false
        Class<?> class6 = Integer.TYPE;
        System.out.println(class4 == class6); //true

        // Class.forName()把的指定的类加载到内存中
        //通过这种方式创建Class对象,没有执行Learn20_0类的静态代码块, 说明Learn20_0没有加载内存
        //Class<?> class7 = Learn20_0.class;
        Class<?> class8 = Class.forName("Learn.C.Learn20_0");  //执行了 静态代码块,说明Learn20_0类加载了内存
    }

    /**
     * 获取类的信息
     */
    private static void m2() {
        //Class<?> class1 = String.class;
        Class<?> class1 = ArrayList.class;

        StringBuilder sb = new StringBuilder();

        // 类的修饰符
        int mod = class1.getModifiers();        //返回值是一个int整数
        sb.append(Modifier.toString(mod));
        //java.lang.reflect.Modifier类的静态方法toString(int)可以把一个整数转换为字符串

        // 类名
        sb.append(" class ");
        //sb.append( class1.getName() );  		//完整类名
        sb.append(class1.getSimpleName());    //简易类名

        // 父类
        Class<?> superclass = class1.getSuperclass();
        //判断父类是否为Object, 如果父类不是Object类就显示
        if (Object.class != superclass) {
            sb.append(" extends ");
            sb.append(superclass.getSimpleName());
        }

        //接口,如果类型是基本类型,或者没有实现接口, getInterfaces()返回一个长度为0的数组
        Class<?>[] interfaces = class1.getInterfaces();
        if (interfaces.length > 0) {
            sb.append(" implements ");
            //遍历interfaces接口数组
            for (int i = 0; i < interfaces.length; i++) {
                sb.append(interfaces[i].getSimpleName());
                //如果当前接口不是最后一个接口, 使用逗号分隔
                if (i < interfaces.length - 1) {
                    sb.append(",");
                }
            }
        }
        //打印输出
        System.out.println(sb);

    }

    /**
     * * 演示通过反射访问字段
     * class1.getField(name)			反射指定名称的公共字段
     * class1.getDeclaredField(name)	反射指定名称的字段(包括公共的,非公共的)
     * <p>
     * field.set(实例名, 字段值)			设置字段的值
     * field.get(实例名)					返回字段的值
     * <p>
     * class1.newInstance()			    创建实例
     * <p>
     * field.setAccessible(true)		设置字段的可访问性
     */
    private static void m3() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        //1) 创建Class对象
        Class<?> class1 = Learn20_0.class;

        //2) 反射公共字段xx
        Field xxField = class1.getField("xx");

        //3) 创建Learn20_0类的对象(实例)
        //通过class1对象创建实例, 会调用类的无参构造方法, 如果该类没有无参构造方法,出现异常
        Object p1 = class1.newInstance();        //相当于  new Learn20_0()

        //4)设置xx字段的值
        xxField.set(p1, 101);     //相当于 p1.setXX(101)

        //5)返回字段的值
        System.out.println(xxField.get(p1));//101

        //6)反射私有字段yy
        Field yyField = class1.getDeclaredField("yy");

        //7)可以设置字段的可访问性
        yyField.setAccessible(true);
        yyField.set(p1, 202);
        System.out.println(yyField.get(p1));//202

        //8)反射公共的静态字段QQ
        Field qqField = class1.getField("QQ");
        //访问静态字段时,在对象名的位置传递null
        qqField.set(null, 606);
        System.out.println(qqField.get(null));//606
    }

    /**
     * 通过反射技术调用方法
     * <p>
     * class1.getMethod(方法名, 方法的形参列表)    返回指定方法签名的方法
     * <p>
     * method.invoke( 实例名,  方法的实参 ) 		方法调用
     * <p>
     * class1.getConstructor( 构造方法的参数列表 )	反射构造方法
     * constructor.newInstance( 构造方法的实参 );	通过构造方法创建实例
     */
    private static void m4() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //创建Class对象
        Class<?> class1 = String.class;

        //获取length()方法
        Method lengthM = class1.getMethod("length", null);

        //反射构造方法 String (char [] )
        Constructor<?> constructor = class1.getConstructor(char[].class);

        //通过构造方法创建实例
        char[] contents = "hello,world".toCharArray();
        Object s1 = constructor.newInstance(contents);    //new String(contents)

        //5)调用length方法
        Object len = lengthM.invoke(s1, null);
        System.out.println(len);//11
        System.out.println(s1);//hello,world

        //反射equals( Object )
        Method equalsMethod = class1.getMethod("equals", Object.class);

        Object result = equalsMethod.invoke(s1, "hello");//s1.equals("hello")
        System.out.println(result);//false

        //7)substring( from ,to )
        Method substringMethod = class1.getMethod("substring", int.class, int.class);
        Object s2 = substringMethod.invoke(s1, 0, 5); //s1.substring(0,5)
        System.out.println(s2);//hello

        //反射静态方法valueOf( int )
        Method valueOfMethod = class1.getMethod("valueOf", int.class);
        //把int数字转换为字符串
        Object s3 = valueOfMethod.invoke(null, 2);//String.valueOf(2);
        System.out.println(s3);
        System.out.println(s3 instanceof String);
    }
}


class Learn20_0 {

    static {
        System.out.println("静态代码块, 在类加载内存后执行");
    }

    public int xx = 10;
    private int yy = 20;
    int zz = 30;
    public static int QQ = 60;

}
