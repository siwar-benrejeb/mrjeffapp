package com.mrjeffapp.notification.service;

import com.mrjeffapp.notification.client.model.Customer;

public interface EmailDestinationResolver {

    public String getDestinationEmail(Customer customer);

}
