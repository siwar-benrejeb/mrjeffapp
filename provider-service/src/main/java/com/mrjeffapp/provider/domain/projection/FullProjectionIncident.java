package com.mrjeffapp.provider.domain.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrjeffapp.provider.domain.Incident;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(name = "full", types = { Incident.class })
public interface FullProjectionIncident {

    String getId();

    FullProjectionIncidentType getIncidentType();

    String getCause();

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", shape = JsonFormat.Shape.STRING)
    Date getCreationDate();

}
