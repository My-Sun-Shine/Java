package com.springbootlearn;

import com.springbootlearn.bean.Person;
import com.springbootlearn.bean.Person01;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringBootLearnApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    Person person;
    @Autowired
    Person01 person01;

    @Autowired
    ApplicationContext ioc;

    @Test
    void contextLoads() {
    }

    /**
     * 测试ConfigurationProperties和@Value获取配置文件信息
     */
    @Test
    void TestPerson() {
        System.out.println(person.toString());
        System.out.println(person01.toString());
    }

    /**
     * 测试@ImportResource
     */
    @Test
    void TestImportResource() {
        System.out.println(ioc.containsBean("Test01"));
    }

    /**
     * 测试@Configuration和@Bean
     */
    @Test
    void TestConfigurationBean() {
        System.out.println(ioc.containsBean("test02"));
    }

    @Test
    void TestSLF4J() {
        //日志的级别；
        //由低到高   trace<debug<info<warn<error
        //可以调整输出的日志级别；日志就只会在这个级别以以后的高级别生效
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；root级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");

    }
}
