package com.example.order.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.order.exception.InvalidInputException;
import com.example.order.exception.SoldOutException;
import com.example.order.util.Input;
import com.example.order.util.Output;
import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final ItemRepository itemRepository;

	public void start() {
		String a = Input.main();

		if (a.equals("o")) {
			Output.orderStart();
			order();
		} else if (a.equals("q") || a.equals("quit")) {
			Output.orderExit();
			System.exit(0);
		} else {
			throw new InvalidInputException();
		}
	}

	public void order() {
		int categoryNumber = Input.orderCategory();

		List<Item> items = itemRepository.findAll();

		Output.order(items, categoryNumber);

		List<Order> orders = new ArrayList<>();

		while (true) {
			int orderNumber = Input.orderNumber();

			if (orderNumber == 0) {
				if (!orders.isEmpty()) {
					receipt(orders);
				}
				break;
			}

			int orderQuantity = Input.orderQuantity();

			if (orderQuantity == 0) {
				if (!orders.isEmpty()) {
					receipt(orders);
				}
				break;
			}
			Order order = new Order(orderNumber, orderQuantity);
			orders.add(order);
		}
		start();
	}

	public void receipt(List<Order> orders) {
		List<Item> items = itemRepository.findAll();

		Output.orderReceipt();
		int orderAmount = 0;
		int paymentAmount = 0;
		int deliveryCharge = 3000;
		for (Order order : orders) {
			for (Item item : items) {
				if (item.getNumber().equals(order.getNumber())) {
					if (item.getQuantity() < order.getQuantity()) {
						throw new SoldOutException();
					}
					orderAmount += item.getAmount() * order.getQuantity();
					Output.info(
						item.getNumber(),
						item.getName(),
						order.getQuantity()
					);
					item.updateQuantity(item.getQuantity() - order.getQuantity());
					itemRepository.save(item);
				}
			}
		}
		Output.orderAmount(orderAmount);
		if (orderAmount < 50000) {
			paymentAmount += orderAmount + deliveryCharge;
		} else {
			paymentAmount = orderAmount;
		}
		Output.paymentAmount(paymentAmount);
	}

}
