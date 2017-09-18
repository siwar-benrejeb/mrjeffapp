package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidVisitCountException extends RuntimeException {
    private static final long serialVersionUID = 8706981889933272578L;

    public InvalidVisitCountException(String message) {
        super(message);
    }

}