package com.example.order.util;

import java.util.List;

import com.example.order.domain.Item;
import com.example.order.domain.Order;

public class Output {

	public static void orderExit() {
		System.out.println("고객님의 주문 감사합니다.");
	}

	public static void itemInfo(List<Item> items, int categoryNumber) {
		System.out.println("상품번호     상품명                                                판매가격     재고수");
		for (Item item : items) {
			if (item.getCategory().getNumber() == categoryNumber) {
				System.out.printf(
					"%-10s %-50s %-10s %-1s \n", item.getNumber(), item.getName(), item.getAmount(),
					item.getQuantity()
				);
			}
		}
		System.out.println();
	}

	public static void orderReceipt(List<Order> orders) {
		System.out.println("주문 내역 : ");
		System.out.println("-------------------------------");

		for (Order order : orders) {
			System.out.println(
				order.getItem().getNumber() + " : " + order.getItem().getName() + " - " + order.getQuantity() + "개");
		}

	}

	public static void orderAmount(int orderAmount) {
		System.out.println("-------------------------------");
		System.out.println("주문금액 : " + orderAmount + "원");
	}

	public static void paymentAmount(int paymentAmount) {
		System.out.println("-------------------------------");
		System.out.println("지불금액 : " + paymentAmount + "원");
		System.out.println("-------------------------------");
	}
}
