package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.service.EventPublisherService;
import com.mrjeffapp.event.AddressCreatedEvent;
import com.mrjeffapp.event.CustomerCreatedEvent;
import com.mrjeffapp.event.CustomerEmailUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.mrjeffapp.customer.config.Constants.Event.*;

@Service
public class EventPublisherServiceImpl implements EventPublisherService {
    private static final Logger LOG = LoggerFactory.getLogger(EventPublisherServiceImpl.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventPublisherServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public CustomerCreatedEvent sendCustomerCreatedEvent(Customer customer) {
        CustomerCreatedEvent event = new CustomerCreatedEvent();
        event.setCustomerId(customer.getId());
        event.setName(customer.getName());
        event.setLastName(customer.getLastName());
        event.setEmail(customer.getEmail());
        event.setPhone(customer.getPhoneNumber());
        event.setEventDate(new Date());

        LOG.debug("Sending customer created event: {}", event);

        LOG.debug("sending to the queue",OUEUE_CUSTOMER_CREATION);
        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, ROUTING_CUSTOMER_CREATED, event);
        LOG.debug("Customer created event sent: {}", event);

        return event;
    }

    @Override
    public AddressCreatedEvent sendAddressCreatedEvent(Address address) {
        AddressCreatedEvent event = new AddressCreatedEvent();
        event.setAddressId(address.getId());
        event.setAddress(address.getAddress());
        event.setAddressNumber(address.getAddressNumber());
        event.setAddressDetails(address.getAddressDetails());
        event.setPostalCode(address.getPostalCode());
        event.setPostalCodeId(address.getPostalCodeId());
        event.setCityId(address.getCityId());
        event.setRegionId(address.getRegionId());
        event.setCountryId(address.getCountryId());
        event.setCustomerId(address.getCustomer().getId());
        event.setEventDate(new Date());

        LOG.debug("Sending event delivery note sign: {}", event);
        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, ROUTING_ADDRESS_CREATED, event);
        LOG.info("Delivery note sign status event sent: {}", event);

        return event;
    }

    @Override
    public CustomerEmailUpdatedEvent sendCustomerEmailUpdatedEvent(Customer customer) {
        CustomerEmailUpdatedEvent event = new CustomerEmailUpdatedEvent();
        event.setCustomerId(customer.getId());
        event.setEventDate(new Date());

        LOG.debug("Sending event customer email updated: {}", event);
        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, ROUTING_CUSTOMER_EMAIL_UPDATED, event);
        LOG.info("Customer email updated event sent: {}", event);

        return event;
    }

}
