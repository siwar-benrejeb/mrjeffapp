package com.mrjeffapp.notification.client.model;
import java.sql.Time;
import java.util.Date;

public class Visit {
    private String id;
    private Date date;
    private String visitTypeCode;
    private Time timeSlotStart;
    private Time timeSlotEnd;
    private Address address;

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

    public String getVisitTypeCode() {
        return visitTypeCode;
    }

    public void setVisitTypeCode(String visitTypeCode) {
        this.visitTypeCode = visitTypeCode;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", visitTypeCode='" + visitTypeCode + '\'' +
                ", timeSlotStart=" + timeSlotStart +
                ", timeSlotEnd=" + timeSlotEnd +
                ", address=" + address +
                '}';
    }
}
