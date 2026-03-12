package com.stock.ms.exception;

import com.stock.ms.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component("stockKafkaErrorHandler")
public class StockKafkaErrorHandler implements KafkaListenerErrorHandler {
    @Autowired
    private StockService stockService;

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        Throwable cause = exception.getCause();

        if (cause instanceof StockNotFoundException) {
            StockNotFoundException snfe = (StockNotFoundException) cause;
            stockService.sendPaymentReversed(snfe.getOrder());
        }

        // Optional: Log or rethrow
        System.err.println("Kafka error handler caught exception: " + exception.getMessage());

        return null; // return null to commit the offset, or rethrow to retry
    }
}
