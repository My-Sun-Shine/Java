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
     * 取得连接
     * @return
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        getConfig();
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    /**
     * 关闭资源
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
