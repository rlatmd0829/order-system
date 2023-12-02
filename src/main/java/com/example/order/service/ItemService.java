package com.example.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.order.domain.Item;
import com.example.order.repository.ItemRepository;
import com.example.order.util.Input;
import com.example.order.util.Output;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	public void itemInfo() {
		int categoryNumber = Input.orderCategory();
		List<Item> items = itemRepository.findAll();
		Output.itemInfo(items, categoryNumber);
	}
}
