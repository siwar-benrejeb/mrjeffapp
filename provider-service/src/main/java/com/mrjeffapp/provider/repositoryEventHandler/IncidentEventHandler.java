package com.mrjeffapp.provider.repositoryEventHandler;

import com.mrjeffapp.provider.domain.Incident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Component
@RepositoryEventHandler(Incident.class)
public class IncidentEventHandler {

    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    @HandleAfterCreate
    public void handleIncidentSave(Incident incident) {

        BUSINESS_LOG.info("Incident: " + incident.getIncidentType().getName() + " - " +  incident.getCause() +
                " has been create: " + incident.getId() +
                " by: " + incident.getUser().getId() + " - " + incident.getUser().getEmail());
    }
}
