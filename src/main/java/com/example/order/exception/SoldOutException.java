package com.example.order.exception;

public class SoldOutException extends RuntimeException {

	static final String MESSAGE = "";

	public SoldOutException() {
		super(MESSAGE);
	}
}
