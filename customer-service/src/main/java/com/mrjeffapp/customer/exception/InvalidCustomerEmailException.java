package com.mrjeffapp.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCustomerEmailException extends RuntimeException {

    public InvalidCustomerEmailException(String message) {
        super(message);
    }

}
