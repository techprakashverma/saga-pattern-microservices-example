package com.order.ms.controller;

import com.order.ms.dto.CustomerOrder;
import com.order.ms.dto.OrderEvent;
import com.order.ms.entity.OrderRepository;
import com.order.ms.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;

	@PostMapping("/orders")
	public void createOrder(@RequestBody CustomerOrder customerOrder) {

		logger.info("Creating order for user {}", customerOrder.getEmail());
		orderService.createOrder(customerOrder);
	}

	@GetMapping("/greet")
	public Map<String, String> greet() {
		logger.info("Called this  method..");
		Map<String, String> response = new HashMap<>();
		response.put("message", "Hello from GET endpoint!");
		return response;
	}
}
