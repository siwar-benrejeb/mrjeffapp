package com.mrjeffapp.order.domain.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrjeffapp.order.domain.Visit;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.util.Date;

@Projection(name = "full", types = { Visit.class })
public interface FullProjectionVisit {

    String getId();

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    Date getDate();

    String getVisitTypeCode();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time getTimeSlotStart();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    Time getTimeSlotEnd();

    String getPostalCodeId();

    String getCityId();

    String getCountryId();

    String getAddressId();

    String getTimeTableTimeSlotId();

}
