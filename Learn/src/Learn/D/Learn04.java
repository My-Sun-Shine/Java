package Learn.D;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Classname Learn04
 * @Date 2020/3/4 11:15
 * @Created by Falling Stars
 * @Description 事务和行级锁
 */
public class Learn04 {
    /**
     * 在JDBC当中，但凡只要执行一条DML语句则提交一次
     * <p>
     * conn.setAutoCommit(false);	关闭自动提交
     * conn.commit();	手动提交
     * conn.rollback();	手动回滚
     * <p>
     * drop table if exists tbl_act;
     * create table tbl_act(
     * actno varchar(255) primary key,
     * balance double(10,2)
     * );
     * insert into tbl_act values('act-001' , 50000);
     * insert into tbl_act values('act-002' , 0);
     */
    private static String url = "jdbc:mysql://localhost:3306/bjpow";
    private static String user = "root";
    private static String password = "123456";

    public static void main(String[] args) {
        m1();
    }

    /**
     * 事务的使用
     */
    private static void m1() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            String sql = "update tbl_act set balance = ? where actno = ?";

            // 自动提交机制关闭
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);
            ps.setDouble(1, 20000);
            ps.setString(2, "act-001");
            int count = ps.executeUpdate();//只要结束就会提交

            // 遇到了异常
            String s = null;
            s.toString();

            // 给?传值
            ps.setDouble(1, 30000);
            ps.setString(2, "act-002");
            count += ps.executeUpdate();

            System.out.println(count);

            conn.commit();//手动提交事务，结束事务。

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();//发生异常进行回滚数据
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            //e.printStackTrace();
        } finally {
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
    }

    /**
     * * 行级锁：
     * 1、行级锁用来处理什么问题的？
     * 多个事务同时对同一张表当中同一些行上的数据进行操作的时候，这些数据可能存在线程安全问题
     * 使用synchronized可以解决这个问题，这是使用java语言的机制，线程可以排队执行
     * 当不使用synchronized机制，我们可以选择使用数据库自身所带的机制“行级锁”来解决这个问题
     * <p>
     * select ename,sal from emp where job = 'MANAGER' for update;
     * 只要以上的DQL语句开始执行，直到当前事务结束，job='MANAGER'的这些记录被锁定，其它事务无法对这些数据进行修改
     */
    private static void m2() {

    }
}
