package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.domain.Customer;

public interface LoginService {

    Customer authenticated(String username, String password);

}
