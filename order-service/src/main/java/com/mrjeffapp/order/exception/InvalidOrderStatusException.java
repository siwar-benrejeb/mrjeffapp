package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidOrderStatusException extends RuntimeException {
    private static final long serialVersionUID = 4706981779909272578L;

    public InvalidOrderStatusException(String message) {
        super(message);
    }

}
