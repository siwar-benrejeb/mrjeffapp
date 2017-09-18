package com.mrjeffapp.event;


public class ProductAuthorizationEvent {
    private String id;
    private String productId;
    private Integer quantity;
    private String orderId;
    private String userId;
    private String workOrderId;

    public ProductAuthorizationEvent(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    @Override
    public String toString() {
        return "ProductAuthorizationEvent{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                '}';
    }
}
