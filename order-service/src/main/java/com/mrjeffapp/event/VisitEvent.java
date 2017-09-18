package com.mrjeffapp.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class VisitEvent {

    private String visitId;

    private String customerId;

    private String visitTypeCode;

    private String orderId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date date;

    private String addressId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date eventDate;

    private String timeTableTimeSlotId;

    private String postalCodeId;

    private String cityId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getVisitTypeCode() {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode) {
        this.visitTypeCode = visitTypeCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getTimeTableTimeSlotId() {
        return timeTableTimeSlotId;
    }

    public void setTimeTableTimeSlotId(String timeTableTimeSlotId) {
        this.timeTableTimeSlotId = timeTableTimeSlotId;
    }

    public String getPostalCodeId() {
        return postalCodeId;
    }

    public void setPostalCodeId(String postalCodeId) {
        this.postalCodeId = postalCodeId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return "VisitEvent{" +
                "visitId=" + visitId +
                ", customerId=" + customerId +
                ", visitTypeCode='" + visitTypeCode + '\'' +
                ", orderId=" + orderId +
                ", date=" + date +
                ", addressId=" + addressId +
                ", eventDate=" + eventDate +
                ", timeTableTimeSlotId=" + timeTableTimeSlotId +
                ", postalCodeId=" + postalCodeId +
                ", cityId=" + cityId +
                '}';
    }
}
