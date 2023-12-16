package com.example.order;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.order.domain.Item;
import com.example.order.enumclass.Category;
import com.example.order.exception.SoldOutException;

class OrderTest {

	private Item item;

	@BeforeEach
	public void before() {
		item = new Item(123, Category.상의, "test", 1000, 100);
	}

	@Test
	void 재고감소() {
		item.decrease(10);
		assertEquals(90, item.getQuantity());
	}

	@Test
	void 재고감소_SoldOutException() {
		assertThrows(SoldOutException.class, () -> item.decrease(101));
	}

	@Test
	void 재고감소_동시요청() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		List<Throwable> exceptions = new ArrayList<>();

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					item.decrease(1);
				} catch (SoldOutException e) {
					exceptions.add(e);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await(); // 모든 스레드의 작업 완료를 기다림

		executorService.shutdown();

		if (!exceptions.isEmpty()) {
			fail("SoldOutException 발생 : " + exceptions.size());
		}
	}

	@Test
	void 재고감소_동시요청_SoldOutException() throws InterruptedException {
		int threadCount = 120;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);
		List<Throwable> exceptions = new ArrayList<>();

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					item.decrease(1);
				} catch (SoldOutException e) {
					exceptions.add(e);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await(); // 모든 스레드의 작업 완료를 기다림

		executorService.shutdown();

		// 모든 예외를 확인하고 테스트 실패로 처리
		if (!exceptions.isEmpty()) {
			fail("SoldOutException 발생 : " + exceptions.size());
		}
	}
}

