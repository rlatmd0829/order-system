package com.example.order;

import lombok.Getter;

@Getter
public class Order {
	private int number;
	private int quantity;

	public Order(int number, int quantity) {
		this.number = number;
		this.quantity = quantity;
	}

}
