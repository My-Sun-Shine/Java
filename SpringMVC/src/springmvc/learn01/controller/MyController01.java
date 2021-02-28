package springmvc.learn01.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Classname MyController
 * @Date 2020/4/7 22:49
 * @Created by Falling Stars
 * @Description
 */
public class MyController01 implements Controller {
    /**
     * 在SpringMVC配置式开发中，需要实现接口Controller，作为处理器类使用
     * 处理器类是处理用户请求的。也叫做后端处理器（控制器）
     * <p>
     * handleRequest():处理用户的请求，能够处理请求的方法叫做处理器方法
     * handleRequest()方法看做是Servlet中的doGet, doPost.
     * <p>
     * 返回值：ModelAndView
     * Model：表示数据的
     * View:指定视图的
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @return a ModelAndView to render, or {@code null} if handled directly
     * @throws Exception in case of errors
     */
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //调用Servlet，处理请求，把结果对象放入视图中显示

        //把数据保存起来,框架对Model中的数据是放入到request作用域。
        //request.setAttribute("msg", "Hello SpringMVC");
        modelAndView.addObject("msg", "Hello SpringMVC");

        //指定视图，显示数据；框架对视图的处理是 forward
        //request.getRequestDispatcher("/WEB-INF/view/learn01/show.jsp").forward(request,response)
        //modelAndView.setViewName("/WEB-INF/view/learn01/show.jsp");

        //视图的逻辑名称， 就是文件名
        //框架把视图解析器的 前缀 + 逻辑名称 + 后缀 = 视图的完整路径
        modelAndView.setViewName("learn01/show");

        //把数据和视图封装到ModelAndView. 在后面的代码中，执行request.setAttibute() ,forward()
        return modelAndView;
    }
}
