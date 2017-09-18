package com.mrjeffapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidAddressException extends RuntimeException {
	private static final long serialVersionUID = 4706981779909272578L;
	
	public InvalidAddressException(String message) {
		super(message);
	}
	
}
