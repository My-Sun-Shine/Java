package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet09
 * @Date 2020/3/9 12:31
 * @Created by Falling Stars
 * @Description  Cookie类：客户端计算机上用来保存当前用户的数据
 */
@WebServlet(urlPatterns = "/A/Servlet09")
public class Servlet09 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);

        //读取cookie
        req.setCharacterEncoding("utf-8");
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + "：" + cookie.getValue());
        }
    }
}
