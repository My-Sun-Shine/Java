package com.crm.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @Classname SqlSessionUtil
 * @Date 2020/3/20 23:27
 * @Created by Falling Stars
 * @Description 获取SqlSession
 */
public class SqlSessionUtil {

    private SqlSessionUtil() {
    }

    private static SqlSessionFactory factory;

    static {

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        factory = new SqlSessionFactoryBuilder().build(inputStream);

    }

    private static ThreadLocal<SqlSession> t = new ThreadLocal<>();

    public static SqlSession getSqlSession() {

        SqlSession session = t.get();

        if (session == null) {

            session = factory.openSession();
            t.set(session);
        }

        return session;

    }

    public static void close(SqlSession session) {

        if (session != null) {
            session.close();
            t.remove();
        }

    }


}
