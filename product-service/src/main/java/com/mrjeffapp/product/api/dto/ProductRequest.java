package com.mrjeffapp.product.api.dto;

import javax.validation.constraints.NotNull;

public class ProductRequest {

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
        return "ProductRequest{" +
                "code='" + code + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
