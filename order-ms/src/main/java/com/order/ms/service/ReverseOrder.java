package com.order.ms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.ms.dto.OrderEvent;
import com.order.ms.entity.Order;
import com.order.ms.entity.OrderRepository;
import com.order.ms.exception.OrderReverseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReverseOrder {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	//@KafkaListener(topics = "reversed-orders", groupId = "orders-group")
	@KafkaListener(
			topics = "reversed-orders",
			groupId = "orders-group",
			errorHandler = "reverseOrderKafkaErrorHandler"
	)
	public void reverseOrder(String event) {
		System.out.println("Inside reverse order for order "+event);
		
		try {
			OrderEvent orderEvent = objectMapper.readValue(event, OrderEvent.class);

			Optional<Order> order = repository.findById(orderEvent.getOrder().getOrderId());

			order.ifPresent(o -> {
				o.setStatus("FAILED");
				this.repository.save(o);
			});
		} catch (Exception e) {
			//e.printStackTrace();
			throw new OrderReverseException("Error reversing order for payload: " + event, e);
		}
	}
}
