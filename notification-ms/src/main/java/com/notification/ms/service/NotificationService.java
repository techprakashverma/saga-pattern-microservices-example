package com.notification.ms.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.ms.dto.CustomerOrder;
import com.notification.ms.dto.NotificationEvent;
import com.notification.ms.exception.EmailSendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void processNotification(String event) {
        NotificationEvent notificationEvent = parseEvent(event);
        CustomerOrder order = notificationEvent.getOrder();
        sendEmail(order);
    }

    private NotificationEvent parseEvent(String event) {
        try {
            return objectMapper.readValue(event, NotificationEvent.class);
        } catch (Exception e) {
            throw new EmailSendException("Failed to parse notification event", e);
        }
    }

    private void sendEmail(CustomerOrder order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pvcreation.mailing@gmail.com");
        //message.setTo(order.getEmail());
        message.setTo("pvcreation.mailing@gmail.com");
        message.setSubject("📦 Shipment Details");
        message.setText("Dear Customer," +
                "\nGood news! Your order " + order.getItem() + " will be shipped soon." +
                "\nTo this address: " + order.getAddress() +
                "\nExpected Delivery Date: " + LocalDate.now().plusDays(4) +
                "\nThank you for shopping with us." +
                "\n\nBest regards,\nTeam pvcreation");

        mailSender.send(message);
        logger.info("Email sent successfully to {}", order.getEmail());
    }
}
