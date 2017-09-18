package com.mrjeffapp.order.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class VisitRelocateRequest {

    @NotNull
    private String customerId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull
    private String timeSlotCode;

    @NotNull
    private String userId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeSlotCode() {
        return timeSlotCode;
    }

    public void setTimeSlotCode(String timeSlotCode) {
        this.timeSlotCode = timeSlotCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VisitRelocateRequest{" +
                "customerId=" + customerId +
                ", date=" + date +
                ", timeSlotCode='" + timeSlotCode + '\'' +
                ", userId=" + userId +
                '}';
    }
}
