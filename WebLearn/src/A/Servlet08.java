package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Classname Servlet08
 * @Date 2020/3/9 12:15
 * @Created by Falling Stars
 * @Description Cookie类：客户端计算机上用来保存当前用户的数据
 */
@WebServlet(urlPatterns = "/A/Servlet08")
public class Servlet08 extends HttpServlet {

    /**
     * 记录当前用户【私人数据】有什么用：提高服务质量，增加响应速度
     * <p>
     * 在浏览器访问某个网站时，必须无条件的将这个网站之前推送的cookie数据，再次推送到服务器上
     * <p>
     * <p>
     * 1）【默认情况】：Cookie推送到用户的浏览器上的如果用户关闭了浏览器，那么Cookie就会被销毁
     * <p>
     * 2）【人工干预】：Cookie推送到用户硬盘上的，同时可以指定Cookie在用户硬盘上的存活时间
     * 在指定的存活时间范围之内。不论用户是否关闭了浏览器都不会导致Cookie消亡
     * cookie.setMaxAge(60) 单位是秒
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        setCookie(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCookie(req, resp);
    }


    private void setCookie(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        //设置编码
        req.setCharacterEncoding("utf-8");
        //获取表单提交信息
        String username = req.getParameter("username");
        String sex = req.getParameter("sex");
        String other = req.getParameter("other");
        //赋值cookie
        Cookie cookie1 = new Cookie("username", username);
        Cookie cookie2 = new Cookie("sex", sex);
        Cookie cookie3 = new Cookie("other", other);
        //设置cookie存活时间
        cookie1.setMaxAge(120);
        //添加cookie
        resp.addCookie(cookie1);
        resp.addCookie(cookie2);
        resp.addCookie(cookie3);
    }
}
