package com.order.ms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDER_TABLE")
public class Order {

	@Id
	@GeneratedValue
	private long id;

	@Column
	private String item;

	@Column
	private int quantity;

	@Column
	private double amount;

	@Column
	private String status;

	@Column
	private String email;

}
