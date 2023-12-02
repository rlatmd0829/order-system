package com.example.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.enumclass.OrderStatus;
import com.example.order.exception.InvalidInputException;
import com.example.order.repository.ItemRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.util.Input;
import com.example.order.util.Output;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final PaymentService paymentService;

	private final ItemRepository itemRepository;
	private final OrderRepository orderRepository;

	public boolean order() {
		int orderNumber = Input.orderNumber();

		if (orderNumber == 0) {
			orderReceipt();
			return false;
		}

		Item item = itemRepository.findByNumber(orderNumber)
			.orElseThrow(InvalidInputException::new);

		int orderQuantity = Input.orderQuantity();

		if (orderQuantity == 0) {
			orderReceipt();
			return false;
		}

		Order order = new Order(orderQuantity);

		item.addOrder(order);
		item.decrease(orderQuantity);
		return true;
	}

	private void orderReceipt() {
		List<Order> orders = orderRepository.findOrdersByStatus(OrderStatus.READY);
		Output.orderReceipt(orders);
		paymentService.payment(orders);
	}
}
