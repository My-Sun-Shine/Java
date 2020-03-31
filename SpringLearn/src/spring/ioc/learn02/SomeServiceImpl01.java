package spring.ioc.learn02;

/**
 * @Classname LearnService001impl
 * @Date 2020/3/31 12:40
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl01 implements SomeService {

    /**
     * Bean的装配
     * 默认装配方式：Spring调用Bean类的无参构造函数，创建空值的实例对象
     */
    public SomeServiceImpl01() {
        super();
        System.out.println("无参构造函数SomeServiceImpl01()");
    }

    public SomeServiceImpl01(int age) {
        super();
        System.out.println("有参构造函数SomeServiceImpl01(),参数为" + age);
    }

    @Override
    public void doSome() {
        System.out.println("执行doSome()方法");
    }
}
