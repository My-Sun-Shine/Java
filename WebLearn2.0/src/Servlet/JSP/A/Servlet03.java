package Servlet.JSP.A;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet03
 * @Date 2020/3/14 23:50
 * @Created by Falling Stars
 * @Description 域对象存取值的应用
 */
@WebServlet(urlPatterns = "/Servlet/JSP/A/Servlet03")
public class Servlet03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //不同域对象的值，观察不同情况下的取值情况
        req.setAttribute("str1", "aaa");
        req.getSession().setAttribute("str2", "bbb");
        ServletContext application = this.getServletContext();
        application.setAttribute("str3", "ccc");

        //请求转发(同一个网站的，会将request、response进行转发)
        //req.getRequestDispatcher("/JSP/A/JSP02.jsp").forward(req, resp);

        //重定向
        resp.sendRedirect(req.getContextPath()+"/JSP/A/JSP02.jsp");
    }
}
