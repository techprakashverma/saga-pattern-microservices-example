package com.order.ms.service;

import com.order.ms.dto.CustomerOrder;
import com.order.ms.dto.OrderEvent;
import com.order.ms.entity.Order;
import com.order.ms.entity.OrderRepository;
import com.order.ms.exception.OrderProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void createOrder(CustomerOrder customerOrder) {
        Order order = new Order();
        try {
            order.setAmount(customerOrder.getAmount());
            order.setItem(customerOrder.getItem());
            order.setQuantity(customerOrder.getQuantity());
            order.setEmail(customerOrder.getEmail());
            order.setStatus("CREATED");
            order = repository.save(order);

            customerOrder.setOrderId(order.getId());

            OrderEvent event = new OrderEvent();
            event.setOrder(customerOrder);
            event.setType("ORDER_CREATED");
            kafkaTemplate.send("new-orders", event);
        } catch (Exception e) {
            order.setStatus("FAILED");
            repository.save(order);
            throw new OrderProcessingException("Failed to process order", e);
        }
    }
}