package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet13
 * @Date 2020/3/9 17:19
 * @Created by Falling Stars
 * @Description Filter接口
 */
@WebServlet(urlPatterns = "/A/Servlet13")
public class Servlet13 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("成功");

    }
}

