package spring.aop.learn02;

/**
 * @Classname SomeServiceImpl
 * @Date 2020/4/4 13:29
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl implements SomeService {
    @Override
    public String doOther(String name, int age) {
        System.out.println("====业务方法doOther===，name参数为" + name + ",age参数为" + age);
        return "doOther";
    }
}
