package springmvc.learn02.controller;



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
 * @Controller：创建处理器类的对象，处理器能够处理用户的请求。
 *       位置：在类定义的上面
 * 说明：创建处理器类对象， 默认是单例对象
 */
@Controller
@RequestMapping("/learn02")
public class MyController {

    /**
     * @RequestMapping: 请求映射，把请求地址和方法绑定起来。让指定的请求由某个方法处理
     *                  看做是<url-pattern>的作用。
     *        属性： 1.value , 请求的uri地址。 使用“/”开头。 请求的uri地址是唯一值，不能重复。
     *        位置： 1.方法定义的上面
     *             2.在类定义的上面
     */
    @RequestMapping(value={"/some.do","/first.do","/a.do"})
    public ModelAndView doSome(){   //doGet
        System.out.println("接收了some.do的请求");
        //some.do的请求交给doSome()方法处理。 方法名和some.do没有关系。
        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("msg", "使用注解的springmvc项目");
        mv.addObject("fun", "doSome");
        //指定视图
        mv.setViewName("learn02/show");

        return mv;
    }

    @RequestMapping(value={"/other.do","/second.do","/b.do"})
    public ModelAndView doOther(){   //doGet
        System.out.println("接收了other.do的请求");
        //some.do的请求交给doSome()方法处理。 方法名和some.do没有关系。
        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("msg", "使用注解的springmvc项目");
        mv.addObject("fun", "doOther");
        //指定视图
        mv.setViewName("learn02/show");

        return mv;
    }


}
