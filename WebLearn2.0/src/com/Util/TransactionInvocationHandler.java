package com.Util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @Classname TransactionInvocationHandler
 * @Date 2020/3/16 22:30
 * @Created by Falling Stars
 * @Description 基于事务的动态代理
 */
public class TransactionInvocationHandler implements InvocationHandler {
    private Object target;

    public TransactionInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 下述invoke方法为代理类的业务方法
     * <p>
     * 应该由两部分代码构成
     * <p>
     * 一部分为业务逻辑
     * target能不能用？不能用，因为target是Object类型
     * 不能直接调用业务方法，那么我们就间接调用业务方法
     * 观察下述参数：
     * 参数method：表示业务层的方法
     * 参数args：业务层方法的参数
     * <p>
     * 一部分为业务逻辑的扩充（事务）
     * 正常搭建
     *
     * @param proxy  需要代理的类
     * @param method 表示业务层的方法
     * @param args   业务层方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入动态代理invoke......");
        Connection conn = null;
        Object obj = null;
        try {
            conn = DBUtil.getConn();
            conn.setAutoCommit(false);
            //处理业务逻辑 调用target对象的method方法，参数为args
            System.out.println("动态代理开始调用函数......");
            obj = method.invoke(target, args);
            System.out.println("动态代理结束调用函数......");
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, null, null);
        }
        System.out.println("离开invoke......");
        return obj;
    }

    /**
     * 获取一个新的代理
     *
     * @return
     */
    public Object getProxy() {

        //获取一个继承Proxy，并实现 target.getClass().getInterfaces()所有接口的类$Proxy0
        //对应该例子，，继承的是TAccountService接口
        //参数 类加载器 接口
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

    }
}
