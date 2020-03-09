package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Classname Servlet11
 * @Date 2020/3/9 14:45
 * @Created by Falling Stars
 * @Description 关于服务端使用多个资源处理用户的一个请求：【重定向方案】【请求转发方案】
 */
@WebServlet(urlPatterns = "/A/Servlet11")
public class Servlet11 extends HttpServlet {
    /**
     * 【重定向方案特征】：
     * 1.通过重定向可以访问的资源范围：可以同一个网站的资源，也可以是不同网站的资源
     * <p>
     * 2.重定向发生的位置：是在客户端；因此在客户端的浏览器中可以看到被调用的资源地址
     * <p>
     * 3.被重定向调用的资源接收的请求方式：只能是GET
     * <p>
     * 4.如果两个资源在同一个网站中，可以使用application共享资源
     * 5.如果两个资源不再同一个网站中，此时不同网站有不同application，因此无法共享数据.
     * <p>
     * <p>
     * 6.如果两个资源在同一个网站中，可以使用session共享资源
     * 7.如果两个资源不再同一个网站中，可以使用共享session
     * <p>
     * 8.request ：不能共享数据
     */

    /**
     * 【请求转发方案】:理解为【重定向缩小】，调用的资源文件必须来自于同一个网站
     * <p>
     * <p>
     * 1.[请求转发不适合场景]：
     * 【添加之后查询】：点击刷新按钮时，重复数据提交
     * 【删除之后查询】：点击刷新按钮时，重复数据删除
     * 【更新之后查询】：点击刷新按钮时，重复数据更新
     * <p>
     * 2.请求转发适合场景：Servlet之后，调用html文件或者JSP文件（动态html文件）
     * <p>
     * <p>
     * 3.请求转发调用资源的范围：只能是同一个网站
     * <p>
     * 4.请求转发发生的位置：发生在服务端，导致客户端无法看到调用的地址
     * <p>
     * 5.在请求转发过程中，所有的资源文件接收的请求方式保持一致的
     * <p>
     * 6.在请求转发过程中，application，session，request的资源可以共享
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println("Servlet11 doGet");

        HttpSession session = req.getSession();
        session.setAttribute("key", "Servlet11放入的数据");
        System.out.println("Servlet11的请求对象内存地址  " + req);
        System.out.println("Servlet11的到请求参数内容 " + req.getParameter("param"));

        //使用request进行共享数据，设置数据
        req.setAttribute("request","request传来的数据");

        //请求转发
        req.getRequestDispatcher("/A/Servlet12").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("Servlet11 doPost");
        req.getRequestDispatcher("/A/Servlet12").forward(req, resp);
    }
}
