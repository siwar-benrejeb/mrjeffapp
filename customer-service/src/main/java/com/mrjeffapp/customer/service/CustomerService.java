package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.api.dto.CustomerBusinessCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerSignUpRequest;
import com.mrjeffapp.customer.api.dto.CustomerUpdateRequest;
import com.mrjeffapp.customer.domain.Customer;

public interface CustomerService {

    Customer signUpCustomer(CustomerSignUpRequest request);

    Customer createCustomer(CustomerCreateRequest request);

    Customer createCustomerBusiness(CustomerBusinessCreateRequest request);

    Customer updateCustomerBusinessDetails(String customerId, String businessId, String businessName);

    Customer updateCustomer(String customerId, CustomerUpdateRequest customerUpdateRequest);

}
