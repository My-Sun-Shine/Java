package batis02.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Classname DBUtil
 * @Date 2020/3/17 22:24
 * @Created by Falling Stars
 * @Description MyBaits框架下的获取session以及关闭
 */

public class DBUtil {
    /*
     * statice块
     * 		以前：加载驱动
     * 		现在：加载MyBaits主配置文件，取得SqlSessionFactory
     *
     * getConn
     * 		以前的getConn：取得连接对象
     * 		现在的getSession:取得SqlSession对象
     *
     * myClose
     * 		以前关闭conn，关闭ps，关闭rs
     * 		现在关闭sqlsession
     *
     * ThreadLocal
     * 		以前存conn
     * 		现在存sqlsession
     *
     * 以前conn关闭后
     * 		t.remove()
     * 现在sqlsession关闭后
     * 		t.remove()
     */
    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<>();

    public static SqlSession getSession() {
        SqlSession session = threadLocal.get();
        if (session == null) {
            session = sqlSessionFactory.openSession();//获取连接池的连接
            threadLocal.set(session);
        }
        return session;
    }


    public static void close(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }
}
