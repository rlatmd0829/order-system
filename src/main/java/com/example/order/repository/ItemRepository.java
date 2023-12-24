package com.example.order.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.order.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	@Lock(LockModeType.OPTIMISTIC) // 낙관적 락 설정
	Optional<Item> findByNumber(Integer number);
}
