package com.mrjeffapp.provider.repositoryEventHandler;

import com.mrjeffapp.provider.domain.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.provider.config.Constants.BUSINESS_EVENT_LOGGER;

@Component
@RepositoryEventHandler(Authorization.class)
public class AuthorizationEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationEventHandler.class);
    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);

    @HandleBeforeSave
    public void handlePersonSave(Authorization authorization) {

    }
}
