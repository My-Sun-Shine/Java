package com.mxw.springcloud.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * @Classname AccountConsumerController
 * @Date 2021/7/12 18:02
 * @Created by FallingStars
 * @Description
 */

@Log4j2
@RestController
public class AccountConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/getConsumerAccount")
    public Map<String, Object> getAccount() {
        Map<String, Object> account = new HashMap<>();
        log.info("---------消费者开始------------");
        //调用服务
        account = restTemplate.getForObject("http://cloud-account/getAccount", HashMap.class);
        log.info("---------消费者结束--------account{}----", account);
        return account;

    }

}
