package com.mxw.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AccountController
 * @Date 2021/7/12 17:23
 * @Created by FallingStars
 * @Description
 */
@Log4j2
@RestController
public class AccountController {
    @GetMapping("/getAccount")
    public Map<String, Object> getAccount() {
        log.info("获取客户信息服务, 服务正常启动");
        Map<String, Object> account = new HashMap<>();
        account.put("id", "1");
        account.put("name", "诗颖");
        log.info("id==" + account.get("id") + ",name==" + account.get("name"));
        return account;
    }
}
