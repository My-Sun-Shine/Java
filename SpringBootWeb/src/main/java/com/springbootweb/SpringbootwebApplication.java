package com.springbootweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;

@MapperScan(value = "com.springbootweb.mapper")
@SpringBootApplication
public class SpringbootwebApplication {
    @Autowired
    private MessageSource messageSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwebApplication.class, args);
    }

}
