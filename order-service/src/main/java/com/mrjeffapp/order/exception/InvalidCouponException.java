package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCouponException extends RuntimeException {
    private static final long serialVersionUID = 3706981779909272578L;

    public InvalidCouponException(String message) {
        super(message);
    }
}
