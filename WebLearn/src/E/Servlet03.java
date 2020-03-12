package E;

import D.Dept;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @Classname Servlet03
 * @Date 2020/3/11 22:55
 * @Created by Falling Stars
 * @Description 查询某一个dept
 */
@WebServlet(urlPatterns = "/E/Servlet03")
public class Servlet03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String deptNo = req.getParameter("deptno");
        String sql = "SELECT  * from dept where deptno = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Dept dept = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(deptNo));
            rs = ps.executeQuery();
            rs.next();
            String dname = rs.getString("dname");
            String loc = rs.getString("loc");
            dept = new Dept(Integer.valueOf(deptNo), dname, loc);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
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
        System.out.println(dept);
        req.setAttribute("dept", dept);

        req.getRequestDispatcher("/JSP/JSP07.jsp").forward(req, resp);
    }
}
