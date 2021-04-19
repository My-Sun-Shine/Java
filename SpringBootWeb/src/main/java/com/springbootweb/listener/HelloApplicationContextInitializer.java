package com.springbootweb.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Classname HelloApplicationContextInitializer
 * @Date 2021/4/19 22:41
 * @Created by FallingStars
 * @Description
 */

public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("ApplicationContextInitializer...initialize..." + configurableApplicationContext);
    }
}
