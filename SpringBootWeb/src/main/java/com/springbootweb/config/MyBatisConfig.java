package com.springbootweb.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @Classname MyBatisConfig
 * @Date 2021/4/14 21:56
 * @Created by FallingStars
 * @Description
 */
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(Configuration configuration) {
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
