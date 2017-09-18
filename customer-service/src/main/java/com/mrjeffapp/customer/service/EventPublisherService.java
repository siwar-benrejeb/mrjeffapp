package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.event.AddressCreatedEvent;
import com.mrjeffapp.event.CustomerCreatedEvent;
import com.mrjeffapp.event.CustomerEmailUpdatedEvent;

public interface EventPublisherService {

    AddressCreatedEvent sendAddressCreatedEvent(Address address);

    CustomerCreatedEvent sendCustomerCreatedEvent(Customer customer);

    CustomerEmailUpdatedEvent sendCustomerEmailUpdatedEvent(Customer customer);

}
