package com.example.order.exception;

public class InvalidInputException extends RuntimeException {

	static final String MESSAGE = "";

	public InvalidInputException() {
		super(MESSAGE);
	}
}
