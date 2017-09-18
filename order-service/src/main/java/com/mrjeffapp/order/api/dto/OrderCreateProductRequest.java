package com.mrjeffapp.order.api.dto;

import javax.validation.constraints.NotNull;

public class OrderCreateProductRequest {

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
        return "OrderCreateProductRequest{" +
                "code='" + code + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
