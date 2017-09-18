package com.mrjeffapp.product.api.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderCalculatorRequest {

    @NotNull
    private List<ProductRequest> products;

    private String coupon;

    @NotNull
    private String customerId;

    public boolean hasCoupon() {
        return ((coupon != null) && !coupon.isEmpty());
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCoupon() {
        return coupon;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "OrderCalculatorRequest{" +
                "products=" + products +
                ", coupon='" + coupon + '\'' +
                ", customerId=" + customerId +
                '}';
    }

}
