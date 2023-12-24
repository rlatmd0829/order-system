package com.example.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.Order;
import com.example.order.enumclass.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findOrdersByStatus(OrderStatus status);
}
