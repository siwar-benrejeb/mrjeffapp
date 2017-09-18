package com.mrjeffapp.event;


import com.mrjeffapp.event.ProductAuthorizationEvent;

import java.util.List;

public class ProductsAuthorizationsEvent {
    private String orderId;
    private String workOrderId;

    private List<ProductAuthorizationEvent> productAuthorizationEventList;

    public List<ProductAuthorizationEvent> getProductAuthorizationEventList() {
        return productAuthorizationEventList;
    }

    public ProductsAuthorizationsEvent() {
    }

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

    public void setProductAuthorizationEventList(List<ProductAuthorizationEvent> productAuthorizationEventList) {
        this.productAuthorizationEventList = productAuthorizationEventList;
    }



}
