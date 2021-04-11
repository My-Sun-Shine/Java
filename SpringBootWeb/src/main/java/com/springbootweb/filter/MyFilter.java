package com.springbootweb.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Classname MyFilter
 * @Date 2021/4/11 18:30
 * @Created by FallingStars
 * @Description
 */
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter process...");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
