package springmvc.learn04.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/learn04")
public class MyController04 {

    /**
     * @RequestMapping: 请求映射，把请求地址和方法绑定起来。让指定的请求由某个方法处理，看做是<url-pattern>的作用
     * <p>
     * 属性：
     * 1.value , 请求的uri地址。 使用“/”开头。 请求的uri地址是唯一值，不能重复。
     * 2.method ,表示请求的方式， 使用RequestMethod类的常量值。例如get请求方式RequestMethod.GET
     * <p>
     * 位置：
     * 1.方法定义的上面
     * 2.在类定义的上面
     */
    //指定some.do的请求，只能使用get方式
    @RequestMapping(value = "/some.do", method = RequestMethod.GET)
    public ModelAndView doSome() {   //doGet
        System.out.println("接收了some.do的请求");
        //some.do的请求交给doSome()方法处理。 方法名和some.do没有关系。
        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("msg", "使用注解的springmvc项目");
        mv.addObject("fun", "doSome");
        //指定视图
        mv.setViewName("learn04/show");

        return mv;
    }

    //指定other.do只能使用 post请求方式
    @RequestMapping(value = "/other.do", method = RequestMethod.POST)
    public ModelAndView doOther() {   //doGet
        System.out.println("接收了other.do的请求");
        //some.do的请求交给doSome()方法处理，方法名和some.do没有关系
        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("msg", "使用注解的springmvc项目");
        mv.addObject("fun", "doOther");
        //指定视图
        mv.setViewName("learn04/show");

        return mv;
    }

    //不指定first.do的请求方式， 那么访问first.do就没有限制
    @RequestMapping(value = "/first.do")
    public ModelAndView doFirst() {
        System.out.println("接收了first.do的请求");

        // some.do的请求交给doSome()方法处理，方法名和some.do没有关系
        ModelAndView mv = new ModelAndView();
        // 调用Service处理业务
        // 添加数据
        mv.addObject("msg", "使用注解的springmvc项目");
        mv.addObject("fun", "doFirst");
        // 指定视图
        mv.setViewName("learn04/show");

        return mv;
    }
}
