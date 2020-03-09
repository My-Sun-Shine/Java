package B;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname Filter03
 * @Date 2020/3/9 20:57
 * @Created by Falling Stars
 * @Description FilterChain接口
 */
public class Filter03 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String b = request.getParameter("b");
        if (b != null && b.equals("b")) {
            chain.doFilter(request, response);
        }
        else{
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("Not OK............b");
        }
    }
}
