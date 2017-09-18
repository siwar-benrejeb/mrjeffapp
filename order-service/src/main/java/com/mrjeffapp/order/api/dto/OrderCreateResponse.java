package com.mrjeffapp.order.api.dto;

import javax.validation.constraints.NotNull;

public class OrderCreateResponse {

    @NotNull
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
