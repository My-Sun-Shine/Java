package Servlet.JSP.A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Classname Servlet01
 * @Date 2020/3/14 20:30
 * @Created by Falling Stars
 * @Description ajax练习，获取text数据
 */
@WebServlet(urlPatterns = "/Servlet/JSP/A/Servlet01")
public class Servlet01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println("Servlet/JSP/A/Servlet01");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * 如果该方法正在处理的是一个传统请求，那么我们就必须要以传统的形式为浏览器做响应，比如转发，重定向
         *
         * 如果该方法正在处理的是一个ajax请求，那么我们就必须要为ajax请求提供一个返回值，使用响应流的方式
         *
         */
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("中国");
        out.close();//tomcat服务器会自动的帮我们关闭掉

    }
}
