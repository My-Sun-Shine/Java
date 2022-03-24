package com.mxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname AccountApplication
 * @Date 2021/7/12 17:41
 * @Created by FallingStars
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册
public class AccountApplication {
    public static void main(String [] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
}
