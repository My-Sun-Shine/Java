package springmvc.learn10.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import springmvc.learn10.exceptions.AgeException;
import springmvc.learn10.exceptions.MyUserException;
import springmvc.learn10.exceptions.NameException;


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
@RequestMapping("/learn10")
public class MyController10 {
    @RequestMapping(value = "/some.do")
    public ModelAndView doSome(Integer age, String name) throws MyUserException {
        System.out.println("接收了some.do的请求 name:" + name + "|age:" + age);
        // some.do的请求交给doSome()方法处理。 方法名和some.do没有关系。
        ModelAndView mv = new ModelAndView();
        if (!"zs".equals(name)) {
            throw new NameException("姓名不正确，必须是zs");
        }

        if (age == null || age > 50) {
            throw new AgeException("年龄比较大了！！！");
        }
        // 调用Service处理业务
        mv.addObject("myName", name);
        mv.addObject("myAge", age);
        // 指定视图
        mv.setViewName("learn10/show");

        return mv;
    }

}
