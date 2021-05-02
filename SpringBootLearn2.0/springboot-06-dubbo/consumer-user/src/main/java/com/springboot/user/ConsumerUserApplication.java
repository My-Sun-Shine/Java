package com.springboot.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1、引入dubbo和zkclient相关依赖
 * 2、配置dubbo的扫描包和注册中心地址
 * 3、引用服务@com.alibaba.dubbo.config.annotation.Reference
 */
@SpringBootApplication
public class ConsumerUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerUserApplication.class, args);
    }

}
