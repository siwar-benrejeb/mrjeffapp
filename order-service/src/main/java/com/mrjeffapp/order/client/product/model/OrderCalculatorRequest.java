package com.mrjeffapp.order.client.product.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderCalculatorRequest {

    @NotNull
    private List<OrderCalculatorProductRequest> products;

    private String coupon;

    @NotNull
    private String customerId;

    public boolean hasCoupon() {
        return ((coupon != null) && !coupon.isEmpty());
    }

    public List<OrderCalculatorProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<OrderCalculatorProductRequest> products) {
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
