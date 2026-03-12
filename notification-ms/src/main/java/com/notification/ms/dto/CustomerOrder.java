package com.notification.ms.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerOrder {

	private String item;

	private int quantity;

	private double amount;

	private String paymentMode;

	private Long orderId;

	private String address;

	private String email;

	public String getItem() {
		return item;
	}

	public CustomerOrder setItem(String item) {
		this.item = item;
		return this;
	}

	public int getQuantity() {
		return quantity;
	}

	public CustomerOrder setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}

	public double getAmount() {
		return amount;
	}

	public CustomerOrder setAmount(double amount) {
		this.amount = amount;
		return this;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public CustomerOrder setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
		return this;
	}

	public Long getOrderId() {
		return orderId;
	}

	public CustomerOrder setOrderId(Long orderId) {
		this.orderId = orderId;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public CustomerOrder setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public CustomerOrder setEmail(String email) {
		this.email = email;
		return this;
	}
}
