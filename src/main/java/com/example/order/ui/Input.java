package com.example.order.ui;

import java.util.Scanner;

public class Input {

	private static Scanner sc = new Scanner(System.in);

	public static String main() {
		System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
		return sc.next();
	}

	public static int orderCategory() {
		System.out.println("카테고리 번호를 입력해주세요.");
		System.out.print("1.상의 / 2.하의 / 3.신발 : ");
		return sc.nextInt();
	}

	public static int orderNumber() {
		System.out.println("더이상 주문하지 않으려면 0을 입력해주세요.");
		System.out.print("상품번호 : ");
		return sc.nextInt();
	}

	public static int orderQuantity() {
		System.out.print("수량 : ");
		return sc.nextInt();
	}
}
