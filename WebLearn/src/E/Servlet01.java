package E;

import D.Dept;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Servlet01
 * @Date 2020/3/11 16:22
 * @Created by Falling Stars
 * @Description 查询所有dept
 */
@WebServlet(urlPatterns = "/E/Servlet01")
public class Servlet01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from dept";
        List<Dept> Deptlist = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int deptNo = rs.getInt("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Deptlist.add(new Dept(deptNo, dname, loc));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
           if(rs!=null){
               try {
                   rs.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
           if(ps!=null){
               try {
                   ps.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
           if(conn!=null){
               try {
                   conn.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
        }
        System.out.println(Deptlist);
        req.setAttribute("deptList", Deptlist);

        req.getRequestDispatcher("/JSP/JSP06.jsp").forward(req, resp);
    }
}
