package spring.aop.learn04;

/**
 * @Classname SomeServiceImpl
 * @Date 2020/4/4 13:29
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl implements SomeService {

    @Override
    public void doSecond() {
        System.out.println("====业务方法doSecond===");
        //System.out.println("====业务方法doSecond===" + (10 / 0));
    }
}
