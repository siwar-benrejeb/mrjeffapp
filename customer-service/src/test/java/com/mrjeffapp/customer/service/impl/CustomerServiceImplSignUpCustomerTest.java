package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CustomerSignUpRequest;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerType;
import com.mrjeffapp.customer.exception.CustomerAlreadyRegistered;
import com.mrjeffapp.customer.repository.CustomerRepository;
import com.mrjeffapp.customer.repository.CustomerTypeRepository;
import com.mrjeffapp.customer.service.CustomerBuilder;
import com.mrjeffapp.customer.service.EventPublisherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplSignUpCustomerTest {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_TYPE_PRIVATE = "PRIVATE";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";
    private static final String CUSTOMER_PASSWORD = "CUSTOMER_PASSWORD";
    private static final String CUSTOMER_PASSWORD_ENCODED = "CUSTOMER_PASSWORD_ENCODED";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerBuilder customerBuilder;

    @Mock
    private EventPublisherService eventPublisherService;

    @Mock
    private CustomerTypeRepository customerTypeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private CustomerSignUpRequest customerRequest;

    private Customer customer;

    @Before
    public void setUp() {
        customerRequest = new CustomerSignUpRequest();
        customerRequest.setEmail(CUSTOMER_EMAIL);
        customerRequest.setName(CUSTOMER_NAME);
        customerRequest.setLastName(CUSTOMER_LAST_NAME);
        customerRequest.setPhoneNumber(CUSTOMER_PHONE);
        customerRequest.setPassword(CUSTOMER_PASSWORD);

        CustomerType customerTypePrivate = new CustomerType(CUSTOMER_TYPE_PRIVATE, "name", "description", true);

        Optional<CustomerType> customerTypeOptional = Optional.of(customerTypePrivate);
        when(customerTypeRepository.findByCodeAndActiveTrue(CUSTOMER_TYPE_PRIVATE)).thenReturn(customerTypeOptional);

        when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.empty());

        when(customerRepository.save(any(Customer.class))).thenAnswer(answer -> {
            Customer customer = answer.getArgumentAt(0, Customer.class);
            customer.setId(CUSTOMER_ID);
            return customer;
        });

        when(passwordEncoder.encode(CUSTOMER_PASSWORD)).thenReturn(CUSTOMER_PASSWORD_ENCODED);

    }

    @Test(expected = CustomerAlreadyRegistered.class)
    public void testSignUpCustomerAlreadyRegistered() {
        when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.of(new Customer()));

        customerServiceImpl.signUpCustomer(customerRequest);
    }

    @Test
    public void testSignUpCustomer() {
        Customer customer = customerServiceImpl.signUpCustomer(customerRequest);

        verify(customerRepository).save(any(Customer.class));

        verify(eventPublisherService).sendCustomerCreatedEvent(any(Customer.class));

        assertThat("Customer type should be private", customer.getCustomerType().getCode(), is(CUSTOMER_TYPE_PRIVATE));
        assertThat("Customer should be active", customer.getEnabled(), is(true));
        assertThat("Customer should not require change its password", customer.getResetPassword(), is(false));
        assertThat("Customer password should exists", customer.getPassword(), is(CUSTOMER_PASSWORD_ENCODED));
    }

}
