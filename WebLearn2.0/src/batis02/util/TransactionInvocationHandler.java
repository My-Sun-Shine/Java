package batis02.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Classname TransactionInvocationHandler
 * @Date 2020/3/17 22:51
 * @Created by Falling Stars
 * @Description MyBaits框架下的事务动态代理模式
 */
public class TransactionInvocationHandler implements InvocationHandler {
    private Object target;

    public TransactionInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * @param proxy  需要代理的类
     * @param method 表示业务层的方法
     * @param args   业务层方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入动态代理invoke......");
        SqlSession session = null;
        Object obj = null;
        try {
            session = batis.util.DBUtil.getSession();
            //处理业务逻辑 调用target对象的method方法，参数为args
            System.out.println("动态代理开始调用函数......");
            obj = method.invoke(target, args);
            System.out.println("动态代理结束调用函数......");
            session.commit();
        } catch (Exception e) {
            session.rollback();
            e.printStackTrace();
        } finally {
            DBUtil.close(session);
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
