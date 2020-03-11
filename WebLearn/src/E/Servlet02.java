package E;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Classname Servlet02
 * @Date 2020/3/11 20:43
 * @Created by Falling Stars
 * @Description 删除某一个dept
 */
@WebServlet(urlPatterns = "/D/Servlet02")
public class Servlet02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deptNo = req.getParameter("deptno");
        String sql = "delete from dept where deptno = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        boolean isdelete = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(deptNo));
            isdelete = ps.execute();
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
        if (!isdelete) {
            resp.getWriter().write("delete failed");
        }

        resp.sendRedirect("/D/Servlet01");

    }
}
