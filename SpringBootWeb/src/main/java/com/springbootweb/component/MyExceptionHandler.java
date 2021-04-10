package com.springbootweb.component;

import com.springbootweb.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname MyExceptionHandler
 * @Date 2021/4/10 23:08
 * @Created by FallingStars
 * @Description 异常处理器自定义
 */
@ControllerAdvice
public class MyExceptionHandler {
    /**
     * 第一种写法没有自适应效果，都返回json数据
     *
     * @param e
     * @return
     */
    /*
    @ExceptionHandler(UserNotExistException.class)
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "user.notExist");
        map.put("message", e.getMessage());
        return map;
    }*/

    /**
     * 转发到/error进行自适应响应效果处理
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        //传入我们自己的错误状态码  4xx 5xx，否则就不会进入定制错误页面的解析流程
        /**
         * Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
         */
        request.setAttribute("javax.servlet.error.status_code", 455);
        map.put("code", "user.notExist");
        map.put("message", e.getMessage());

        //将额外数据放到request域中
        request.setAttribute("ext", map);
        //转发到/error
        return "forward:/error";
    }
}

