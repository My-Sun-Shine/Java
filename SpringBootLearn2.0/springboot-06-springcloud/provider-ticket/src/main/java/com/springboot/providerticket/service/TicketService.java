package com.springboot.providerticket.service;

import org.springframework.stereotype.Service;

/**
 * @Classname TicketService
 * @Date 2021/5/2 23:34
 * @Created by FallingStars
 * @Description
 */
@Service
public class TicketService {

    public String getTicket() {
        System.out.println("8002");
        return "《厉害了，我的国》";
    }
}
