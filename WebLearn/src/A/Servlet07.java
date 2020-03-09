package A;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet07
 * @Date 2020/3/9 12:07
 * @Created by Falling Stars
 * @Description  ServletContext接口（全局作用域接口/上下文环境接口）
 */
@WebServlet(urlPatterns = "/A/Servlet07")
public class Servlet07 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        ServletContext context = req.getServletContext();

        //获取共享数据
        System.out.println(context.getAttribute("data1"));
    }
}
