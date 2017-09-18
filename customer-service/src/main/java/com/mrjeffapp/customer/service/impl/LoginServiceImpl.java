package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.LoginController;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.exception.AuthenticationException;
import com.mrjeffapp.customer.repository.CustomerRepository;
import com.mrjeffapp.customer.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private final CustomerRepository customerRepository;



    private final PasswordEncoder encoder;

    @Autowired
    public LoginServiceImpl(CustomerRepository customerRepository, PasswordEncoder encoder) {
        this.customerRepository = customerRepository;

        this.encoder = encoder;
    }
@Override
    public Customer authenticated(String username, String password) {
        Optional<Customer> userOptional = customerRepository.findByEmailAndEnabledTrue(username);

        if(!userOptional.isPresent()) {
            throw new AuthenticationException("Invalid customer credentials");
        }

        Customer customer = userOptional.get();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matchBackendPassword = encoder.matches(password, customer.getPassword());

        if(!matchBackendPassword) {



            throw new AuthenticationException("Invalid customer credentials");
        }else

        return customer;
    }


    private void generateHashPasswordAndUpdate(Customer customer, String passwordLoginRequest) {
        String encodedPassword = encoder.encode(passwordLoginRequest);

        customer.setPassword(encodedPassword);
        customer.setPasswordMigrated(true);

        customerRepository.save(customer);
    }

}
