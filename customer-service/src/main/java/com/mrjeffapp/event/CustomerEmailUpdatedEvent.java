package com.mrjeffapp.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class CustomerEmailUpdatedEvent {

    private String customerId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date eventDate;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "CustomerEmailUpdatedEvent{" +
                "customerId='" + customerId + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
