package springmvc.learn12.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Classname MyInterceptor
 * @Date 2021/2/28 17:56
 * @Created by FallingStars
 * @Description
 */

/**
 * 实现HandlerInterceptor接口的类叫做拦截器，能够拦截用户的请求。
 * 一个项目中可以有0或多个拦截器
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("拦截器111111 MyInterceptor的preHandle()");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView mv) throws Exception {
        System.out.println("拦截器111111 MyInterceptor的postHandle() ");

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("拦截器111111 MyInterceptor的afterCompletion()");

    }

}
