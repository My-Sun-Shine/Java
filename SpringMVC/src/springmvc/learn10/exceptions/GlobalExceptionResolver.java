package springmvc.learn10.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 使用GlobalExceptionResolver集中处理异常。
 *
 * @ControllerAdvice: 处理器增强。
 * 目的是给处理器类中的方法，增强功能， 一般是增强异常处理。
 * 位置：在类定义的上面，这个类集中处理异常。
 * <p>
 * 当处理类抛出异常时，中央调度器会找@ControllerAdvice修饰的类，
 * 执行类中的@ExceptionHandler修饰的方法
 */
@ControllerAdvice
public class GlobalExceptionResolver {

    /**
     * 在处理器类中，定义方法处理异常。方法的上面加入@ExceptionHandler
     *
     * @ExceptionHandler 属性： value,异常的子类，表示处理的异常类型
     */
    //处理NameException
    @ExceptionHandler(value = NameException.class)
    public ModelAndView doNameException(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("tips", "@ControllerAdvice使用注解处理NameException");
        mv.addObject("ex", e);
        mv.setViewName("learn10/nameError");
        return mv;
    }

    //处理AgeException
    @ExceptionHandler(value = AgeException.class)
    public ModelAndView doAgeException(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("tips", "@ControllerAdvice使用注解处理AgeException");
        mv.addObject("ex", e);
        mv.setViewName("learn10/ageError");
        return mv;
    }

    //处理其他异常
    @ExceptionHandler
    public ModelAndView doOtherException(Exception e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("tips", "@ControllerAdvice使用注解处理OtherException");
        mv.addObject("ex", e);
        mv.setViewName("learn10/defaultError");
        return mv;
    }
}
