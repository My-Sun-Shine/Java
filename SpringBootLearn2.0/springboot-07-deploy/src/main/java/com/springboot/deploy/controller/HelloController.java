package com.springboot.deploy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Classname HelloController
 * @Date 2021/5/2 15:57
 * @Created by FallingStars
 * @Description
 */
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
