package com.example.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderApplication {
	public static List<Item> items = new ArrayList<>();
	public static List<Category> categories = new ArrayList<>();
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
		run();
	}

	public static void run() {
		prepare();
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

	public static void order() {
		System.out.println("카테고리를 선택해주세요.");
		System.out.print("1.상의 / 2.하의 / 3.신발 : ");
		int categoryNumber = sc.nextInt();

		System.out.println("상품번호   상품명      판매가격   재고수");
		for (Item item : items) {
			if (item.getCategoryId() == categoryNumber) {
				String formattedId = String.format("%-9s", item.getId());
				String formattedName = String.format("%-9s", item.getName());
				String formattedAmount = String.format("%-9s", item.getAmount());
				String formattedQuantity = String.format("%-5s", item.getQuantity());

				System.out.println(formattedId + formattedName + formattedAmount + formattedQuantity);
			}
		}
		System.out.println();

		List<Order> orders = new ArrayList<>();

		while (true) {
			System.out.println("더이상 주문하지 않으려면 0을 입력해주세요.");
			System.out.print("상품번호 : ");
			int orderNumber = sc.nextInt();

			if (orderNumber == 0) {
				if (!orders.isEmpty()) {
					receipt(orders);
				}
				break;
			}

			System.out.print("수량 : ");
			int orderQuantity = sc.nextInt();

			if (orderQuantity == 0) {
				if (!orders.isEmpty()) {
					receipt(orders);
				}
				break;
			}

			Order order = new Order(orderNumber, orderQuantity);
			orders.add(order);
		}

	}

	public static void receipt(List<Order> orders) {
		System.out.println("주문 내역 : ");
		System.out.println("-------------------------------");
		int orderAmount = 0;
		int paymentAmount = 0;
		int deliveryCharge = 2500;
		for (Order order : orders) {
			for (Item item : items) {
				if (item.getId() == order.getNumber()) {
					if (item.getQuantity() < order.getQuantity()) {
						System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
						break;
					}
					orderAmount += item.getAmount() * order.getQuantity();
					System.out.println(item.getName() + " - " + order.getQuantity() + "개");
					item.updateQuantity(item.getQuantity() - order.getQuantity());
				}
			}
		}

		System.out.println("-------------------------------");
		System.out.println("주문금액 : " + orderAmount + "원");
		if (orderAmount < 50000) {
			paymentAmount += orderAmount + deliveryCharge;
		}
		System.out.println("-------------------------------");
		System.out.println("지불금액 : " + paymentAmount + "원");
		System.out.println("-------------------------------");
		run();
	}

	public static void prepare() {
		Category category1 = new Category(1, "상의");
		Category category2 = new Category(2, "하의");
		Category category3 = new Category(3, "신발");
		categories.add(category1);
		categories.add(category2);
		categories.add(category3);

		Item shirt1 = new Item(1, 1, "티셔츠1", 5000, 20);
		Item shirt2 = new Item(2, 1, "티셔츠2", 10000, 5);
		Item shirt3 = new Item(3, 1, "티셔츠3", 13000, 2);
		Item pants1 = new Item(4, 2, "바지1", 23000, 7);
		Item pants2 = new Item(5, 2, "바지2", 30000, 5);
		Item shoes1 = new Item(6, 3, "신발1", 50000, 8);
		Item shoes2 = new Item(7, 3, "신발2", 65000, 2);
		Item shoes3 = new Item(8, 3, "신발3", 120000, 1);
		Item shoes4 = new Item(9, 3, "신발4", 60000, 6);
		items.add(shirt1);
		items.add(shirt2);
		items.add(shirt3);
		items.add(pants1);
		items.add(pants2);
		items.add(shoes1);
		items.add(shoes2);
		items.add(shoes3);
		items.add(shoes4);

	}

}
