package com.mrjeffapp.event;

import java.util.List;

public class ProductsAuthorizationsEvent {
    private String orderId;
    private String workOrderId;

    private List<ProductAuthorizationEvent> productAuthorizationEventList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public List<ProductAuthorizationEvent> getProductAuthorizationEventList() {
        return productAuthorizationEventList;
    }

    public void setProductAuthorizationEventList(List<ProductAuthorizationEvent> productAuthorizationEventList) {
        this.productAuthorizationEventList = productAuthorizationEventList;
    }

    @Override
    public String toString() {
        return "ProductsAuthorizationsEvent{" +
                "orderId='" + orderId + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", productAuthorizationEventList=" + productAuthorizationEventList +
                '}';
    }
}