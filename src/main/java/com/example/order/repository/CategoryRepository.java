package com.example.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
