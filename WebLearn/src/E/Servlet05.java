package E;

import D.Dept;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Servlet05
 * @Date 2020/3/12 13:35
 * @Created by Falling Stars
 * @Description JSTL的应用,统计每个部门各个job的人数
 */
@WebServlet(urlPatterns = "/E/Servlet05")
public class Servlet05 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String sql = " select dname,ifnull(job,'') as job,count(job) as jobNum " +
                "from dept d left join emp e on d.deptno=e.deptno " +
                "group by dname,job;";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map bigMap = new HashMap();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String dname = rs.getString("dname");
                String job = rs.getString("job");
                int jobNum = rs.getInt("jobNum");
                Map map =(Map)bigMap.get(dname);//小map or  null
                if(map==null){
                    map = new HashMap();
                    map.put(job, jobNum);
                    bigMap.put(dname, map);
                }else{
                    map.put(job, jobNum);
                }
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
        req.setAttribute("bigMap", bigMap);
        req.getRequestDispatcher("/JSP/JSP10.jsp").forward(req, resp);
    }
}
