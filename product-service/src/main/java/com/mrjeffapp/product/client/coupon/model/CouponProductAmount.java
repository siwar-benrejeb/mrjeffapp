package com.mrjeffapp.product.client.coupon.model;

import javax.validation.constraints.NotNull;

public class CouponProductAmount {

    @NotNull
    private String productId;

    @NotNull
    private Double totalAmount;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "CouponProductAmount{" +
                "productId=" + productId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
