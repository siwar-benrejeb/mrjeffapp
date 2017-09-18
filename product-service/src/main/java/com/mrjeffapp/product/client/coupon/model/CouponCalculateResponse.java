package com.mrjeffapp.product.client.coupon.model;

import java.util.Set;

public class CouponCalculateResponse {

    private String couponId;

    private String coupon;

    private Double couponAmount;

    private String discountCategory;

    private Double orderAmount;

    private Double orderAmountDiscount;

    private Set<CouponProductAmount> productsAmount;

    private String customerId;

    public String getCoupon() {
        return coupon;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(String discountCategory) {
        this.discountCategory = discountCategory;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getOrderAmountDiscount() {
        return orderAmountDiscount;
    }

    public void setOrderAmountDiscount(Double orderAmountDiscount) {
        this.orderAmountDiscount = orderAmountDiscount;
    }

    public Set<CouponProductAmount> getProductsAmount() {
        return productsAmount;
    }

    public void setProductsAmount(Set<CouponProductAmount> productsAmount) {
        this.productsAmount = productsAmount;
    }

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    @Override
    public String toString() {
        return "CouponCalculateResponse{" +
                "coupon='" + coupon + '\'' +
                ", couponId=" + couponId +
                ", discountCategory='" + discountCategory + '\'' +
                ", orderAmount=" + orderAmount +
                ", orderAmountDiscount=" + orderAmountDiscount +
                ", productsAmount=" + productsAmount +
                ", customerId=" + customerId +
                '}';
    }
}
