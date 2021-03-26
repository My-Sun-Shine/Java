package com.springbootlearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 *  @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:beans.xml"})
public class SpringBootLearnApplication {

    public static void main(String[] args) {
        // Spring应用启动起来
        SpringApplication.run(SpringBootLearnApplication.class, args);
    }

}
