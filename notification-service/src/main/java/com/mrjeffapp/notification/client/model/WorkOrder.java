package com.mrjeffapp.notification.client.model;

import java.util.Date;
import java.util.Set;

/**
 * Created by siwsiw on 24/05/2017.
 */
public class WorkOrder {

    private String id;
    private String orderId;
    private String orderCode;
    private String customerId;
    private Set<ProviderProduct> providerProducts;
    private Date creationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<ProviderProduct> getProviderProducts() {
        return providerProducts;
    }

    public void setProviderProducts(Set<ProviderProduct> providerProducts) {
        this.providerProducts = providerProducts;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", providerProducts=" + providerProducts +
                ", creationDate=" + creationDate +
                '}';
    }
}
