package com.Util;

/**
 * @Classname ServiceFactory
 * @Date 2020/3/16 22:54
 * @Created by Falling Stars
 * @Description 该工厂的作用为，传TAccountServiceImpl对象，取TAccountServiceProxy对象
 */
public class ServiceFactory {
    /**
     * 获取代理类
     *
     * @param service
     * @return
     */
    public static Object getService(Object service) {
        System.out.println("getService：" + service);
        return new TransactionInvocationHandler(service).getProxy();
    }
}
