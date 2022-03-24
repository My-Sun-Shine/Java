package com.mxw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname ConsumerApplication
 * @Date 2021/7/12 18:09
 * @Created by FallingStars
 * @Description
 */
@SpringBootApplication
@EnableDiscoveryClient //开启服务注册
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    /**
     * 创建服务调用的对象
     *
     * @LoadBalanced 添加此注解后，RestTemplate就具有了ribben客户端负载均衡能力
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
