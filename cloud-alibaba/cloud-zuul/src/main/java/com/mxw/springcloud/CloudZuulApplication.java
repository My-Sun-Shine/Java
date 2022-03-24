package com.mxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Classname CloudZuulApplication
 * @Date 2021/7/19 10:24
 * @Created by FallingStars
 * @Description
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class CloudZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudZuulApplication.class, args);
    }
}
