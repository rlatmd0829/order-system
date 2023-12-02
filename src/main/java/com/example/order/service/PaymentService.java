package com.example.order.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.order.domain.Order;
import com.example.order.domain.Payment;
import com.example.order.repository.PaymentRepository;
import com.example.order.util.Output;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;

	public void payment(List<Order> orders) {
		Payment payment = new Payment(LocalDate.now(), orders);
		paymentRepository.save(payment);

		Output.orderAmount(payment.getOrdersAmount());
		Output.paymentAmount(payment.getPaymentAmount());
	}
}
