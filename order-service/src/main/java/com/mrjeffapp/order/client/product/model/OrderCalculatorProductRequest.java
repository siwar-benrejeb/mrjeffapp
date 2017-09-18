package com.mrjeffapp.order.client.product.model;

import javax.validation.constraints.NotNull;

public class OrderCalculatorProductRequest {

    @NotNull
    private String code;

    @NotNull
    private Integer quantity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderCalculatorProductRequest{" +
                "code='" + code + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
