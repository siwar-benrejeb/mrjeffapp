package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidTimingDateException extends RuntimeException {

    public InvalidTimingDateException(String message) {
        super(message);
    }

}