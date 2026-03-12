package com.stock.ms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stock.ms.dto.Stock;
import com.stock.ms.exception.StockNotFoundException;
import com.stock.ms.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StockController {

	private static final Logger logger = LoggerFactory.getLogger(StockController.class);

	@Autowired
	private StockService stockService;

	//@KafkaListener(topics = "new-payments", groupId = "payments-group")
	@KafkaListener(
			topics = "new-payments",
			groupId = "payments-group",
			errorHandler = "stockKafkaErrorHandler" //linking custom error handler
	)
	public void updateStock(String paymentEvent) throws JsonProcessingException, StockNotFoundException {

		stockService.updateStock(paymentEvent);
	}

	@PostMapping("/addItems")
	public void addItems(@RequestBody Stock stock) {
		stockService.addStock(stock);
	}
}
