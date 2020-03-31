package spring.ioc.learn02;

/**
 * @Classname LearnService001impl
 * @Date 2020/3/31 12:40
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl02 implements SomeService {

    public SomeServiceImpl02() {
        super();
        System.out.println("无参构造函数SomeServiceImpl02()");
    }

    public SomeServiceImpl02(int age) {
        super();
        System.out.println("有参构造函数SomeServiceImpl02(),参数为" + age);
    }

    @Override
    public void doSome() {
        System.out.println("执行doSome()方法");
    }
}
