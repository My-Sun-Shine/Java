package E;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet06
 * @Date 2020/3/12 23:24
 * @Created by Falling Stars
 * @Description  $.ajax()使用
 */
@WebServlet(urlPatterns = "/E/Servlet06")
public class Servlet06 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        String name = req.getParameter("uname");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(name+",你好");
    }
}
