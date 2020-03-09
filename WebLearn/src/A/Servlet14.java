package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet14
 * @Date 2020/3/9 22:07
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = "/A/Servlet14")
public class Servlet14 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        String value = req.getParameter("param");
        System.out.println("参数：" + value);
        resp.getWriter().write("中文");
    }
}
