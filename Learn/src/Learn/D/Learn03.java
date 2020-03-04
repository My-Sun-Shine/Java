package Learn.D;

import java.sql.*;
import java.util.Scanner;

/**
 * @Classname Learn03
 * @Date 2020/3/3 23:27
 * @Created by Falling Stars
 * @Description SQL注入
 */
public class Learn03 {
    /**
     * drop table if exists tbl_user;
     * create table tbl_user(
     * id int primary key auto_increment,
     * username varchar(255),
     * password varchar(255),
     * email varchar(255)
     * );
     * insert into tbl_user(username,password,email) values('zhangsan','123','zhangsan@123.com');
     * insert into tbl_user(username,password,email) values('admin','admin123','admin@126.com');
     */
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement ps = null;
    private static String url = "jdbc:mysql://localhost:3306/bjpow";
    private static String user = "root";
    private static String password = "123456";

    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.jdbc.Driver");//不需要返回值
            conn = DriverManager.getConnection(url, user, password);
            //resultSet = m1(conn);
            //resultSet = m2(conn);
            resultSet = m3(conn);
            Learn01.printSet(resultSet);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
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
            if (stmt != null) {
                try {
                    stmt.close();
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
    }

    /**
     * 存在sql注入的风险
     * 输入username= aa
     * password=   aa' or '1'='1
     *
     * @param conn
     * @return
     */
    private static ResultSet m1(Connection conn) {
        Scanner s = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String username = s.nextLine();
        System.out.print("请输入密码：");
        String password = s.nextLine();
        String sql = "select id,username,password,email from tbl_user where username = '" + username
                + "' and password = '" + password + "'";
        System.out.println(sql);
        try {
            stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 防止sql注入
     *
     * @param conn
     * @return
     */
    private static ResultSet m2(Connection conn) {
        Scanner s = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String username = s.nextLine();
        System.out.print("请输入密码：");
        String password = s.nextLine();
        //框架SQL（SQL语句当中但凡需要“值”的位置统一使用?代替）
        String sql = "select id,username,password,email from tbl_user where username = ? and password = ?";
        //System.out.println(sql);
        try {
            //程序执行到这里Java程序会将这个框架sql发送给DBMS，DBMS进行预先的sql语句编译
            //获取“预编译”的数据库操作对象
            ps = conn.prepareStatement(sql);
            /*
            PreparedStatement原理：
            先进行sql框架的编译，然后给sql框架的占位符?传值，即使用户信息中含有sql语句关键字，
            但是这些关键字并不会参与sql语句的编译过程，这样可以防止sql注入
            * */

            ps.setString(1, username);
            ps.setString(2, password);

            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 进行模糊查询
     * 占位符?不能出现在单引号当中，占位符出现在单引号当中之后变成了普通的问号字符
     *
     * @param conn
     * @return
     */
    private static ResultSet m3(Connection conn) {
        //String sql = "select ename from emp where ename like '%?%'";
        //占位符?不能出现在单引号当中，占位符出现在单引号当中之后变成了普通的问号字符。

        String sql = "select ename from emp where ename like ?";
        //System.out.println(sql);
        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, "%O%");

            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
