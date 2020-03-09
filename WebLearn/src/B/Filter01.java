package B;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname Filter01
 * @Date 2020/3/9 17:22
 * @Created by Falling Stars
 * @Description Filter接口：过滤器
 */

/**
 * [过滤器的生命周期]：
 * <p>
 * 1.【创建时机】：Tomcat启动时，负责创建过滤器对象。
 * 2.【创建个数】：Tomcat运行时，一个过滤器实现类只能被创建一个实例对象。
 * 3.【消亡时机】：Tomcat关闭时，由Tomcat负责销毁所有的过滤器。
 */
public class Filter01 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 来判断被拦截的请求是否合法
     * 从而决定是否拒绝该请求
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //从拦截的【请求对象】读取用户的年龄
        String name = request.getParameter("name");
        if (name == null || !name.equals("admin")) {//本次请求是违法的

            //拒绝用户的请求，通过拦截的响应对象拒绝
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("你不是管理员");
        } else {//合法
            chain.doFilter(request, response);//对拦截的请求放行处理。
        }
    }

    @Override
    public void destroy() {

    }
}
