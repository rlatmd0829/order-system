package com.example.order.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.order.enumclass.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "payments")
@NoArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	@Column(nullable = false)
	public LocalDate paymentDate;

	@OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<Order> orders = new ArrayList<>();

	public Payment(LocalDate paymentDate, List<Order> orders) {
		this.paymentDate = paymentDate;
		this.orders = orders;
		for (Order order : orders) {
			order.updatePayment(this);
			order.updateStatus(OrderStatus.SUCCESS);
		}
	}

	public Integer getOrdersAmount() {
		Integer total = 0;
		for (Order order : orders) {
			total += order.getItem().getAmount();
		}
		return total;
	}

	public Integer getPaymentAmount() {
		Integer total = 0;
		for (Order order : orders) {
			total += order.getItem().getAmount();
		}
		if (total < 50000) {
			total += 3000;
		}
		return total;
	}
}
