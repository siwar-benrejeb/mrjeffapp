package com.mrjeffapp.notification.client.model;

import java.util.Date;

/**
 * Created by siwsiw on 25/05/2017.
 */
public class ProviderProduct {
    private String id;

    private String productId;

    private Integer quantity;

    private WorkOrder workOrder;

    private Date creationDate;

    public ProviderProduct(String id, String productId, Integer quantity, WorkOrder workOrder, Date creationDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.workOrder = workOrder;
        this.creationDate = creationDate;
    }
    public ProviderProduct(){}

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

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "ProviderProduct{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                ", workOrder=" + workOrder +
                ", creationDate=" + creationDate +
                '}';
    }
}
