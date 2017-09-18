package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.IncidentType;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { IncidentType.class })
public interface FullProjectionIncidentType {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
