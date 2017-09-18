package com.mrjeffapp.product.service;

import com.mrjeffapp.product.api.dto.ProductRequest;
import com.mrjeffapp.product.domain.PriceCalculation;

import java.util.List;

public interface ProductPriceCalculatorService {

    PriceCalculation calculatePrice(List<ProductRequest> products, String customerId);

    PriceCalculation calculateCartPriceWithCoupon(List<ProductRequest> products, String customerId, String coupon);

    PriceCalculation calculateOrderPriceWithCoupon(List<ProductRequest> products, String customerId, String coupon);

}
