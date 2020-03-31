package spring.ioc.learn01;

/**
 * @Classname SomeServiceImpl
 * @Date 2020/3/30 22:10
 * @Created by Falling Stars
 * @Description 测试接口实现
 */
public class SomeServiceImpl implements SomeService {

    public SomeServiceImpl(){
        super();
        System.out.println("无参构造函数SomeServiceImpl()");
    }

    @Override
    public void doSome() {
        System.out.println("执行doSome()方法");
    }
}
