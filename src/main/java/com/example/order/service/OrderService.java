package com.example.order.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.enumclass.OrderStatus;
import com.example.order.exception.InvalidInputException;
import com.example.order.exception.SoldOutException;
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

	public void start() {
		String action = Input.main();

		if (action.equals("o")) {
			Output.orderStart();
			order();
		} else if (action.equals("q") || action.equals("quit")) {
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

		while (true) {
			int orderNumber = Input.orderNumber();

			if (orderNumber == 0) {
				orderReceipt();
				break;
			}

			Item item = itemRepository.findByNumber(orderNumber)
				.orElseThrow(InvalidInputException::new);

			int orderQuantity = Input.orderQuantity();

			if (orderQuantity == 0) {
				orderReceipt();
				break;
			}

			Order order = new Order(orderQuantity);

			if (item.getQuantity() < order.getQuantity()) {
				throw new SoldOutException();
			}

			item.addOrder(order);
			item.updateQuantity(item.getQuantity() - order.getQuantity());
		}
		start();
	}

	public void orderReceipt() {
		List<Order> orders = orderRepository.findOrdersByStatus(OrderStatus.READY);

		Output.orderReceipt(orders);
		paymentService.payment(orders);
	}

}
