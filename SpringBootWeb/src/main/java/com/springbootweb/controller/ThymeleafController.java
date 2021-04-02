package com.springbootweb.controller;

import com.springbootweb.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Classname ThymeleafController
 * @Date 2021/4/2 21:59
 * @Created by FallingStars
 * @Description
 */
@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @GetMapping("/m01")
    public String m01(Map<String, Object> map, HttpServletRequest request) {
        map.put("hello", "Hello World!");
        HttpSession session = request.getSession();
        UserInfo user = new UserInfo();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setNationality("nationality");
        session.setAttribute("user", user);
        return "thymeleaf01";
    }


}

