package com.mrjeffapp.order.client.product.model;

import java.util.List;

public class OrderCalculatorResponse {

    private List<OrderCalculatorRequest> products;

    private Double totalPrice;

    private Double discount;

    private String discountCategory;

    private Double totalPriceWithoutDiscount;

    private String coupon;

    private String couponId;

    private Boolean couponValid;

    public List<OrderCalculatorRequest> getProducts() {
        return products;
    }

    public void setProducts(List<OrderCalculatorRequest> products) {
        this.products = products;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTotalPriceWithoutDiscount() {
        return totalPriceWithoutDiscount;
    }

    public void setTotalPriceWithoutDiscount(Double totalPriceWithoutDiscount) {
        this.totalPriceWithoutDiscount = totalPriceWithoutDiscount;
    }

    public String getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(String discountCategory) {
        this.discountCategory = discountCategory;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Boolean getCouponValid() {
        return couponValid;
    }

    public void setCouponValid(Boolean couponValid) {
        this.couponValid = couponValid;
    }
}
