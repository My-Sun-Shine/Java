package com.springbootweb.controller;

import com.springbootweb.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname HelloController
 * @Date 2021/4/10 22:37
 * @Created by FallingStars
 * @Description
 */
@Controller
@RequestMapping("hello")
public class HelloController {
    @ResponseBody
    @RequestMapping("/m1")
    public String m1(@RequestParam("user") String user) {
        if (user.equals("admin")) {
            throw new UserNotExistException();
        }
        return "Hello World";
    }
}
