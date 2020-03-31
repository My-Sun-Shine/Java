package spring.ioc.learn02;

/**
 * @Classname LearnService001impl
 * @Date 2020/3/31 12:40
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl03 implements SomeService {

    public SomeServiceImpl03() {
        super();
        System.out.println("无参构造函数SomeServiceImpl03()");
    }

    public SomeServiceImpl03(int age) {
        super();
        System.out.println("有参构造函数SomeServiceImpl03(),参数为" + age);
    }

    @Override
    public void doSome() {
        System.out.println("执行doSome()方法");
    }
}
