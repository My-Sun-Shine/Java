package com.crm.web.filter;

import com.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname LoginFilter
 * @Date 2020/3/22 15:40
 * @Created by Falling Stars
 * @Description 访问每个页面，判断是否登录
 */
public class LoginFilter implements Filter {
    /**
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("访问每个页面，判断是否登录");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        System.out.println(path);
        if (path.equals("/login.jsp") || path.equals("/settings/user/login.do")) {
            chain.doFilter(req, resp);
        } else {
            User user = (User) req.getSession().getAttribute("user");
            if (user != null) {
                chain.doFilter(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        }
    }
}
