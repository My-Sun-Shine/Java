package A;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet06
 * @Date 2020/3/9 10:59
 * @Created by Falling Stars
 * @Description ServletContext接口（全局作用域接口/上下文环境接口）
 */
@WebServlet(urlPatterns = "/A/Servlet06")
public class Servlet06 extends HttpServlet {
    /**
     * 【全局作用域对象】作用为当前网站所有的Servlet提供共享数据
     * <p>
     * 【全局作用域对象】生命周期：
     * <p>
     * 1）【全局作用域对象】是由Tomcat负责创建的
     * 2）【全局作用域对象】是在Tomcat启动时被创建
     * 3） 一个网站中只能存在一个【全局作用域对象】
     * 4）【全局作用域对象】是在Tomcat将要关闭时，由Tomcat负责销毁
     * <p>
     * 【全局作用域对象】保存的共享数据，实在服务端计算机的内存中
     * 所以【全局作用域对象】保存的数据量越大，服务端内存消耗越大。
     * 导致服务端计算机运行速度会大幅下降
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        ServletContext application = req.getServletContext();
        String driver = application.getInitParameter("mysqlDriver");
        String url = application.getInitParameter("mysqlurl");
        String username = application.getInitParameter("mysqlusername");
        String password = application.getInitParameter("mysqlpassword");
        System.out.println(driver);
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);

        /*
        在项目运行时，由某一个Servlet产生数据，可以交给【全局作用域对象】
        把它共享给当前网站中的所有的Servlet
        * */
        application.setAttribute("data1", driver);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
