package com.mrjeffapp.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4706981779909272573L;

    public ProductNotFoundException(String message) {
        super(message);
    }

}

