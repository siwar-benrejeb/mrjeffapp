package com.mrjeffapp.event;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ExpiredAuthorizationEvent {

    private String countryCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date timeSlotStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date timeSlotEnd;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date eventDate;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Date getTimeSlotStart() {
        return timeSlotStart;
    }

    public void setTimeSlotStart(Date timeSlotStart) {
        this.timeSlotStart = timeSlotStart;
    }

    public Date getTimeSlotEnd() {
        return timeSlotEnd;
    }

    public void setTimeSlotEnd(Date timeSlotEnd) {
        this.timeSlotEnd = timeSlotEnd;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
