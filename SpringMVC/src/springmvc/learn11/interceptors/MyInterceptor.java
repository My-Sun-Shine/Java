package springmvc.learn11.interceptors;

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

    private long btime;

    /**
     * preHandle:预处理方法， 在请求之前先执行的方法。 可以对请求做预先的判断和处理。
     * 参数：
     * Object handler：处理器对象。
     * <p>
     * 返回值：boolean
     * true: 拦截可以让请求通过。说明请求是正确的，合法的。可以执行afterCompletion()
     * <p>
     * 拦截器MyInterceptor的preHandle()
     * 执行了MyController处理器的doSome()
     * 拦截器MyInterceptor的postHandle()
     * 拦截器MyInterceptor的afterCompletion()
     * <p>
     * false:请求被阶段。 拦截器认为请求是不正确的。 不能被处理器处理。
     * 拦截器的afterCompletion()不会执行。
     * <p>
     * 拦截器MyInterceptor的preHandle()
     * <p>
     * <p>
     * preHandle()可以看做是过滤器的doFilter() :实现登陆用户检查，权限的认证， 日志的输出。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        btime = System.currentTimeMillis();
        System.out.println("拦截器MyInterceptor的preHandle()");
        return true;
    }

    /**
     * postHandle：后处理方法，在处理器方法执行之后执行的。能够获取到处理器方法的执行结果（ModelAndView）
     * 可以对执行做修改，修改数据，修改视图。 是对原来的执行结果的二次修正。
     * 参数：
     * Object handler：处理器对象。
     * ModelAndView mv：处理器方法的执行结果
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
            throws Exception {
        System.out.println("拦截器MyInterceptor的postHandle() mv:" + mv + "|handler:" + handler);
        //修改mv的数据和视图
        if (mv != null) {
            //修改数据
            mv.addObject("date", new Date());
            //修改视图
            mv.setViewName("learn11/other");
        }
    }

    /**
     * afterCompletion:最后执行的方法，在视图对象（View）处理完成后执行的。
     * afterCompletion执行意味请求处理完成了。可以把请求过程中占用的资源在这里释放。
     * 他是程序最后执行的方法。
     * 参数：
     * Object handler：处理器对象。
     * Exception ex:异常对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器MyInterceptor的afterCompletion()");
        //结束时间
        long etime = System.currentTimeMillis();
        System.out.println("请求的处理时长：" + (etime - btime));
    }
}
