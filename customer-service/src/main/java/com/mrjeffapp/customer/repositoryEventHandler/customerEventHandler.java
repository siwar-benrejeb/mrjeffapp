package com.mrjeffapp.customer.repositoryEventHandler;

import com.mrjeffapp.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.customer.config.Constants.Event.BUSINESS_EVENT_LOGGER;

@Component
@RepositoryEventHandler(Customer.class)
public class customerEventHandler {

    private static final Logger BUSINESS_LOG = LoggerFactory.getLogger(BUSINESS_EVENT_LOGGER);


    @HandleBeforeSave
    public void handleWorkOrderBeforeSave(Customer customer) {

        BUSINESS_LOG.info("Customer: " + customer.getId() +
                " will be updated: " + customer.getName() + " " +
                customer.getLastName() + " " +
                customer.getEmail() + " " +
                customer.getPhoneNumber() + " "
        );
    }

    @HandleAfterSave
    public void handleWorkOrderAfterSave(Customer customer) {
            BUSINESS_LOG.info("Customer: " + customer.getId() +
                    "has been updated: " + customer.getName() + " " +
                    customer.getLastName() + " " +
                    customer.getEmail() + " " +
                    customer.getPhoneNumber() + " "
            );
    }
}
