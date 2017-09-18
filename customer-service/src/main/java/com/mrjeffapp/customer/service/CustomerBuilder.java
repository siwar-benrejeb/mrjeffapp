package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.api.dto.CustomerCreateAddressRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;

public interface CustomerBuilder {

    Customer buildCustomer(CustomerCreateRequest customerCreateRequest);

    Address buildAddress(CustomerCreateRequest customer, CustomerCreateAddressRequest customerCreateAddressRequest);

}
