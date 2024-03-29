package com.crm.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.session.SqlSession;

/**
 * @Classname TransactionInvocationHandler
 * @Date 2020/3/20 23:27
 * @Created by Falling Stars
 * @Description 事务代理模式
 */
public class TransactionInvocationHandler implements InvocationHandler {

    private Object target;

    public TransactionInvocationHandler(Object target) {

        this.target = target;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        SqlSession session = null;

        Object obj = null;

        try {
            session = SqlSessionUtil.getSqlSession();

            obj = method.invoke(target, args);

            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
            throw e.getCause();//处理的是什么异常，继续抛出什么异常
        } finally {
            SqlSessionUtil.close(session);
        }

        return obj;
    }

    public Object getProxy() {

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

    }

}











































