package com.example.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
