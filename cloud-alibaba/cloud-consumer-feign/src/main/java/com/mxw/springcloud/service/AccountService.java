package com.mxw.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Classname AccountService
 * @Date 2021/7/13 8:48
 * @Created by FallingStars
 * @Description
 */
@FeignClient("cloud-account")
public interface AccountService {

    @GetMapping("/getAccount")
    public Map<String,Object> getAccount();
}
