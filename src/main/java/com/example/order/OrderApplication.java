package com.example.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.order.exception.InvalidInputException;
import com.example.order.service.CSVService;
import com.example.order.service.ItemService;
import com.example.order.service.OrderService;
import com.example.order.ui.Input;
import com.example.order.ui.Output;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class OrderApplication implements CommandLineRunner {

	public final CSVService csvService;
	public final OrderService orderService;
	public final ItemService itemService;

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		csvService.readDataAndSaveToDB();
		start();
	}

	public void start() {
		String action = Input.main();

		if (action.equals("o")) {
			itemService.itemInfo();

			while (true) {
				if (!orderService.order()) {
					break;
				}
			}

		} else if (action.equals("q") || action.equals("quit")) {
			Output.orderExit();
			System.exit(0);
		} else {
			throw new InvalidInputException();
		}

		start();
	}
}
