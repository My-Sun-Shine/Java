package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.regex.Pattern;

/**
 * @Classname Servlet05
 * @Date 2020/3/8 22:22
 * @Created by Falling Stars
 * @Description HttpServletRequest接口的练习
 */
@WebServlet(urlPatterns = "/A/Learn05")
public class Servlet05 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String deptno = req.getParameter("deptNo");
        String dname = req.getParameter("dname");
        String loc = req.getParameter("loc");
        System.out.println(deptno + "," + dname + "," + loc);
        if(isNumeric(deptno)){
            insertData(deptno, dname, loc);
        }
        else{
            PrintWriter writer = resp.getWriter();
            writer.write("deptno is not Integer");
        }




    }

    private boolean insertData(String deptno, String dname, String loc) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpow", "root", "123456");
            String sql = "INSERT INTO dept(deptno,dname,loc) VALUES (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(deptno));
            ps.setString(2, dname);
            ps.setString(3, loc);
            return ps.execute();
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
        return false;
    }

    private boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
