package com.mxw.springcloud.fallback;

import com.mxw.springcloud.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname AccountServiceBack
 * @Date 2021/7/13 10:29
 * @Created by FallingStars
 * @Description
 */
@Log4j2
@Service
public class AccountServiceBack implements AccountService {
    @Override
    public Map<String, Object> getAccount() {
        Map<String, Object> account = new HashMap<>();
        account.put("msg", "服务熔断了，本地处理");
        return account;
    }
}
