package spring.ioc.learn07;

/**
 * @Classname SomeServiceImpl
 * @Date 2020/4/1 23:19
 * @Created by Falling Stars
 * @Description
 */
public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        System.out.println("===============doSome=================");
    }
}
