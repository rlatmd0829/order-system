package com.example.order.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.example.order.enumclass.Category;
import com.example.order.exception.SoldOutException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "items")
@Slf4j
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

	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Order> orders = new ArrayList<>();

	@Version
	private Integer version;

	public Item(Integer number, Category category, String name, Integer amount, Integer quantity) {
		this.number = number;
		this.category = category;
		this.name = name;
		this.amount = amount;
		this.quantity = quantity;
	}

	public void decrease(Integer quantity) {
		if (this.quantity < quantity) {
			throw new SoldOutException();
		}
		Integer remainQuantity = this.quantity - quantity;

		log.info(String.valueOf(remainQuantity));

		this.quantity = remainQuantity;
	}

	public void addOrder(Order order) {
		this.orders.add(order);
		order.updateItem(this);
	}
}
