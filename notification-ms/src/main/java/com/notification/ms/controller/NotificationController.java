package com.notification.ms.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.notification.ms.dto.NotificationEvent;
import com.notification.ms.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @KafkaListener(topics = "new-notification", groupId = "notification-group")
    public void deliverOrder(String event) throws JsonMappingException, JsonProcessingException {

        logger.info("Received notification event: {}", event);
        notificationService.processNotification(event);
    }

}
