package com.mxw.springcloud.controller;

import com.mxw.springcloud.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AccountConsumerController
 * @Date 2021/7/13 8:50
 * @Created by FallingStars
 * @Description
 */
@Log4j2
@RestController
public class AccountConsumerController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/getAccount")
    public Map<String, Object> getAccount() {
        Map<String, Object> account = new HashMap<>();
        log.info("---------消费者开始------------");
        account = accountService.getAccount();
        log.info("---------消费者结束--------account{}----", account);
        return account;

    }
}
