package com.springbootweb.component;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @Classname MyErrorAttributes
 * @Date 2021/4/10 23:17
 * @Created by FallingStars
 * @Description 自定义错误信息处理
 */
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map=super.getErrorAttributes(webRequest, options);
        map.put("company","奥特之星");

        //异常处理器携带的数据
        Map<String,Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", 0);
        map.put("ext",ext);
        return map;
    }
}
