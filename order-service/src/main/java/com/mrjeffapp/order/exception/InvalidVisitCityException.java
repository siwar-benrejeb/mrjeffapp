package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidVisitCityException extends RuntimeException {
    private static final long serialVersionUID = 8706981889909272578L;

    public InvalidVisitCityException(String message) {
        super(message);
    }
}
