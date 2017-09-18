package com.mrjeffapp.product.client.coupon.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class CouponRedeemRequest {

    @NotNull
    private String coupon;

    @NotNull
    private String orderId;

    @NotNull
    private Double orderAmount;

    @Valid
    private Set<CouponProductAmount> productsAmount;

    private String customerId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<CouponProductAmount> getProductsAmount() {
        return productsAmount;
    }

    public void setProductsAmount(Set<CouponProductAmount> productsAmount) {
        this.productsAmount = productsAmount;
    }

}
