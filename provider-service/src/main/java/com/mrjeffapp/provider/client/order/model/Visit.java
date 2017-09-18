package com.mrjeffapp.provider.client.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Visit {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date date;

    private String addressId;

    private String visitTypeCode;

    private String timeSlotStart;

    private String timeSlotEnd;

    private String postalCodeId;

    private String timeTableTimeSlotId;

    private String timetableTypeCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTimeSlotStart() {
        return timeSlotStart;
    }

    public void setTimeSlotStart(String timeSlotStart) {
        this.timeSlotStart = timeSlotStart;
    }

    public String getTimeSlotEnd() {
        return timeSlotEnd;
    }

    public void setTimeSlotEnd(String timeSlotEnd) {
        this.timeSlotEnd = timeSlotEnd;
    }

    public String getPostalCodeId() {
        return postalCodeId;
    }

    public void setPostalCodeId(String postalCodeId) {
        this.postalCodeId = postalCodeId;
    }

    public String getTimeTableTimeSlotId() {
        return timeTableTimeSlotId;
    }

    public void setTimeTableTimeSlotId(String timeTableTimeSlotId) {
        this.timeTableTimeSlotId = timeTableTimeSlotId;
    }

    public String getTimetableTypeCode() {
        return timetableTypeCode;
    }

    public void setTimetableTypeCode(String timetableTypeCode) {
        this.timetableTypeCode = timetableTypeCode;
    }
}
