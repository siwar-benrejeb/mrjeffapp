package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidVisitException extends RuntimeException {
    private static final long serialVersionUID = 8706981889933272578L;

    public InvalidVisitException(String message) {
        super(message);
    }

}