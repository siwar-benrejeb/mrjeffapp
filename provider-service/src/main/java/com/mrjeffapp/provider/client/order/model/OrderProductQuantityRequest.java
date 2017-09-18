package com.mrjeffapp.provider.client.order.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderProductQuantityRequest {

    @NotNull
    private String code;

    @NotNull
    @Min(value = 1)
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
        return "OrderProductQuantityRequest{" +
                "code='" + code + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
