package com.springboot.ticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、引入dubbo和zkclient相关依赖
 * 2、配置dubbo的扫描包和注册中心地址
 * 3、使用@com.alibaba.dubbo.config.annotation.Service发布服务
 */
@SpringBootApplication
public class ProviderTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderTicketApplication.class, args);
    }

}
