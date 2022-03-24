package com.mxw.springcloud.service;

import com.mxw.springcloud.fallback.AccountServiceBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Classname AccountService
 * @Date 2021/7/13 9:50
 * @Created by FallingStars
 * @Description
 */
@FeignClient(value = "cloud-account", fallback = AccountServiceBack.class)
//@FeignClient(value = "cloud-account")
public interface AccountService {
    //获取客户信息
    @GetMapping("/getAccount")
    public Map<String, Object> getAccount();
}
