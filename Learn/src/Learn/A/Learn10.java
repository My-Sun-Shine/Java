package Learn.A;

/**
 * @Classname Learn010
 * @Date 2020/2/21 16:58
 * @Created by Falling Stars
 * @Description 继承和方法覆盖
 */
public class Learn10 {
    /*
    B类继承A类
    A类：父类，基类，超类，superclass
    B类：子类，派生类，subclass
    子类不会继承父类的私有方法和构造方法
    Java语言的所有类都默认继承Object
     */

    public static void main(String[] args) {

        B b = new B();
        b.Print();// 调用子类方法

        A b1 = new B();//向上转型
        b1.Print();
        ((B) b1).PrintB();//向下转型

        //类似C#的is运算符
        //返回false,则代表b1不是C类型
        if (b1 instanceof C) {
            System.out.println("YY");
        } else if (b1 instanceof A) {
            //返回true,则代表b1是A类型
            System.out.println("Y");
        }

    }
}

class A {
    public void Print() {
        System.out.println("A");
    }
}

class B extends A {
    /*
    方法覆盖
    方法重写发生在具有继承关系的父子类之间
    返回值类型相同，方法名相同，参数列表相同
    访问权限重写后不能变低，可以更高
    抛出异常的数量不能变多，可以变少
    私有方法不能继承，所以不能覆盖
    构造方法不能继承，所以不能覆盖
    静态方法不能覆盖
     */
    public void Print() {
        System.out.println("B");
    }

    public void PrintB() {
        System.out.println("BB");
    }
}

class C extends A {
}


