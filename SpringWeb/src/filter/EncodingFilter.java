package filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname EncodingFilter
 * @Date 2020/3/21 0:09
 * @Created by Falling Stars
 * @Description 字符编码过滤器
 */

public class EncodingFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("字符编码过滤器");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(request, response);
    }
}
