package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidVisitTypeException extends RuntimeException {

    public InvalidVisitTypeException(String message) {
        super(message);
    }

}