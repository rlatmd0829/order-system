package com.example.order.controller;

import java.util.Scanner;

public class InputController {

	private static Scanner sc = new Scanner(System.in);

	public static String main() {
		System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
		return sc.next();
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
