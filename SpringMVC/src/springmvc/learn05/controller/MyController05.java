package springmvc.learn05.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springmvc.learn05.beans.Student;


/**
 * @Classname MyController
 * @Date 2020/4/7 22:49
 * @Created by Falling Stars
 * @Description
 */

/**
 * @Controller： 创建处理器类的对象，处理器能够处理用户的请求。 位置：在类定义的上面
 * 说明：创建处理器类对象， 默认是单例对象
 */
@Controller
@RequestMapping("/learn05")
public class MyController05 {
    /**
     * 逐个接收请求参数：
     * 要求处理器方法的形参名和请求中参数名一样。
     * <p>
     * 框架接收参数：
     * 1.首先使用request对象接收参数值
     * String strName  = request.getParameter("name");
     * String strAge = request.getParameter("age");
     * <p>
     * 2.当使用处理器适配器调用处理器方法时，按名称传入值
     * doSome(strName,Integer.parseInt(strAge))
     * <p>
     * 框架可以实现参数转换，把String-int, long ,float ,double
     */

    @RequestMapping(value = "/form1.do")
    public ModelAndView doForm1(Integer age, String name) {
        System.out.println("接收了form1.do的请求 name:" + name + "|age:" + age);

        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("myName", name);
        mv.addObject("myAge", age);
        //指定视图
        mv.setViewName("learn05/other");

        return mv;
    }

    /**
     * 逐个接收请求参数：
     * 请求中参数名和形参不一样，需要使用@RequestParam
     *
     * @RequestParam: 参数绑定，把请求参数和形参对应上 属性： 1，value 请求中参数名
     * 2, required boolean 默认是true
     * true:表示请求参数必须出现
     * false:可以不出现
     * 位置：在形参的定义前面
     * <p>
     * HTTP Status 400 - Required Integer parameter 'rage' is not present
     */
    @RequestMapping(value = "/form2.do")
    public ModelAndView doForm2(@RequestParam(value = "rage", required = false) Integer age,
                                @RequestParam(value = "rname", required = false) String name) {
        System.out.println("接收了form2.do的请求 name:" + name + "|age:" + age);

        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("myName", name);
        mv.addObject("myAge", age);
        //指定视图
        mv.setViewName("learn05/other");

        return mv;
    }

    /**
     * 使用java 对象接收请求的多个参数：
     * 要求： 请求中参数名和对象的属性名必须一样，按名称对应给属性赋值
     */
    @RequestMapping(value = "/form3.do")
    public ModelAndView doForm3(Student stu) {
        System.out.println("接收了form3.do的请求 name:" + stu.getName() + "|age:" + stu.getAge());

        ModelAndView mv = new ModelAndView();
        //调用Service处理业务
        //添加数据
        mv.addObject("myName", stu.getName());
        mv.addObject("myAge", stu.getAge());
        mv.addObject("myStudent", stu);
        //指定视图
        mv.setViewName("learn05/other");

        return mv;
    }
}
