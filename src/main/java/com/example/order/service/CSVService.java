package com.example.order.service;

import java.io.FileReader;

import org.springframework.stereotype.Service;

import com.example.order.domain.Item;
import com.example.order.enumclass.Category;
import com.example.order.repository.ItemRepository;
import com.opencsv.CSVReader;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CSVService {

	private final ItemRepository itemRepository;

	public void readDataAndSaveToDB() {
		try (CSVReader reader = new CSVReader(new FileReader("items.csv"))) {
			// 첫 번째 행(제목) 건너뛰기
			reader.readNext();
			String[] line;
			while ((line = reader.readNext()) != null) {

				Item item = new Item(
					Integer.valueOf(line[0]),
					Category.valueOf(line[1]),
					line[2],
					Integer.valueOf(line[3]),
					Integer.valueOf(line[4])
				);

				itemRepository.save(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
