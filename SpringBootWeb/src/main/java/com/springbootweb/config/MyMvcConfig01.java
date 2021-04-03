package com.springbootweb.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Classname MyMvcConfig01
 * @Date 2021/4/3 13:34
 * @Created by FallingStars
 * @Description
 */
@Configuration
public class MyMvcConfig01 implements WebMvcConfigurer {

    /**
     * 所有的WebMvcConfigurer组件都会一起起作用
     * @return
     */
    @Bean
    public WebMvcConfigurer configurer01() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
            }
        };
        return webMvcConfigurer;
    }
}
