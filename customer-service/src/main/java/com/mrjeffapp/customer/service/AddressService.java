package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.api.dto.CreateAddressRequest;
import com.mrjeffapp.customer.domain.Address;

public interface AddressService {

    Address createCustomerAddress(String customerId, CreateAddressRequest addressRequest);

}
