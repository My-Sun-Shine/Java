package springmvc.learn07.controller;


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
@RequestMapping("/learn07")
public class MyController07 {

    /**
     * 处理器方法返回ModelAndView实现转发到视图页面
     * 语法：mv.setViewName("forward:视图的完整路径")
     * <p>
     * forward:的特点是不和视图解析器一同工作。
     */
    @RequestMapping("/doSome.do")
    public ModelAndView doSome() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "zhangSan");
        mv.addObject("age", 23);
        mv.setViewName("forward:/WEB-INF/view/learn07/show.jsp");
        return mv;
    }

    /**
     * 处理器方法返回String，使用forward转发到视图页面
     * 语法：return "forward:视图完整路径"
     * <p>
     * forward:的特点是不和视图解析器一同工作。
     */
    @RequestMapping("/doOther.do")
    public String doOther() {
        return "forward:/WEB-INF/view/learn07/show.jsp";
    }

    /**
     * 转发到其他的处理器，由其他的处理器继续处理用户的请求
     */
    @RequestMapping("/dispatch.do")
    public ModelAndView dispatchRequest(String name, Integer age) {
        System.out.println("============dispatch========= name:" + name + "|age:" + age);
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", name);
        mv.addObject("age", age);
        mv.setViewName("forward:/learn07/doProcess.do");
        return mv;
    }

    @RequestMapping("/doProcess.do")
    public String doProcess(String name, Integer age) {
        System.out.println("============doProcess========= name:" + name + "|age:" + age);
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", name);
        mv.addObject("age", age);
        mv.setViewName("learn07/show");
        //return mv;
        return "learn07/show";
    }
}
