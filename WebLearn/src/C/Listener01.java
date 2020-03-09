package C;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Classname Listener01
 * @Date 2020/3/9 22:55
 * @Created by Falling Stars
 * @Description 监听器接口：监听全局作用域对象的创建与销毁
 */

/**
 * 用于对【域对象】的【生命周期】和【保存数据变化】进行监控
 * <p>
 * ServletContext application: 全局作用域对象(当前网站中所有的Servlet)
 * <p>
 * HttpSession session :会话作用域对象（同一个用户访问多个Servlet）
 * <p>
 * HttpServletRequest request :请求作用域对象（请求转发）
 */
public class Listener01 implements ServletContextListener {

    //在全局作用域对象将要被初始化时被触发
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("全局作用域对象将要被初始化时被触发");
    }

    //在全局作用域对象将要被销毁时被触发
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("全局作用域对象将要被销毁时被触发");
    }
}
