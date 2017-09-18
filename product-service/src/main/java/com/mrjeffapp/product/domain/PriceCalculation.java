package com.mrjeffapp.product.domain;

public class PriceCalculation {

    private String coupon;

    private String couponId;

    private Double couponAmount;

    private Double totalPrice;

    private Double discount;

    private String discountCategory;

    private Double totalPriceWithoutDiscount;

    private Boolean couponValid;

    public PriceCalculation() {
    }

    public PriceCalculation(Double totalPrice, Double totalPriceWithoutDiscount, Double discount, String discountCategory, String couponId, String coupon, Double couponAmount, Boolean couponValid) {
        this.totalPrice = totalPrice;
        this.totalPriceWithoutDiscount = totalPriceWithoutDiscount;
        this.discount = discount;
        this.discountCategory = discountCategory;
        this.couponId = couponId;
        this.coupon = coupon;
        this.couponAmount = couponAmount;
        this.couponValid = couponValid;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountCategory() {
        return discountCategory;
    }

    public void setDiscountCategory(String discountCategory) {
        this.discountCategory = discountCategory;
    }

    public Double getTotalPriceWithoutDiscount() {
        return totalPriceWithoutDiscount;
    }

    public void setTotalPriceWithoutDiscount(Double totalPriceWithoutDiscount) {
        this.totalPriceWithoutDiscount = totalPriceWithoutDiscount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
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
        return "PriceCalculation{" +
                "coupon='" + coupon + '\'' +
                ", couponId=" + couponId +
                ", couponAmount=" + couponAmount +
                ", totalPrice=" + totalPrice +
                ", discount=" + discount +
                ", discountCategory='" + discountCategory + '\'' +
                ", totalPriceWithoutDiscount=" + totalPriceWithoutDiscount +
                ", couponValid=" + couponValid +
                '}';
    }
}
