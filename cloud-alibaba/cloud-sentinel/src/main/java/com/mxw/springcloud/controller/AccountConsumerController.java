package com.mxw.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mxw.springcloud.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AccountConsumerController
 * @Date 2021/7/13 9:56
 * @Created by FallingStars
 * @Description
 */
@Log4j2
@RestController
public class AccountConsumerController {
    @Resource
    private AccountService accountService;

    @GetMapping("/getAccount")
    //通过@SentinelResource中添加blockHandler参数，给其添加自定义异常方法
    //@SentinelResource(value = "/getAccount", blockHandler = "handleException")
    @SentinelResource(value = "/getAccount")
    public Map<String, Object> getAccount() {
        Map<String, Object> account = new HashMap<>();
        log.info("---------消费者开始------------");
        account = accountService.getAccount();
        log.info("---------消费者结束--------account{}----", account);
        return account;
    }

    /**
     * 自定义异常策略
     * 返回值和参数要跟目标函数一样，参数可以追加BlockException
     */
    public Map<String, Object> handleException(BlockException exception) {
        try {
            throw exception;
        } catch (BlockException e) {
            e.printStackTrace();
        }
        Map<String, Object> account = new HashMap<>();
        log.info("flow exception{}", exception.getClass().getCanonicalName());
        account.put("msg", "别点了，达到阀值了，稍后再试");
        return account;
    }
}
