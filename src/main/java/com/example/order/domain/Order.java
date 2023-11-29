package com.example.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(nullable = false)
	private Integer number;

	@Column(nullable = false)
	private Integer quantity;

	public Order(Integer number, Integer quantity) {
		this.number = number;
		this.quantity = quantity;
	}

}
