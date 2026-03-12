package com.stock.ms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.ms.dto.CustomerOrder;
import com.stock.ms.dto.DeliveryEvent;
import com.stock.ms.dto.PaymentEvent;
import com.stock.ms.dto.Stock;
import com.stock.ms.entity.StockRepository;
import com.stock.ms.entity.WareHouse;
import com.stock.ms.exception.StockNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    @Autowired
    private StockRepository repository;

    @Autowired
    private KafkaTemplate<String, DeliveryEvent> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaPaymentTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public void updateStock(String paymentEvent) throws JsonProcessingException, StockNotFoundException {

        logger.info("Inside update inventory for order {}", paymentEvent);

        PaymentEvent p = objectMapper.readValue(paymentEvent, PaymentEvent.class);
        CustomerOrder order = p.getOrder();

            Iterable<WareHouse> inventories = repository.findByItem(order.getItem());

            boolean exists = inventories.iterator().hasNext();

            if (!exists) {
                System.out.println("Stock not exist so reverting the order");
                throw new StockNotFoundException("Stock not available",order);
            }

            inventories.forEach(i -> {
                i.setQuantity(i.getQuantity() - order.getQuantity());

                repository.save(i);
            });

            DeliveryEvent event = new DeliveryEvent();
            event.setType("STOCK_UPDATED");
            event.setOrder(p.getOrder());
            kafkaTemplate.send("new-stock", event);
        /*} catch (Exception e) {
            PaymentEvent pe = new PaymentEvent();
            pe.setOrder(order);
            pe.setType("PAYMENT_REVERSED");
            kafkaPaymentTemplate.send("reversed-payments", pe);
        }*/
    }

    public void sendPaymentReversed(CustomerOrder order) {
        logger.info("Send Payment Reversed:: {}", order.getItem());
        PaymentEvent reversed = new PaymentEvent();
        reversed.setOrder(order);
        reversed.setType("PAYMENT_REVERSED");
        kafkaPaymentTemplate.send("reversed-payments", reversed);
    }

    public void addStock(Stock stock) {
        Iterable<WareHouse> existingItems = repository.findByItem(stock.getItem());

        if (existingItems.iterator().hasNext()) {
            existingItems.forEach(i -> {
                i.setQuantity(i.getQuantity() + stock.getQuantity());
                repository.save(i);
            });
        } else {
            WareHouse i = new WareHouse();
            i.setItem(stock.getItem());
            i.setQuantity(stock.getQuantity());
            repository.save(i);
        }
    }
}
