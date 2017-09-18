package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CustomerUpdateRequest;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.exception.InvalidCustomerEmailException;
import com.mrjeffapp.customer.exception.InvalidCustomerException;
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

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplUpdateCustomerTest {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";

    private static final String CUSTOMER_EMAIL_UPDATED = "CUSTOMER_EMAIL_UPDATED@mrjeffapp.com";
    private static final String CUSTOMER_NAME_UPDATED = "CUSTOMER_NAME_UPDATED";
    private static final String CUSTOMER_LAST_NAME_UPDATED = "CUSTOMER_LAST_NAME_UPDATED";
    private static final String CUSTOMER_PHONE_UPDATED = "CUSTOMER_PHONE_UPDATED";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerBuilder customerBuilder;

    @Mock
    private EventPublisherService eventPublisherService;

    @Mock
    private CustomerTypeRepository customerTypeRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private CustomerUpdateRequest customerUpdateRequest;

    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setName(CUSTOMER_NAME);
        customer.setLastName(CUSTOMER_LAST_NAME);
        customer.setPhoneNumber(CUSTOMER_PHONE);

        customerUpdateRequest = new CustomerUpdateRequest();
        customerUpdateRequest.setEmail(CUSTOMER_EMAIL_UPDATED);
        customerUpdateRequest.setName(CUSTOMER_NAME_UPDATED);
        customerUpdateRequest.setLastName(CUSTOMER_LAST_NAME_UPDATED);
        customerUpdateRequest.setPhoneNumber(CUSTOMER_PHONE_UPDATED);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        when(customerRepository.findByEmail(CUSTOMER_EMAIL_UPDATED)).thenReturn(Optional.empty());

        when(customerRepository.save(any(Customer.class))).thenAnswer(answer -> {
            Customer customer = answer.getArgumentAt(0, Customer.class);
            return customer;
        });

    }

    @Test(expected = InvalidCustomerException.class)
    public void testCustomerNotFound() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        customerServiceImpl.updateCustomer(CUSTOMER_ID, customerUpdateRequest);
    }

    @Test(expected = InvalidCustomerEmailException.class)
    public void testCustomerEmailAlreadyUsed() {
        when(customerRepository.findByEmail(CUSTOMER_EMAIL_UPDATED)).thenReturn(Optional.of(new Customer()));

        customerServiceImpl.updateCustomer(CUSTOMER_ID, customerUpdateRequest);
    }

    @Test
    public void testCustomerCustomerUpdatedSameEmail() {
        customerUpdateRequest.setEmail(CUSTOMER_EMAIL);

        Customer customer = customerServiceImpl.updateCustomer(CUSTOMER_ID, customerUpdateRequest);

        verify(customerRepository).save(customer);
        verify(customerRepository, never()).findByEmail(CUSTOMER_EMAIL_UPDATED);
        verify(eventPublisherService, never()).sendCustomerEmailUpdatedEvent(customer);

        assertThat("Customer email should not be updated", customer.getEmail(), is(CUSTOMER_EMAIL));
        assertThat("Customer name should be updated", customer.getName(), is(CUSTOMER_NAME_UPDATED));
        assertThat("Customer last name should be updated", customer.getLastName(), is(CUSTOMER_LAST_NAME_UPDATED));
        assertThat("Customer phone should be updated", customer.getPhoneNumber(), is(CUSTOMER_PHONE_UPDATED));
    }

    @Test
    public void testCustomerCustomerUpdatedDifferentEmail() {
        Customer customer = customerServiceImpl.updateCustomer(CUSTOMER_ID, customerUpdateRequest);

        verify(customerRepository).save(customer);
        verify(customerRepository).findByEmail(CUSTOMER_EMAIL_UPDATED);
        verify(eventPublisherService).sendCustomerEmailUpdatedEvent(customer);

        assertThat("Customer email should be updated", customer.getEmail(), is(CUSTOMER_EMAIL_UPDATED));
        assertThat("Customer name should be updated", customer.getName(), is(CUSTOMER_NAME_UPDATED));
        assertThat("Customer last name should be updated", customer.getLastName(), is(CUSTOMER_LAST_NAME_UPDATED));
        assertThat("Customer phone should be updated", customer.getPhoneNumber(), is(CUSTOMER_PHONE_UPDATED));
    }

}
