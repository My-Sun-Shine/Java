package com.mxw.springcloud.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AccountConfig
 * @Date 2021/7/13 9:16
 * @Created by FallingStars
 * @Description
 */
@Log4j2
@RestController
@RefreshScope//表示实时刷新
public class AccountConfig {
    @Value("${account.id}")
    private String id;
    @Value("${account.name}")
    private String name;

    @GetMapping("/getAccount")
    public Map<String, Object> getAccount() {
        Map<String, Object> account = new HashMap<>();
        log.info("id==" + id + ",name==" + name);
        account.put("id", id);
        account.put("name", name);
        log.info("---------获取配置的客户信息account{}----", account);
        return account;
    }
}
