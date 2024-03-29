package com.springbootlearn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname HelloController
 * @Date 2021/3/22 21:50
 * @Created by FallingStars
 * @Description
 */
@Controller
@ResponseBody
@RequestMapping("/learn001/Hello")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
