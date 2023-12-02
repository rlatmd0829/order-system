package com.example.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	Optional<Item> findByNumber(Integer number);
}
