package com.mrjeffapp.provider.api.dto;

import javax.validation.constraints.NotNull;

public class ProductsRequest {

    @NotNull
    private String workOrder;

    @NotNull
    private String productId;

    @NotNull
    private Integer quantity;


    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductsRequest{" +
                "workOrder='" + workOrder + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
