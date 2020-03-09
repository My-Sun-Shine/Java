package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Classname Servlet12
 * @Date 2020/3/9 16:50
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = "/A/Servlet12")
public class Servlet12 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        HttpSession session = req.getSession();
        System.out.println("Servlet11通过session传来的数据 " + session.getAttribute("key"));

        System.out.println("Servlet12的请求对象内存地址  " + req);
        System.out.println("Servlet12的到请求参数内容 " + req.getParameter("param"));
        //接收request传过来的数据
        System.out.println("Servlet11通过request传来的数据 " + req.getAttribute("request"));
        System.out.println("Servlet12 doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("Servlet12 doPost");
    }
}
