package com.stock.ms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.ms.dto.DeliveryEvent;
import com.stock.ms.dto.PaymentEvent;
import com.stock.ms.entity.StockRepository;
import com.stock.ms.entity.WareHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReversesStock {

	@Autowired
	private StockRepository repository;

	@Autowired
	private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

	@KafkaListener(topics = "reversed-stock", groupId = "stock-group")
	public void reverseStock(String event) throws JsonProcessingException {
		System.out.println("Inside reverse stock for order "+event);

			DeliveryEvent deliveryEvent = new ObjectMapper().readValue(event, DeliveryEvent.class);

			Iterable<WareHouse> inv = this.repository.findByItem(deliveryEvent.getOrder().getItem());

			inv.forEach(i -> {
				i.setQuantity(i.getQuantity() + deliveryEvent.getOrder().getQuantity());
				repository.save(i);
			});

			PaymentEvent paymentEvent = new PaymentEvent();
			paymentEvent.setOrder(deliveryEvent.getOrder());
			paymentEvent.setType("PAYMENT_REVERSED");
			kafkaTemplate.send("reversed-payments", paymentEvent);

	}
}
