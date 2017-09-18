package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.exception.InvalidCustomerException;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerBusinessDetails;
import com.mrjeffapp.customer.domain.CustomerType;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplUpdateBusinessDetailsTest {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_BUSINESS_ID = "CUSTOMER_BUSINESS_ID";
    private static final String CUSTOMER_BUSINESS_NAME = "CUSTOMER_BUSINESS_NAME";
    private static final String CUSTOMER_TYPE_BUSINESS = "BUSINESS";

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

    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer();

        CustomerType customerTypePrivate = new CustomerType();
        customerTypePrivate.setCode(CUSTOMER_TYPE_BUSINESS);
        Optional<CustomerType> customerTypeOptional = Optional.of(customerTypePrivate);
        when(customerTypeRepository.findByCodeAndActiveTrue(CUSTOMER_TYPE_BUSINESS)).thenReturn(customerTypeOptional);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class))).thenAnswer(a -> {
            Customer customer = a.getArgumentAt(0, Customer.class);
            return customer;
        });
    }

    @Test(expected = InvalidCustomerException.class)
    public void testErrorUserNotExists() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        customerServiceImpl.updateCustomerBusinessDetails(CUSTOMER_ID, CUSTOMER_BUSINESS_ID, CUSTOMER_BUSINESS_NAME);
    }

    @Test
    public void testCreateCustomerBusinessDetails() {
        customer = customerServiceImpl.updateCustomerBusinessDetails(CUSTOMER_ID, CUSTOMER_BUSINESS_ID, CUSTOMER_BUSINESS_NAME);
        CustomerBusinessDetails customerBusinessDetails = customer.getCustomerBusinessDetails();

        assertThat("Customer business id should be set", customerBusinessDetails.getBusinessId(), is(CUSTOMER_BUSINESS_ID));
        assertThat("Customer business name should be set", customerBusinessDetails.getBusinessName(), is(CUSTOMER_BUSINESS_NAME));
    }

    @Test
    public void testUpdateCustomerBusinessDetails() {
        customer.setCustomerBusinessDetails(new CustomerBusinessDetails());

        Customer customer = customerServiceImpl.updateCustomerBusinessDetails(CUSTOMER_ID, CUSTOMER_BUSINESS_ID, CUSTOMER_BUSINESS_NAME);

        customer = customerServiceImpl.updateCustomerBusinessDetails(CUSTOMER_ID, CUSTOMER_BUSINESS_ID, CUSTOMER_BUSINESS_NAME);
        CustomerBusinessDetails customerBusinessDetails = customer.getCustomerBusinessDetails();

        assertThat("Customer business id should be set", customerBusinessDetails.getBusinessId(), is(CUSTOMER_BUSINESS_ID));
        assertThat("Customer business name should be set", customerBusinessDetails.getBusinessName(), is(CUSTOMER_BUSINESS_NAME));
    }

}
