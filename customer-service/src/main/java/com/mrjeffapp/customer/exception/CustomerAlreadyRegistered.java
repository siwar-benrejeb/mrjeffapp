package com.mrjeffapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyRegistered extends RuntimeException {
    private static final long serialVersionUID = 1706551779909272573L;

    public CustomerAlreadyRegistered(String message) {
        super(message);
    }

}
