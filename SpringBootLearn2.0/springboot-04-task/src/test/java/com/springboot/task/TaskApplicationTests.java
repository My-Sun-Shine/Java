package com.springboot.task;

import com.springboot.task.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class TaskApplicationTests {
    String path = "D:\\GitHub\\Java\\SpringBootLearn2.0\\springboot-04-task\\src\\main\\resources\\";

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    void contextLoads() {
    }

    /**
     * 测试异步任务
     */
    @Test
    void test01() {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject("通知-今晚开会");
        message.setText("今晚7:30开会");

        message.setTo("1399237176@qq.com");
        message.setFrom("1399237176@qq.com");

        mailSender.send(message);
    }

    @Test
    public void test02() throws Exception {
        //1、创建一个复杂的消息邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //邮件设置
        helper.setSubject("通知-今晚开会");
        helper.setText("<b style='color:red'>今天 7:30 开会</b>", true);

        helper.setTo("1399237176@qq.com");
        helper.setFrom("1399237176@qq.com");

        //上传文件
        helper.addAttachment("1.jpg", new File(path + "1.jpg"));
        helper.addAttachment("2.jpg", new File(path + "2.jpg"));

        mailSender.send(mimeMessage);

    }
}
