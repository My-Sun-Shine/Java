package Learn.D;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @Classname Learn02
 * @Date 2020/3/3 23:11
 * @Created by Falling Stars
 * @Description 怎么获取类路径下的文件的绝对路径，读取数据库配置文件
 */
public class Learn02 {
    /**
     * @param args
     */
    public static void main(String[] args) {
        m1();
        m();

    }

    private static void m() {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //String[] configs = getConfig1();
            String[] configs = getConfig2();
            Class.forName(configs[0]);
            conn = DriverManager.getConnection(configs[1], configs[2], configs[3]);
            ps = conn.prepareStatement("select * from emp");
            boolean execute = ps.execute();
            if (execute) {
                System.out.println("OK");
            } else {
                System.out.println("Not OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private static String[] getConfig1() throws IOException {
        Properties properties = new Properties();
        //InputStream in = Learn24.class.getResourceAsStream("/Learn/D/config.properties");
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("Learn/D/config.properties");
        properties.load(in);
        String jdbc = properties.getProperty("JDBC");
        String url = properties.getProperty("URL");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return new String[]{jdbc, url, username, password};
    }

    private static String[] getConfig2() {
        //不需要斜杠开头, 也不需要配置文件的扩展名(前提是配置文件的扩展名properties写正确)
        ResourceBundle bundle = ResourceBundle.getBundle("Learn/D/config");
        String jdbc = bundle.getString("JDBC");
        String url = bundle.getString("URL");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        return new String[]{jdbc, url, username, password};
    }


    /**
     * 放在src目录下的资源都可以认为放到了类路径当中。
     * 怎么获取类路径下的文件的绝对路径？
     * String path = Thread.currentThread().getContextClassLoader().getResource("从类的根路径下作为起点的路径").getPath();
     * 以上代码只能获取类路径下文件的绝对路径，当这个文件不在类路径下的话，以上代码无法获取绝对路径
     * Thread.currentThread().getContextClassLoader() 获取当前线程的类加载器对象
     */
    private static void m1() {
        // 当一个文件在类路径下的时候，怎么获取该文件的绝对路径呢？
        // String path = Thread.currentThread().getContextClassLoader().getResource("a").getPath();
        String url = "Learn/D/Learn02.class";
        String path = Thread.currentThread().getContextClassLoader().getResource(url).getPath();
        System.out.println(path);
    }
}
