package Learn.D;

import java.sql.*;

/**
 * @Classname Learn01
 * @Date 2020/3/3 22:01
 * @Created by Falling Stars
 * @Description JDBC的练习
 */
public class Learn01 {

    public static void main(String[] args) {
        //String sql1 = "insert into dept(deptno,dname,loc) values(50,'销售部','北京')";
        //update(sql1);
        //String sql2 = "update dept set dname = '研发一部', loc = '天津' where deptno = 50";
        //update(sql2);
        //String sql3 = "delete from dept where deptno = 50";
        //update(sql3);
        //query1();
        String sql = "select e.empno,e.ename,e.sal as salary,d.dname as deptname from emp e " +
                "join dept d on e.deptno = d.deptno";
        query(sql);

    }

    private static void update(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //第一步：注册驱动	【作用：告诉Java程序我们即将要连接的数据库是什么版本的】
            /*
             * Class.forName("com.mysql.jdbc.Driver");
             * Class.forName方法执行的时候会进行类加载，类加载的时候static语句块会执行
             */
            // Driver类中有一个静态代码块，可以注册
            Class.forName("com.mysql.jdbc.Driver");//不需要返回值

            //第二步：获取数据库连接对象	【程序执行到此处表示JVM和MySQL数据库建立连接，通道打开。】
            //Oracle数据库的URL：oracle:jdbc:thin:@192.168.151.2:1521:bjpow
            String url = "jdbc:mysql://localhost:3306/bjpow";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);
            //System.out.println(conn.toString());

            //第三步：获取数据库操作对象	【数据库操作对象主要任务是执行SQL语句。】
            //一个Connection可以开启多个Statement对象
            stmt = conn.createStatement();
            //System.out.println(stmt.toString());

            //第四步：执行SQL语句				【执行SQL语句】
            //DBC中的SQL语句不需要以“;”结尾
            int count = stmt.executeUpdate(sql);
            System.out.println(count);

            //第五步：处理查询结果集			【当SQL语句是一个DQL语句的话，是有查询结果的，需要处理查询结果。】


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //第六步：释放资源					【关闭所有资源。】
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


    private static void query1() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");//不需要返回值
            String url = "jdbc:mysql://localhost:3306/bjpow";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String sql = "select e.empno,e.ename,e.sal as salary,d.dname as deptname from emp e " +
                    "join dept d on e.deptno = d.deptno";
            resultSet = stmt.executeQuery(sql);
            // next()方法会将cursor【游标】从当前所在的位置向前移动一行，游标指向的这一行有数据的话next()方法返回true。否则返回false
            // 游标最开始的时候在第一行记录的后面(上面/左面)。游标最初并没有指向第一条记录
            while (resultSet.next()) {
                // 以特定的类型取出数据
				/*
				int empno = resultSet.getInt("empno");
				String ename = resultSet.getString("ename");
				double sal = resultSet.getDouble("salary");
				String dname = resultSet.getString("deptname");
				System.out.println(empno + "," + ename + "," + sal * 10 + "," + dname);
				*/

                int empno = resultSet.getInt(1);
                String ename = resultSet.getString(2);
                double sal = resultSet.getDouble(3);
                String dname = resultSet.getString(4);
                System.out.println(empno + "," + ename + "," + sal * 5 + "," + dname);
            }

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


    private static void query(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;
        try {

            Class.forName("com.mysql.jdbc.Driver");//不需要返回值
            String url = "jdbc:mysql://localhost:3306/bjpow";
            String user = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            printSet(resultSet);

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


    public static void printSet(ResultSet resultSet) throws SQLException {
        if(resultSet!=null){
            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();//获取列的数量
            while (resultSet.next()){
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i));
                    if (i != columnCount) {
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
        }
    }
}
