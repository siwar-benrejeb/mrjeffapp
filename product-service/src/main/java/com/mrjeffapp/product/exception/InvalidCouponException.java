package com.mrjeffapp.product.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class InvalidCouponException extends HystrixBadRequestException {

    public InvalidCouponException(String message) {
        super(message);
    }
}
