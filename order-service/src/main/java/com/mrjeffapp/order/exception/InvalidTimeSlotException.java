package com.mrjeffapp.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidTimeSlotException extends RuntimeException {

    private static final long serialVersionUID = 1706981773909272571L;

    public InvalidTimeSlotException(String message) {
        super(message);
    }

}
