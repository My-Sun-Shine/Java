package com.springbootweb.config;


import com.springbootweb.component.LoginHandlerInterceptor;
import com.springbootweb.component.MyLocaleResolver;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Classname MyMvcConfig01
 * @Date 2021/4/3 13:34
 * @Created by FallingStars
 * @Description
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 所有的WebMvcConfigurer组件都会一起起作用
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer configurer01() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index");
                registry.addViewController("/index.html").setViewName("index");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //添加自定义的拦截器，并且添加拦截路径，以及排除不需要拦截的路径
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html", "/", "/user/login","/error","/hello/**");
            }
        };
        return webMvcConfigurer;
    }


    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
