package com.mrjeffapp.provider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 6460001684582104484L;

	public AuthenticationException(String message) {
		super(message);
	}
	
}
