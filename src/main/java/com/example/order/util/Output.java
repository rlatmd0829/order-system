package com.example.order.util;

import java.time.LocalDate;
import java.util.List;

import com.example.order.domain.Item;

public class Output {
	public static void orderStart() {
		System.out.println("주문을 시작합니다.");
	}

	public static void orderExit() {
		System.out.println("고객님의 주문 감사합니다.");
	}

	public static void order(List<Item> items, int categoryNumber) {
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

	public static void orderReceipt() {
		System.out.println("주문 내역 : ");
		System.out.println("-------------------------------");
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

	public static void info(int itemNumber, String itemName, int orderQuantity) {
		LocalDate now = LocalDate.now();
		System.out.println("결제 날짜 : " + now);
		System.out.println(itemNumber + " : " + itemName + " - " + orderQuantity + "개");
	}
}
