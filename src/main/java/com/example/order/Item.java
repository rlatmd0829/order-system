package com.example.order;

import lombok.Getter;

@Getter
public class Item {
	private int id;
	private int categoryId;
	private String name;
	private int amount;
	private int quantity;

	public Item(int id, int categoryId, String name, int amount, int quantity) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.amount = amount;
		this.quantity = quantity;
	}

	public void updateQuantity(int quantity) {
		this.quantity = quantity;
	}
}
