package com.example.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import com.example.order.service.CSVService;
import com.example.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class OrderApplication implements CommandLineRunner {

	public final CSVService csvService;
	public final OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		csvService.readDataAndSaveToDB();
		orderService.start();
	}
}
