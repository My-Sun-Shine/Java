package B;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname Filter02
 * @Date 2020/3/9 20:57
 * @Created by Falling Stars
 * @Description FilterChain接口
 */

/**
 * 1）在实际开发过程中，一种资源文件往往匹配多个过滤器；
 * 那么当请求发送时，由哪一个过滤器首先执行，哪一个过滤器再执行，就是有FilterChain
 * <p>
 * <p>
 * 2）开发人员习惯于将FilterChain修饰的对象称为【过滤器链条对象】
 * <p>
 * <p>
 * 3）【过滤器链条对象】本质上就是一个数组，在这个数组保存过滤器对象，并根据过滤器在数组中位置，决定过滤器出场顺序
 * <p>
 * <p>
 * 4）当某一个过滤器对当前请求放行时，需要将拦截的【请求对象】和【响应对象】交还给【过滤器链条对象】
 * 由【过滤器链条对象】调用下一个位置过滤器进行再次检测
 * <p>
 * <p>
 * 5）如果现在放行的过滤器是最后一个过滤器，此时【过滤器链条对象】将【请求对象】和【响应对象】交还给Tomcat,
 * 由Tomcat调用被请求资源给用户
 * <p>
 * <p>
 * 6）过滤器在过滤器链条对象出场顺序与过滤器配置文件web.xml声明顺序有关
 */
public class Filter02 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String a = request.getParameter("a");
        if (a != null && a.equals("a")) {
            chain.doFilter(request, response);
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("Not OK............a");
        }
    }
}
