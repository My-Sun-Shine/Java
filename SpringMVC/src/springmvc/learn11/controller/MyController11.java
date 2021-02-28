package springmvc.learn11.controller;


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
@RequestMapping("/learn11")
public class MyController11 {
    @RequestMapping(value = "/some.do")
    //定义方法处理请求。 这是处理器方法
    public ModelAndView doSome(Integer age, String name) {
        System.out.println("执行了MyController11处理器的doSome()");
        //some.do的请求交给doSome()方法处理。 方法名和some.do没有关系。
        ModelAndView mv = new ModelAndView();
        mv.addObject("myName", name);
        mv.addObject("myAge", age);
        //指定视图
        mv.setViewName("learn11/show");

        return mv;
    }

}
