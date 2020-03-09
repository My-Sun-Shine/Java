package A;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet15
 * @Date 2020/3/9 23:28
 * @Created by Falling Stars
 * @Description 监听器接口
 */
@WebServlet(urlPatterns = "/A/Servlet15")
public class Servlet15 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        ServletContext context = req.getServletContext();
        context.setAttribute("key", 100);
        context.setAttribute("key", 500);
        context.removeAttribute("key");
    }
}
