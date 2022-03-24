package com.mxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Classname CloudConfigApplication
 * @Date 2021/7/13 9:18
 * @Created by FallingStars
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册
public class CloudConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudConfigApplication.class, args);
    }
}
