package com.mrjeffapp.order.api.dto;

import javax.validation.constraints.NotNull;

public class OrderStatusChangeRequest {

    @NotNull
    private String orderStatusCode;

    @NotNull
    private String userId;

    public String getOrderStatusCode() {
        return orderStatusCode;
    }

    public void setOrderStatusCode(String orderStatusCode) {
        this.orderStatusCode = orderStatusCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderStatusChangeRequest{" +
                "orderStatusCode='" + orderStatusCode + '\'' +
                ", userId=" + userId +
                '}';
    }
}
