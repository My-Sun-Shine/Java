package com.mxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname FeignConsumerApplication
 * @Date 2021/7/13 8:52
 * @Created by FallingStars
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册
@EnableFeignClients //开启feign客户端
public class FeignConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerApplication.class, args);
    }
}
