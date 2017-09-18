package com.mrjeffapp.product.api;

import com.mrjeffapp.product.api.dto.CartCalculatorRequest;
import com.mrjeffapp.product.api.dto.CartCalculatorResponse;
import com.mrjeffapp.product.client.CouponServiceClient;
import com.mrjeffapp.product.domain.PriceCalculation;
import com.mrjeffapp.product.service.ProductPriceCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartCalculatorController {
    private static final Logger LOG = LoggerFactory.getLogger(CartCalculatorController.class);

    private final ProductPriceCalculatorService productPriceCalculatorService;

    private final CouponServiceClient couponServiceClient;

    @Autowired
    public CartCalculatorController(ProductPriceCalculatorService productPriceCalculatorService, CouponServiceClient couponServiceClient) {
        this.productPriceCalculatorService = productPriceCalculatorService;
        this.couponServiceClient = couponServiceClient;
    }

    @PostMapping("/total")
    public CartCalculatorResponse calculate(@Valid @RequestBody CartCalculatorRequest request) {
        LOG.debug("Products: {}, coupon: {}, customer: {}", request.getProducts(), request.getCoupon(), request.getCustomerId());

        PriceCalculation priceCalculation = null;
        if(request.hasCoupon()) {
            priceCalculation = productPriceCalculatorService.calculateCartPriceWithCoupon(request.getProducts(), request.getCustomerId(), request.getCoupon());

        } else {
            priceCalculation = productPriceCalculatorService.calculatePrice(request.getProducts(), request.getCustomerId());
        }

        CartCalculatorResponse response = new CartCalculatorResponse();
        response.setProducts(request.getProducts());
        response.setCustomerId(request.getCustomerId());

        response.setCoupon(priceCalculation.getCoupon());
        response.setCouponId(priceCalculation.getCouponId());
        response.setCouponAmount(priceCalculation.getCouponAmount());
        response.setTotalPrice(priceCalculation.getTotalPrice());
        response.setTotalPriceWithoutDiscount(priceCalculation.getTotalPriceWithoutDiscount());
        response.setDiscount(priceCalculation.getDiscount());
        response.setDiscountCategory(priceCalculation.getDiscountCategory());
        response.setCouponValid(priceCalculation.getCouponValid());

        return response;
    }

}
