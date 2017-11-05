package com.mrjeffapp.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Time;
import java.util.Date;

public class OrderCreateVisitRequest {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull
    private String addressId;

    @NotNull
    @Pattern(regexp = "PICKUP|DELIVERY")
    private String visitTypeCode;

    @NotNull
    private String timeSlotCode;
    @NotNull
    private Time timeSlotStart;
    @NotNull
    private Time timeSlotEnd;

    public Time getTimeSlotStart() {
        return timeSlotStart;
    }

    public void setTimeSlotStart(Time timeSlotStart) {
        this.timeSlotStart = timeSlotStart;
    }

    public Time getTimeSlotEnd() {
        return timeSlotEnd;
    }

    public void setTimeSlotEnd(Time timeSlotEnd) {
        this.timeSlotEnd = timeSlotEnd;
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

    public String getVisitTypeCode() {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode) {
        this.visitTypeCode = visitTypeCode;
    }

    public String getTimeSlotCode() {
        return timeSlotCode;
    }

    public void setTimeSlotCode(String timeSlotCode) {
        this.timeSlotCode = timeSlotCode;
    }

    @Override
    public String toString() {
        return "OrderCreateVisitRequest{" +
                "date=" + date +
                ", addressId=" + addressId +
                ", visitTypeCode='" + visitTypeCode + '\'' +
                ", timeSlotCode='" + timeSlotCode + '\'' +
                '}';
    }
}
