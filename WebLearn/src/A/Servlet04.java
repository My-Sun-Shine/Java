package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Classname Servlet03
 * @Date 2020/3/8 16:29
 * @Created by Falling Stars
 * @Description HttpServletRequest接口：请求接口
 */
@WebServlet(urlPatterns = "/A/Servlet04")

public class Servlet04 extends HttpServlet {
    /**
     * 1.HttpServletRequest接口继承ServletRequest接口
     * <p>
     * 2.将HttpServletRequest接口修饰的对象【请求对象】
     * <p>
     * 3.【请求对象】在Servlet中可以读取来自于浏览器发送的【请求协议包】中内容
     * <p>
     * 4.【请求对象】是由Tomcat负责创建的。并由Tomcat将【请求对象】作为参数，传入到Servlet实现类中doGet/doPost
     * <p>
     * GET请求方式；要求浏览器将请求参数保存在【请求头】；
     * <p>
     * 【请求头】内容只能由Tomcat负责翻译；
     * Tomcat9.0版本中默认使用的字符集utf-8
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get请求-------------------start");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("URL：" + requestURI);
        System.out.println("Method：" + method);

        //如果获取的参数不存在，这该方法返回null
        String username = request.getParameter("username");

        System.out.println("username：" + username);

        String[] aiHaos = request.getParameterValues("aiHao");
        System.out.println(Arrays.toString(aiHaos));

        System.out.println("get请求-------------------end");
    }

    /**
     * POST请求方式；要求浏览器将请求参数保存在【请求体】
     * <p>
     * 【请求体】内容只能由【HttpServletRequest】负责翻译
     * <p>
     * 【HttpServletRequest】默认使用的字符集ISO-8859-1
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post请求-------------------start");
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");

        System.out.println("username：" + username);

        String[] aiHaos = req.getParameterValues("aiHao");
        System.out.println(Arrays.toString(aiHaos));

        System.out.println("post请求--------------------end");
    }
}
