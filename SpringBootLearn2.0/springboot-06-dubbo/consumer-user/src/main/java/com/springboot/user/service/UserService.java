package com.springboot.user.service;

import com.springboot.ticket.service.TicketService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Classname UserService
 * @Date 2021/5/2 22:53
 * @Created by FallingStars
 * @Description
 */
@Service   //为springframework的service注解
public class UserService {
    @Reference
    TicketService ticketService;

    public void hello(){
        String ticket = ticketService.getTicket();
        System.out.println("秒杀掉票了！"+ticket);
    }

}
