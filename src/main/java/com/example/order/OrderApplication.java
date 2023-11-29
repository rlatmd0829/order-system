package com.example.order;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.order.domain.Category;
import com.example.order.domain.Item;
import com.example.order.domain.Order;
import com.example.order.repository.ItemRepository;
import com.example.order.service.CSVDataService;

@SpringBootApplication
public class OrderApplication implements CommandLineRunner {
	public static Scanner sc = new Scanner(System.in);

	@Autowired
	public CSVDataService csvDataService;

	@Autowired
	public ItemRepository itemRepository;

	@Autowired
	DataSource dataSource;

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		prepare();
		runConsoleApp();
	}

	public void runConsoleApp() {
		System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
		String a = sc.next();
		if (a.equals("o")) {
			System.out.println("주문을 시작합니다.");
			order();
		} else if (a.equals("q") || a.equals("quit")) {
			System.out.println("고객님의 주문 감사합니다.");
			System.exit(0);
		} else {
			System.out.println("잘못입력했습니다.");
			System.exit(0);
		}
	}

	public void order() {
		// System.out.println("카테고리를 선택해주세요.");
		// System.out.print("1.상의 / 2.하의 / 3.신발 : ");
		// int categoryNumber = sc.nextInt();

		List<Item> items = itemRepository.findAll();

		System.out.println("상품번호     상품명                                                판매가격     재고수");
		for (Item item : items) {
			// if (item.getCategoryId() == categoryNumber) {
			System.out.printf("%-10s %-50s %-10s %-1s \n", item.getNumber(), item.getName(), item.getAmount(), item.getQuantity());
			// }
		}
		System.out.println();

		List<Order> orders = new ArrayList<>();

		while (true) {
			System.out.println("더이상 주문하지 않으려면 0을 입력해주세요.");
			System.out.print("상품번호 : ");
			int orderNumber = sc.nextInt();

			if (orderNumber == 0) {
				if (!orders.isEmpty()) {
					receipt(orders, items);
				}
				break;
			}

			System.out.print("수량 : ");
			int orderQuantity = sc.nextInt();

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
		System.out.println("주문 내역 : ");
		System.out.println("-------------------------------");
		int orderAmount = 0;
		int paymentAmount = 0;
		int deliveryCharge = 2500;
		for (Order order : orders) {
			for (Item item : items) {
				if (item.getNumber().equals(order.getNumber())) {
					if (item.getQuantity() < order.getQuantity()) {
						System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
						break;
					}
					orderAmount += item.getAmount() * order.getQuantity();
					System.out.println(item.getName() + " - " + order.getQuantity() + "개");
					item.updateQuantity(item.getQuantity() - order.getQuantity());
					itemRepository.save(item);
				}
			}
		}

		System.out.println("-------------------------------");
		System.out.println("주문금액 : " + orderAmount + "원");
		if (orderAmount < 50000) {
			paymentAmount += orderAmount + deliveryCharge;
		} else {
			paymentAmount = orderAmount;
		}
		System.out.println("-------------------------------");
		System.out.println("지불금액 : " + paymentAmount + "원");
		System.out.println("-------------------------------");
		runConsoleApp();
	}

	public void prepare() {
		csvDataService.readDataAndSaveToDB();
	}

}
