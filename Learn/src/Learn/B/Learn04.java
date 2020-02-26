package Learn.B;

/**
 * @Classname Learn04
 * @Date 2020/2/24 20:18
 * @Created by Falling Stars
 * @Description 接口
 */
public class Learn04 {

    /**
     * 1)类实现了接口,需要重写接口中抽象方法,如果没有重写接口所有的抽象方法,那么这个类需要定义为抽象类
     * <p>
     * 2)接口是一种引用数据类型,可以定义变量,但是接口不能实例化对象,
     * 接口的引用可以赋值实现类对象, 通过接口引用调用抽象方法,
     * 实际上就是执行实现类对象的方法,称为接口多态.接口就是为了实现多态的
     * <p>
     * 3)接口的内容: 接口中方法默认使用public abstract修饰,
     * 接口中的字段默认使用public static final修饰,
     * 接口中还可以定义public static方法,
     * 还可以定义public default方法
     * <p>
     * 4)一个类在继承父类的同时,可以实现多个接口, 接口支持多实现, 需要重写所有接口的所有抽象方法
     * <p>
     * 5)接口也可以继承, 并且支持多继承
     * <p>
     * 6)接口的引用可以赋值匿名内部类对象
     */


    public static void main(String[] args) {
        Ilearn04 l = new Ilearn04() {
            @Override
            public void show() {
                System.out.println("AAA");
            }
        };

        l.show();
        l.m2();
        Ilearn04.sm();

        System.out.println("--------------------------");
        Computer c = new Computer();
        c.insert(new Mouse());
        c.checkout();
        c.insert(new Keyboard());
        c.checkout();
        System.out.println("--------------------------------");
        Product p1 = new Product("aaa", 1000, new ThirtyPercentDiscount());
        p1.showInfo();
        System.out.println("--------------------------------");
        Product p2 = new Product("aaa", 1000, new NoDiscount());
        p2.showInfo();
        System.out.println("--------------------------------");
        Product p3 = new Product("aaa", 1000, new CashBackDiscount());
        p3.showInfo();
    }
}

interface Ilearn04 {
    //接口中方法默认使用public abstract修饰
    void show();

    int xx = 66;//接口中的字段默认使用public static final修饰，直接通过接口名使用

    public static void sm() {
        System.out.println("接口中还可以定义public static方法，直接通过接口名使用");
    }

    public default void m2() {
        System.out.println("在接口中使用default修饰方法,");
        System.out.println("表示该方法可以有默认的方法体,");
        System.out.println("该方法在实现类中可以重写，也可以不重写.");
        System.out.println();
    }
}

/**
 * * 1) 定义Usb接口, 有连接设备, 断开连接两个操作
 * 2) 定义鼠标Mouse类,实现了Usb接口
 * 3) 定义键盘Keyboard类, 实现了Usb接口
 * 4) 定义计算机类Computer类,  计算机有一个Usb插槽
 * 可以把Usb设备插入到Usb插槽中
 * 可以把Usb插槽上的设备拔下来
 * 5)编写测试类, 测试往电脑上插入键盘, 拔出键盘后再插入鼠标
 */
interface Usb {
    /**
     * 连接设备
     */
    void connect();

    /**
     * 断开连接
     */
    void disconnect();
}

class Mouse implements Usb {
    @Override
    public void connect() {
        System.out.println("鼠标已连接,可以使用了");
    }

    @Override
    public void disconnect() {
        System.out.println("鼠标已拔出");
    }
}

class Keyboard implements Usb {
    @Override
    public void connect() {
        System.out.println("键盘已连接, 可以打字了");
    }

    @Override
    public void disconnect() {
        System.out.println("键盘已拔出");
    }
}

class Computer {
    //模拟USB插槽
    Usb usbSlot;

    //模拟把USB设备插入到USB插槽中
    public void insert(Usb usb) {
        usbSlot = usb;
        usb.connect();
    }

    //模拟把USB设备从插槽中拔出
    public void checkout() {
        if (usbSlot == null)
            return;
        usbSlot.disconnect();
        usbSlot = null;
    }

}

/**
 * * 1)定义一个折扣接口Discountable
 * 操作: 可以根据原价返回折扣之后 的价格
 * double  getDiscountPrice(  double originalPrice );
 * 2) 定义一个打七折的实现类,  ThirtyPercentDiscount
 * 3) 定义一个按比例返现的实现类, CashBackDiscount
 * 4) 定义一个没有折扣的实现类,  NoDiscount
 * 5) 定义一个产品类 Product, 有产品名称, 有原价, 有一个折扣方式三个属性
 * 定义一个方法,显示产品名称,原价, 折扣之后的价格
 * 6)编写测试类
 */
interface Discountable {
    double getDiscountPrice(double originalPrice);
}

class ThirtyPercentDiscount implements Discountable {
    @Override
    public double getDiscountPrice(double originalPrice) {
        return originalPrice * 0.7;
    }
}

class CashBackDiscount implements Discountable {
    @Override
    public double getDiscountPrice(double originalPrice) {
        if (originalPrice > 300) {
            return originalPrice - 69;
        } else if (originalPrice > 200) {
            return originalPrice - 30;
        }
        return originalPrice;
    }
}

class NoDiscount implements Discountable {
    @Override
    public double getDiscountPrice(double originalPrice) {
        return originalPrice;
    }
}

class Product {
    private String name;
    private double originalprice;
    private Discountable discountable;

    public Product(String name, double originalprice, Discountable discountable) {
        this.name = name;
        this.originalprice = originalprice;
        this.discountable = discountable;
    }

    public void showInfo() {
        System.out.println("产品信息:");
        System.out.println("\t名称:" + name);
        System.out.println("\t原价:" + originalprice);
        //根据打折方式, 计算产品折扣之后 的价格
        System.out.println("\t折扣价:" + discountable.getDiscountPrice(originalprice));

    }
}


