package com.mrjeffapp.product.client;

import com.mrjeffapp.product.client.coupon.model.CouponCalculateRequest;
import com.mrjeffapp.product.client.coupon.model.CouponCalculateResponse;
import com.mrjeffapp.product.client.coupon.model.CouponRedeemRequest;
import com.mrjeffapp.product.client.coupon.model.CouponRedeemResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "coupon-service", decode404 = true)
public interface CouponServiceClient {

    @RequestMapping(method = POST, value = "/coupon/calculate")
    public CouponCalculateResponse calculateCouponDiscount(CouponCalculateRequest couponCalculateRequest);

    @RequestMapping(method = POST, value = "/coupon/redeem")
    public CouponRedeemResponse calculateAndRedeemCouponDiscount(CouponRedeemRequest couponRedeemRequest);

}
