package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidChannelException extends RuntimeException {
	private static final long serialVersionUID = 4706981779909272578L;

	public InvalidChannelException(String message) {
		super(message);
	}
	
}
