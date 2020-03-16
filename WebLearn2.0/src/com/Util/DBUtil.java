package com.Util;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * @Classname DBUtil
 * @Date 2020/3/15 21:57
 * @Created by Falling Stars
 * @Description 数据库工具：得到连接，关闭资源
 */
public class DBUtil {
    private DBUtil() {

    }

    private static String Driver;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    /**
     * ThreadLocal是一种基于当前线程安全的存取机制
     * 将所需值存放到ThreadLocal对象中之后，只要当前线程还在，那么就可以取得ThreadLocal对象里面保存的内容
     * <p>
     * set(值)：存值
     * get():取值
     * remove():移除值
     */
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try {
            /*
             * 驱动如何加载
             * Class.forName("com.mysql.jdbc.Driver")
             *
             * 驱动加载几次?
             * 服务器启动期间，加载一次即可
             *
             * 何时加载?
             * 是在所有jdbc代码执行前，加载
             */
            getConfig();
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 当threadLocal中有conn时，直接取出
     * 当threadLocal没有conn时，创建conn，并保存到threadLocal中
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            getConfig();
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            threadLocal.set(conn);
        }
        return conn;
    }

    /**
     * 关闭资源
     *
     * @param conn
     * @param ps
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {

        //关闭资源的顺序为按照创建的顺序逆序关闭
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            /*
             * tomcat以及我们常用web服务器，自带线程池
             * 线程池中的线程用完不销毁（回到线程池中），里面的值一直在
             * 所以必须手动remove
             *
             * 连接池：conn用完后，回到连接池中，conn也还在
             *
             */
            threadLocal.remove();
        }
    }


    /**
     * 获取配置
     */
    private static void getConfig() {
        //不需要斜杠开头, 也不需要配置文件的扩展名(前提是配置文件的扩展名properties写正确)
        ResourceBundle bundle = ResourceBundle.getBundle("com/Util/config");
        Driver = bundle.getString("JDBC");
        URL = bundle.getString("URL");
        USER = bundle.getString("username");
        PASSWORD = bundle.getString("password");
    }


}
