package spring.aop.learn01;

/**
 * @Classname SomeServiceImpl
 * @Date 2020/4/4 13:29
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("====业务方法doSome===");
    }

    @Override
    public void doSome(String name, int age) {
        System.out.println("====业务方法doSome===，参数为" + name + "," + age);
    }
}
