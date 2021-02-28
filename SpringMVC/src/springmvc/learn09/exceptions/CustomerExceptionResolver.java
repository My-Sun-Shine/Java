package springmvc.learn09.exceptions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


/**
 * 实现HandlerExceptionResolver接口的类叫做异常处理器。
 */
public class CustomerExceptionResolver implements HandlerExceptionResolver {

    /**
     * resolveException():处理异常。 当处理器类中抛出异常后，中央调度器执行resolveException()处理异常。
     * 这个方法的结果就是用户看到的结果（ModelAndView）
     * <p>
     * 参数：
     * Object handler：发生异常的处理器对象
     * Exception exception：发生的异常
     * 返回值：
     * ModelAndView: 异常处理结果的数据和视图
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception exception) {
        //处理异常。 把异常信息记录到数据库， 记录到日志文件。
        //当异常发生时，发送短信， 邮件给开发人员。
        //给用户一个友好的提示。
        ModelAndView mv = new ModelAndView();

        mv.addObject("msg", exception.getMessage());
        //判断异常的类型
        if (exception instanceof NameException) {
            //NameException
            //添加给用户的提示数据
            mv.addObject("tips", "用户你好，姓名必须是zs才可以！！！");
            mv.addObject("ex", exception);
            //指定视图
            mv.setViewName("learn09/nameError");

        } else if (exception instanceof AgeException) {
            //AgeException
            mv.addObject("tips", "用户你好，年龄不能大于50岁的");
            mv.addObject("ex", exception);
            mv.setViewName("learn09/ageError");
        } else {
            //处理其他异常
            mv.addObject("tips", "未知异常，请稍后重试!!!");
            mv.addObject("ex", exception);
            mv.setViewName("learn09/defaultError");
        }
        return mv;
    }

}
