package batis.util;



/**
 * @Classname ServiceFactory
 * @Date 2020/3/17 22:56
 * @Created by Falling Stars
 * @Description MyBaits框架下的代理工厂
 */
public class ServiceFactory {
    public static Object getService(Object service) {
        System.out.println("getService：" + service);
        return new TransactionInvocationHandler(service).getProxy();
    }
}
