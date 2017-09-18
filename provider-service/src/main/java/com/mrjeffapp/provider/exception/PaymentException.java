package com.mrjeffapp.provider.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }

}
