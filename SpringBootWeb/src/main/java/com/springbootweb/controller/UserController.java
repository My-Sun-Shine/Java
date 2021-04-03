package com.springbootweb.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Classname UserController
 * @Date 2021/4/3 21:59
 * @Created by FallingStars
 * @Description
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public String login(String username, String password, Map<String, Object> map, HttpSession session) {
        if (StringUtils.isNotEmpty(username) && StringUtils.equals("123456", password)) {
            //登陆成功，防止表单重复提交，可以重定向到主页
            session.setAttribute("loginUser", username);
            return "redirect:/main.html";
        } else {
            //登陆失败
            map.put("msg", "用户名密码错误");
            return "index";
        }
    }
}
