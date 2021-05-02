package com.springboot.ticket.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.stereotype.Component;

/**
 * @Classname TicketServiceImpl
 * @Date 2021/5/2 22:38
 * @Created by FallingStars
 * @Description
 */
@Service   //注意，导入的是dubbo中的service注解
@EnableDubbo
@Component
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "我和我的祖国";
    }
}
