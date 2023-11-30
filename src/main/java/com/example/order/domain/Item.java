package com.example.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.order.enumclass.Category;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private Integer number;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false)
	private Integer quantity;

	public Item(Integer number, Category category, String name, Integer amount, Integer quantity) {
		this.number = number;
		this.category = category;
		this.name = name;
		this.amount = amount;
		this.quantity = quantity;
	}

	public void updateQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
