package com.notification.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/send-mail")
    public String sendMail(){
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("pvcreation.mailing@gmail.com");
            message.setTo("pvcreation.mailing@gmail.com");
            message.setSubject("\uD83D\uDCE6 Shipment Details");
            message.setText("This is a sample email body for my first email!");

            mailSender.send(message);
            return "success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

