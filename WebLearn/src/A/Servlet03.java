package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname Servlet03
 * @Date 2020/3/8 16:29
 * @Created by Falling Stars
 * @Description HttpServletResponse接口：响应接口
 */
@WebServlet(urlPatterns = "/A/Servlet03")
public class Servlet03 extends HttpServlet {

    /**
     * 1）HttpServletResponse接口继承于ServletResponse接口
     * <p>
     * 2）HttpServletResponse接口修饰对象负责将Servlet中处理结果推送到【响应包】，然后这个【响应包】由Tomcat推送到客户端计算机浏览器
     * <p>
     * 3）将HttpServletResponse接口修饰对象称为【响应对象】
     * <p>
     * 4）响应对象是由Tomcat负责创建的。并由Tomcat将响应对象作为参数，传入到Servlet实现类中doGet/doPost
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println("doGet.........Servlet03");

        //这句话的意思，是让浏览器用utf8来解析返回的数据
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        //这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859  
        resp.setCharacterEncoding("UTF-8");

        PrintWriter respWriter = resp.getWriter();
        respWriter.write(20 + 30);//显示为2，因为输出的2是50的unicode码

        respWriter.write("<br/>");
        respWriter.write(20 + 30 + "");

        respWriter.write("<br/>");
        String str = "Hello World";
        respWriter.write(str);

        respWriter.write("<br/>");
        String str1 = "你好";
        respWriter.write(str1);

        String str2 = "姓名:<input type='type' />";
        respWriter.write("<br/>");
        respWriter.write(str2);

        //resp.sendRedirect("http://baidu.com");//转到百度页面
        respWriter.write("<br/>");
        List<Map<String, String>> list = getData();
        for (Map<String, String> map : list) {
            respWriter.write(map.get("deptno") + "," + map.get("dname") + "," + map.get("loc"));
            respWriter.write("<br/>");
        }

    }


    /**
     * 使用JDBC获取数据库数据
     * 注意：mysql-connector-java-5.1.23-bin.jar必须添加到Tomcat的lib文件夹下
     *
     * @return
     */
    private List<Map<String, String>> getData() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String, String>> list = new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            String sql = "select * from dept";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("deptno", rs.getString("deptno"));
                    map.put("dname", rs.getString("dname"));
                    map.put("loc", rs.getString("loc"));
                    list.add(map);
                }
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return null;
    }
}
