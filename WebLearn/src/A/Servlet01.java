package A;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Classname Servlet01
 * @Date 2020/3/7 23:32
 * @Created by Falling Stars
 * @Description Servlet实现类的请求路径写法
 */
//@WebServlet(urlPatterns = "/A/Servlet01")
public class Servlet01 extends HttpServlet {
    public Servlet01() {
        System.out.println("Servlet01构造方法被调用");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("hello, 这是我的第一个Servlet...");
        resp.getWriter().write("当前系统时间是：" + new Date());
        System.out.println("doGet......Servlet01");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
