package E;

import D.Job;
import net.sf.json.JSONArray;

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
 * @Classname Servlet08
 * @Date 2020/3/13 14:19
 * @Created by Falling Stars
 * @Description 选择某一个部门，展示该部门中职位信息
 */
@WebServlet(urlPatterns = "/E/Servlet08")
public class Servlet08 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String deptNo = null;
        deptNo = req.getParameter("deptno");

        String sql = "SELECT dname,ifnull(job,'') AS job,COUNT(job) AS jobNum " +
                "FROM dept d LEFT JOIN emp e ON d.deptno=e.deptno WHERE d.deptno=? " +
                "GROUP BY dname,job ;";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List list = new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(deptNo));
            rs = ps.executeQuery();

            while (rs.next()) {
                String dname = rs.getString("dname");
                String job = rs.getString("job");
                int jobNum = rs.getInt("jobNum");
                //System.out.println(dname);
                list.add(new Job(dname, job, jobNum));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray);
        resp.getWriter().print(jsonArray);


    }
}
