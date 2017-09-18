package com.mrjeffapp.order.client.administrative.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;

public class TimeTableAvailableResponse {

    private String timetableTimeSlotId;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Time orderCloseTimeMorning;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Time orderCloseTimeAfternoon;

    private Integer timingDaysVisits;

    private Boolean timeSlotAvailable;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Time timeSlotStart;

    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Time timeSlotEnd;

    public Boolean getTimeSlotAvailable() {
        return timeSlotAvailable;
    }

    public void setTimeSlotAvailable(Boolean timeSlotAvailable) {
        this.timeSlotAvailable = timeSlotAvailable;
    }

    public String getTimetableTimeSlotId() {
        return timetableTimeSlotId;
    }

    public void setTimetableTimeSlotId(String timetableTimeSlotId) {
        this.timetableTimeSlotId = timetableTimeSlotId;
    }

    public Time getOrderCloseTimeMorning() {
        return orderCloseTimeMorning;
    }

    public void setOrderCloseTimeMorning(Time orderCloseTimeMorning) {
        this.orderCloseTimeMorning = orderCloseTimeMorning;
    }

    public Integer getTimingDaysVisits() {
        return timingDaysVisits;
    }

    public void setTimingDaysVisits(Integer timingDaysVisits) {
        this.timingDaysVisits = timingDaysVisits;
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

    public Time getOrderCloseTimeAfternoon() {
        return orderCloseTimeAfternoon;
    }

    public void setOrderCloseTimeAfternoon(Time orderCloseTimeAfternoon) {
        this.orderCloseTimeAfternoon = orderCloseTimeAfternoon;
    }

    @Override
    public String toString() {
        return "TimeTableAvailableResponse{" +
                "timetableTimeSlotId=" + timetableTimeSlotId +
                ", orderCloseTimeMorning=" + orderCloseTimeMorning +
                ", orderCloseTimeAfternoon=" + orderCloseTimeAfternoon +
                ", timingDaysVisits=" + timingDaysVisits +
                ", timeSlotAvailable=" + timeSlotAvailable +
                ", timeSlotStart=" + timeSlotStart +
                ", timeSlotEnd=" + timeSlotEnd +
                '}';
    }
}
