package com.crm.utils;

/**
 * @Classname ServiceFactory
 * @Date 2020/3/20 23:27
 * @Created by Falling Stars
 * @Description 获取代理服务
 */
public class ServiceFactory {

    public static Object getService(Object service) {

        return new TransactionInvocationHandler(service).getProxy();

    }

}
