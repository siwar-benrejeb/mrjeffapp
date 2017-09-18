package com.mrjeffapp.product.client.coupon.exception;

import com.mrjeffapp.product.exception.InvalidCouponException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CouponErrorDecoder implements ErrorDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(CouponErrorDecoder.class);

    private ErrorDecoder delegate;

    public CouponErrorDecoder() {
        delegate = new Default();
    }

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 400) {
            InvalidCouponException invalidCouponException = new InvalidCouponException("Invalid coupon");
            return invalidCouponException;
        }

        return delegate.decode(methodKey, response);
    }

}
