package A;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @Classname Servlet16
 * @Date 2020/3/12 21:33
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = "/A/Servlet16")
public class Servlet16 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String dname = req.getParameter("dname");
        String sql = "select count(*)  from  dept where dname=?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ServletContext application = req.getServletContext();
        String driver = application.getInitParameter("mysqlDriver");
        String url = application.getInitParameter("mysqlurl");
        String user = application.getInitParameter("mysqlusername");
        String password = application.getInitParameter("mysqlpassword");
        boolean isflag = false;

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }//延迟工作时间3秒

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            ps = conn.prepareStatement(sql);
            ps.setString(1, dname);
            rs = ps.executeQuery();
            rs.next();
            isflag = rs.getInt(1) == 0;

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

        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        if (isflag) {
            out.write("部门名称可以注册");
        } else {
            out.write("部门名称已经存在");
        }

    }
}
