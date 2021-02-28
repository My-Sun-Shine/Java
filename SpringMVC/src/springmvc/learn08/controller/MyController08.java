package springmvc.learn08.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @Classname MyController
 * @Date 2020/4/7 22:49
 * @Created by Falling Stars
 * @Description
 */

/**
 * @Controller：创建处理器类的对象，处理器能够处理用户的请求。 位置：在类定义的上面
 * 说明：创建处理器类对象， 默认是单例对象
 */
@Controller
@RequestMapping("/learn08")
public class MyController08 {

    /**
     * 处理器方法返回ModelAndView实现重定向到视图页面
     * 语法：mv.setViewName("redirect:视图的完整路径")
     * <p>
     * redirect:的特点是不和视图解析器一同工作。
     * <p>
     * 重定向：
     * 1.不能访问/WEB-INF下面的资源
     * 2.框架可以把Model中简单类型的数据转为String,作为get请求的参数传递。
     * 3.在目标的页面中，可以使用${param.参数名} 获取参数值。
     */
    @RequestMapping("/doSome.do")
    public ModelAndView doSome(Integer age, String name) {
        System.out.println("======接收了doSome.do的请求 name:" + name + "|age:" + age);
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", name);
        mv.addObject("age", age);
        //http://localhost:8080/view/show.jsp?name=zhangsan&age=28
        mv.setViewName("redirect:/show.jsp");
        return mv;
    }

    /**
     * 处理器方法返回String，使用redirect重定向到视图页面
     * 语法：return "redirect:视图完整路径"
     * <p>
     * redirect:的特点是不和视图解析器一同工作。
     */
    @RequestMapping("/doOther.do")
    public String doOther(Integer age, String name) {
        System.out.println("======接收了doOther.do的请求 name:" + name + "|age:" + age);
        return "redirect:/show.jsp";
    }

    /**
     * 重定向到其他的处理器，由其他的处理器继续处理用户的请求
     */
    @RequestMapping("/doRedirect.do")
    public ModelAndView doRedirect(String name, Integer age) {
        System.out.println("======接收了doRedirect.do的请求 name:" + name + "|age:" + age);
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", name);
        mv.addObject("age", age);
        //http://localhost:8080/learn08/doProcess.do?name=zhangsan&age=28
        mv.setViewName("redirect:/learn08/doProcess.do");
        return mv;
    }

    @RequestMapping("/doProcess.do")
    public String doProcess(String name, Integer age) {
        System.out.println("======接收了doProcess.do的请求 name:" + name + "|age:" + age);
        return "learn08/show";
    }
}
