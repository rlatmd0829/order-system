package com.example.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.order.controller.InputController;
import com.example.order.controller.OutputController;
import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.repository.ItemRepository;
import com.example.order.service.CSVDataService;

@SpringBootApplication
public class OrderApplication implements CommandLineRunner {

	@Autowired
	public CSVDataService csvDataService;

	@Autowired
	public ItemRepository itemRepository;

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		prepare();
		runConsoleApp();
	}

	public void runConsoleApp() {
		String a = InputController.main();

		if (a.equals("o")) {
			OutputController.orderStart();
			order();
		} else if (a.equals("q") || a.equals("quit")) {
			OutputController.orderExit();
			System.exit(0);
		} else {
			OutputController.orderInvalid();
			System.exit(0);
		}
	}

	public void order() {
		// System.out.println("카테고리를 선택해주세요.");
		// System.out.print("1.상의 / 2.하의 / 3.신발 : ");
		// int categoryNumber = sc.nextInt();

		List<Item> items = itemRepository.findAll();

		OutputController.order(items);

		// System.out.println("상품번호     상품명                                                판매가격     재고수");
		// for (Item item : items) {
			// if (item.getCategoryId() == categoryNumber) {
			// System.out.printf("%-10s %-50s %-10s %-1s \n", item.getNumber(), item.getName(), item.getAmount(), item.getQuantity());
			// }
		// }
		// System.out.println();

		List<Order> orders = new ArrayList<>();

		while (true) {
			int orderNumber = InputController.orderNumber();;

			if (orderNumber == 0) {
				if (!orders.isEmpty()) {
					receipt(orders, items);
				}
				break;
			}

			int orderQuantity = InputController.orderQuantity();

			if (orderQuantity == 0) {
				if (!orders.isEmpty()) {
					receipt(orders, items);
				}
				break;
			}

			Order order = new Order(orderNumber, orderQuantity);
			orders.add(order);
		}

	}

	public void receipt(List<Order> orders, List<Item> items) {
		OutputController.orderReceipt();
		int orderAmount = 0;
		int paymentAmount = 0;
		int deliveryCharge = 2500;
		for (Order order : orders) {
			for (Item item : items) {
				if (item.getNumber().equals(order.getNumber())) {
					if (item.getQuantity() < order.getQuantity()) {
						OutputController.orderError();
						break;
					}
					orderAmount += item.getAmount() * order.getQuantity();
					System.out.println(item.getName() + " - " + order.getQuantity() + "개");
					item.updateQuantity(item.getQuantity() - order.getQuantity());
					itemRepository.save(item);
				}
			}
		}
		OutputController.orderAmount(orderAmount);
		if (orderAmount < 50000) {
			paymentAmount += orderAmount + deliveryCharge;
		} else {
			paymentAmount = orderAmount;
		}
		OutputController.paymentAmount(paymentAmount);
		runConsoleApp();
	}

	public void prepare() {
		csvDataService.readDataAndSaveToDB();
	}

}
