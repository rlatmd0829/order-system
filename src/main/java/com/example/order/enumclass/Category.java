package com.example.order.enumclass;

import lombok.Getter;

@Getter
public enum Category {
	상의(1),
	하의(2),
	신발(3);

	private int number;

	Category(int number) {
		this.number = number;
	}
}
