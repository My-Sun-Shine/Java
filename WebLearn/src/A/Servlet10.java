package A;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @Classname Servlet10
 * @Date 2020/3/9 13:09
 * @Created by Falling Stars
 * @Description HTTPSession接口：会话管理对象，在服务器计算机保存用户私人数据
 */
@WebServlet(urlPatterns = "/A/Servlet10")
public class Servlet10 extends HttpServlet {

    /**
     * Cookie只能保存String类型数据
     * HTTPSession可以保存高级类型数据
     * <p>
     * 【会话管理对象】将消耗服务端的内存
     * Tomcat考虑内存消耗问题，因此不会主动为当前用户创建【会话管理对象】
     * 只有用户访问的Servlet的向Tomcat索要当前用户的【会话管理对象】时，Tomcat才会为当前用户创建【会话管理对象】
     * <p>
     * Tomcat在创建Session时，自动为这个Session创建一个【唯一编号】【sessionId】;
     * Tomcat偷偷将这个编号通过Cookie推送到用户的浏览器上
     * <p>
     * 3)HttpSession销毁时机：
     * 在用户关闭浏览器时，此时只是切断用户与session的联系，但是Tomcat没法监控客户端的浏览器何时关闭
     * 所以此时session依然是存在的
     * <p>
     * Tomcat会为每一个HttpSession设置一个【最大空闲时间】。
     * 如果发现当前HttpSession的空闲时间达到设置的上限，Tomcat认为当前用户已经放弃了HttpSession
     * 此时Tomcat才会销毁掉HttpSession.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setHeader("Content-type", "text/html;charset=UTF-8");

        /**
         * 如果Tomcat发现当前用户，已经在服务端存在Session，则直接返回
         * 如果没有发现存在，则Tomcat会自动创建一个Session
         *
         * 如果当前用户的身份已经确定的时候使用
         */
        //req.getSession();

        /**
         * 与 req.getSession();一样
         */
        //req.getSession(true);

        /**
         * 如果Tomcat发现当前用户，已经在服务端存在Session，则直接返回
         * 如果没有发现存在，此时将返回null
         *
         * 如果当前用户的身份没有确定的时候使用
         */
        //req.getSession(false);


        String method = req.getParameter("method");

        if (method != null && method.equals("look")) {
            //获取session

            PrintWriter writer = resp.getWriter();
            HttpSession session = req.getSession(false);
            System.out.println(session);
            if (session != null) {
                Enumeration<String> names = session.getAttributeNames();
                while (names.hasMoreElements()) {
                    String name = names.nextElement();
                    System.out.println(name + " : " + session.getAttribute(name));
                    writer.write(name + " : " + session.getAttribute(name));
                    writer.write("<br/>");
                }
            } else {
                //重定向
                resp.sendRedirect("/Web04.html");
            }

        } else {

            //获取session
            HttpSession session = req.getSession();
            System.out.println(session);
            //获取参数name
            String name = req.getParameter("name");

            //判断session中是否存在name
            Object num = session.getAttribute(name);

            if (num == null) {
                //session.setAttribute("关键字",私人数据)
                //要求"关键字"和私人数据都不能为Null
                session.setAttribute(name, 1);
            } else {
                session.setAttribute(name, (int) num + 1);
            }
            resp.sendRedirect("/Web04.html");
        }


    }
}
