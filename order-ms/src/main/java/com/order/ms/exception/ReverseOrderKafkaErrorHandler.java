package com.order.ms.exception;

import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Component("reverseOrderKafkaErrorHandler")
public class ReverseOrderKafkaErrorHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        Throwable cause = exception.getCause();
        System.err.println(" Kafka error (reversed-orders): " + cause.getMessage());
        cause.printStackTrace();
        return null;
    }
}
