package A;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet06
 * @Date 2020/3/9 10:59
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = "/A/Servlet06")
public class Servlet06 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        ServletContext application = req.getServletContext();
        String driver = application.getInitParameter("mysqlDriver");
        String url = application.getInitParameter("mysqlurl");
        String username = application.getInitParameter("mysqlusername");
        String password = application.getInitParameter("mysqlpassword");
        System.out.println(driver);
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
