package B;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname Filter04
 * @Date 2020/3/9 22:02
 * @Created by Falling Stars
 * @Description
 */
public class Filter04 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request,response);
    }
}
