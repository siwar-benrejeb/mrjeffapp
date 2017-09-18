package com.mrjeffapp.order.api.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static com.mrjeffapp.order.config.Constants.VISIT_TYPE_CODE_DELIVERY;
import static com.mrjeffapp.order.config.Constants.VISIT_TYPE_CODE_PICKUP;

public class OrderCreateRequest {

    @NotNull
    private String customerId;

    @NotNull
    private String channelCode;

    private String couponCode;

    @NotNull
    @Valid
    private Set<OrderCreateProductRequest> products;

    @NotNull
    @Valid
    private Set<OrderCreateVisitRequest> visits;

    @NotNull
    @Pattern(regexp = "B2B|B2C")
    private String orderTypeCode;

    @NotNull
    @Pattern(regexp = "B2B|B2C")
    private String timetableTypeCode;

    @NotNull
    private String paymentMethodCode;

    @NotNull
    private String billingAddressId;

    private String note;

    public OrderCreateVisitRequest getPickupVisitRequest() {
        OrderCreateVisitRequest pickup = visits.stream()
                                            .filter(p -> VISIT_TYPE_CODE_PICKUP.equals(p.getVisitTypeCode()))
                                            .findFirst()
                                            .get();

        return pickup;
    }

    public OrderCreateVisitRequest getDeliveryVisitRequest() {
        OrderCreateVisitRequest pickup = visits.stream()
                                            .filter(p -> VISIT_TYPE_CODE_DELIVERY.equals(p.getVisitTypeCode()))
                                            .findFirst()
                                            .get();

        return pickup;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Set<OrderCreateProductRequest> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderCreateProductRequest> products) {
        this.products = products;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }

    public Set<OrderCreateVisitRequest> getVisits() {
        return visits;
    }

    public void setVisits(Set<OrderCreateVisitRequest> visits) {
        this.visits = visits;
    }

    public String getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getOrderTypeCode() {
        return orderTypeCode;
    }

    public void setOrderTypeCode(String orderTypeCode) {
        this.orderTypeCode = orderTypeCode;
    }

    public boolean hasCouponCode() {
        return couponCode != null;
    }

    public String getTimetableTypeCode() {
        return timetableTypeCode;
    }

    public void setTimetableTypeCode(String timetableTypeCode) {
        this.timetableTypeCode = timetableTypeCode;
    }

    @Override
    public String toString() {
        return "OrderCreateRequest{" +
                "customerId=" + customerId +
                ", channelCode='" + channelCode + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", products=" + products +
                ", visits=" + visits +
                ", orderTypeCode='" + orderTypeCode + '\'' +
                ", timetableTypeCode='" + timetableTypeCode + '\'' +
                ", paymentMethodCode='" + paymentMethodCode + '\'' +
                ", billingAddressId=" + billingAddressId +
                ", note='" + note + '\'' +
                '}';
    }
}
