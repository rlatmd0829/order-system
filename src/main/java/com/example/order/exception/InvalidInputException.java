package com.example.order.exception;

public class InvalidInputException extends RuntimeException {

	static final String MESSAGE = "잘못입력했습니다.";

	public InvalidInputException() {
		super(MESSAGE);
	}
}
