package Servlet.JSP.A;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname Servlet04
 * @Date 2020/3/15 16:30
 * @Created by Falling Stars
 * @Description 模板模式的应用，重载service方法
 */
@WebServlet(urlPatterns = {"/Servlet/JSP/A/Servlet03/save.do", "/Servlet/JSP/A/Servlet03/update.do",
        "/Servlet/JSP/A/Servlet03/delete.do", "/Servlet/JSP/A/Servlet03/select.do"})
public class Servlet04 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入到service方法来处理请求");
        String path = req.getServletPath();
        if ("/Servlet/JSP/A/Servlet03/save.do".equals(path)) {
            System.out.println("保存操作");
        } else if ("/Servlet/JSP/A/Servlet03/update.do".equals(path)) {
            System.out.println("更新操作");
        } else if ("/Servlet/JSP/A/Servlet03/delete.do".equals(path)) {
            System.out.println("删除操作");
        } else if ("/Servlet/JSP/A/Servlet03/select.do".equals(path)) {
            System.out.println("查询操作");

        }
    }
}
