package Learn.B;

/**
 * @Classname Learn03
 * @Date 2020/2/24 18:39
 * @Created by Falling Stars
 * @Description 抽象类
 */
public class Learn03 extends Learn030 {
    /*
    1)抽象类不能实例化对象, 抽象类引用需要赋值子类对象
	2)含有抽象方法的类必须定义为抽象类, 但是抽象类中不一定含有抽象方法
	3)子类继承了抽象类后, 需要重写抽象类的所有抽象方法,如果子类没有重写所有的抽象方法,子类也需要定义为抽象类
	4)abstract与final 不可以共存
	5)抽象类的引用可以赋值匿名内部类对象
	*/

    @Override
    public void show() {
        System.out.println("bbb");
    }

    public static void main(String[] args) {
        //抽象类的引用可以赋值匿名内部类对象
        Learn030 learn030 = new Learn030() {
            @Override
            public void show() {
                System.out.println("aaa");
            }
        };

        learn030.show();

        System.out.println("--------------------------------");
        Learn03 learn03 = new Learn03();
        learn03.show();

        System.out.println("--------------------------------");
        Master m = new Master();
        Dog d = new Dog();
        Cat c = new Cat();
        m.feed(d);
        m.feed(c);

        System.out.println("--------------------------------");
        Circle circle = new Circle(10);
        Rectangle rectangle = new Rectangle(10, 5);
        printGraphics2D(circle);
        printGraphics2D(rectangle);

    }

    private static void printGraphics2D(Graphics2D g) {
        System.out.println("图形面积:" + g.getArea());
        System.out.println("图形周长:" + g.getPerimeter());
    }
}

abstract class Learn030 {
    public abstract void show();
}

/**
 * 定义一个宠物类Pet, 宠物会卖萌sellMeng()
 * 定义小狗Dog类继承Pet类, 重写卖萌方法
 * 定义小猫Cat类继承Pet类,重写卖萌方法
 * 定义主人类Master,  主人有一个喂养宠物的方法,  void  feed( Pet  pet ),  主人喂宠物时,宠物就卖萌
 * 编写测试类, 主人喂狗, 主人喂猫
 */
abstract class Pet {
    public abstract void sellMeng();
}

class Dog extends Pet {
    @Override
    public void sellMeng() {
        System.out.println("汪汪汪");
    }
}

class Cat extends Pet {
    @Override
    public void sellMeng() {
        System.out.println("喵喵喵");
    }
}

class Master {
    public void feed(Pet pet) {
        pet.sellMeng();
    }
}

/**
 * 定义平面图形类, 有求面积,求求周长的操作
 * 不同的图形求面积/求周长的公式不同, 在平面图形类中,没法具体实现这两个操作
 */
abstract class Graphics2D {
    /**
     * 求面积
     *
     * @return
     */
    public abstract double getArea();

    /**
     * 求周长
     *
     * @return
     */
    public abstract double getPerimeter();
}

/**
 * 矩形
 */
class Rectangle extends Graphics2D {
    private double x;
    private double y;

    /**
     * 矩形
     *
     * @param x 长
     * @param y 宽
     */
    public Rectangle(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public double getArea() {
        return x * y;
    }

    @Override
    public double getPerimeter() {
        return (x + y) * 2;
    }
}

class Circle extends Graphics2D {
    private double x;

    /**
     * 圆形
     *
     * @param x 半径
     */
    public Circle(double x) {
        super();
        this.x = x;
    }

    @Override
    public double getArea() {
        return x * x * Math.PI;
    }

    @Override
    public double getPerimeter() {
        return 2 * x * Math.PI;
    }
}