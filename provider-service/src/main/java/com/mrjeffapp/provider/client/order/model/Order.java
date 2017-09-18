package com.mrjeffapp.provider.client.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Set;

public class Order {

    private String id;

    private String code;

    private String customerId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date orderDate;

    private OrderStatus orderStatus;

    private Channel channel;

    private String billingAddressId;

    private String headquarterId;

    private String providerId;

    private String couponId;

    private String note;

    private Double totalPrice;

    private Set<ProductOrder> productsOrder;

    private Set<Visit> visits;

    private Set<OrderStatusTracking> statusTracking;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getHeadquarterId() {
        return headquarterId;
    }

    public void setHeadquarterId(String headquarterId) {
        this.headquarterId = headquarterId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<ProductOrder> getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(Set<ProductOrder> productsOrder) {
        this.productsOrder = productsOrder;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<OrderStatusTracking> getStatusTracking() {
        return statusTracking;
    }

    public void setStatusTracking(Set<OrderStatusTracking> statusTracking) {
        this.statusTracking = statusTracking;
    }

    public String getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }
}
