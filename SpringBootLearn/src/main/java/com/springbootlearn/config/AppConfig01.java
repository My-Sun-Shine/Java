package com.springbootlearn.config;

import com.springbootlearn.bean.Test02;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname AppConfig01
 * @Date 2021/3/26 23:22
 * @Created by FallingStars
 * @Description @Configuration：指明当前类是一个配置类
 * 就是来替代之前的Spring配置文件，在配置文件中用<bean><bean/>标签添加组件
 */
@Configuration
public class AppConfig01 {

    /**
     * 使用@Bean给容器中添加组件
     * 将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名
     *
     * @return
     */
    @Bean
    public Test02 test02() {
        System.out.println("配置类@Bean为容器添加组件Test02");
        return new Test02();
    }
}
