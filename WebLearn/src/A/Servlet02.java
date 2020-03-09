package A;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname Servlet02
 * @Date 2020/3/8 14:47
 * @Created by Falling Stars
 * @Description Servlet的生命周期
 */

/**
 * 1.【实例对象创建】：所有的Servlet实现类的实例对象，是不能由开发人员负责创建；只能由Tomcat负责创建
 * <p>
 * <p>
 * 2.【实例对象创建时机】：【默认情况下】只有当前第一个用户来访问这个Servlet实现类时Tomcat才会创建这个Servlet的实例对象
 * <p>
 * <p>
 * 【人工干预下】，可以要求Tomcat在启动时，就将Servlet的实例对象进行创建
 * web.xml
 * <servlet>
 * <servlet-name>AServlet02</servlet-name>
 * <servlet-class>A.Servlet02</servlet-class>
 * <load-on-startup>50</load-on-startup><!--只要填写一个大于0的整数-->
 * </servlet>
 * <p>
 * <load-on-startup>值，在大于0的情况下，值越小，加载的优先级就越高
 * <p>
 * <p>
 * 3.【实例对象被创建次数】：一个Servlet实现类只能能被创建一个实例对象。
 * <p>
 * <p>
 * 4.【实例对象何时被销毁】：在Tomcat将要关闭时，负责将所有的Servlet实现类的实例对象进行销毁
 */
//@WebServlet(urlPatterns = "/A/Servlet02")
public class Servlet02 extends HttpServlet {
    public Servlet02() {
        System.out.println("Servlet02构造方法被调用");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println("doGet...........Servlet02");
    }
}
