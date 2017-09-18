package com.mrjeffapp.notification.client.model;


import java.util.Set;

public class Order {
    private static final String VISIT_TYPE_PICKUP = "PICKUP";
    private static final String VISIT_TYPE_DELIVERY = "DELIVERY";

    private String id;
    private String customerId;
    private String code;
    private String note;
    private Double totalPrice;
    private Set<ProductOrder> productsOrder;
    private Set<Visit> visits;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ProductOrder> getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(Set<ProductOrder> productsOrder) {
        this.productsOrder = productsOrder;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public Visit getPickupVisit() {
        Visit visit = visits.stream().filter(v -> v.getVisitTypeCode().equals(VISIT_TYPE_PICKUP))
                .findFirst().get();
        return visit;
    }

    public Visit getDeliveryVisit() {
        Visit visit = visits.stream().filter(v -> v.getVisitTypeCode().equals(VISIT_TYPE_DELIVERY))
                .findFirst().get();
        return visit;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", code='" + code + '\'' +
                ", note='" + note + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", productsOrder=" + productsOrder +
                ", visits=" + visits +
                '}';
    }
}
