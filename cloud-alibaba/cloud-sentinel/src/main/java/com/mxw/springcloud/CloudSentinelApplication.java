package com.mxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname CloudSentinelApplication
 * @Date 2021/7/13 9:58
 * @Created by FallingStars
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册
@EnableFeignClients //开启feign客户端
public class CloudSentinelApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudSentinelApplication.class, args);
    }
}
