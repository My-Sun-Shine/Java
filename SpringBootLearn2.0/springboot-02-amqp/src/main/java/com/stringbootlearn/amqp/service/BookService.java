package com.stringbootlearn.amqp.service;

import com.stringbootlearn.amqp.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @Classname BookService
 * @Date 2021/4/27 23:13
 * @Created by FallingStars
 * @Description 消息监听
 */
@Service
public class BookService {
    @RabbitListener(queues = "atguigu.news")
    public void receive(Book book) {
        System.out.println("收到消息：" + book);
    }

    @RabbitListener(queues = "atguigu")
    public void receive02(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
