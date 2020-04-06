package spring.aop.learn05;

/**
 * @Classname SomeServiceImpl
 * @Date 2020/4/4 13:29
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl implements SomeService {

    @Override
    public void doThird() {
        System.out.println("====业务方法doThird===");
        //System.out.println("====业务方法doThird===" + (10 / 0));
    }
}
