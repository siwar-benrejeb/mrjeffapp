package com.mrjeffapp.product.api.dto;

import java.util.List;

public class CartCalculatorResponse {

    private List<ProductRequest> products;

    private Double totalPrice;

    private Double discount;

    private String discountCategory;

    private Double totalPriceWithoutDiscount;

    private String coupon;

    private String couponId;

    private Double couponAmount;

    private String customerId;

    private Boolean couponValid;

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public Double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Boolean getCouponValid() {
        return couponValid;
    }

    public void setCouponValid(Boolean couponValid) {
        this.couponValid = couponValid;
    }

    @Override
    public String toString() {
        return "CartCalculatorResponse{" +
                "products=" + products +
                ", totalPrice=" + totalPrice +
                ", discount=" + discount +
                ", discountCategory='" + discountCategory + '\'' +
                ", totalPriceWithoutDiscount=" + totalPriceWithoutDiscount +
                ", coupon='" + coupon + '\'' +
                ", couponId=" + couponId +
                ", couponAmount=" + couponAmount +
                ", customerId=" + customerId +
                ", couponValid=" + couponValid +
                '}';
    }
}
