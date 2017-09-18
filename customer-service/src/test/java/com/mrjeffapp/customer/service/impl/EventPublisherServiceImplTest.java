package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.event.AddressCreatedEvent;
import com.mrjeffapp.event.CustomerCreatedEvent;
import com.mrjeffapp.event.CustomerEmailUpdatedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Date;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventPublisherServiceImplTest {
    public static final String SERVICE_EXCHANGE = "customer-service/amq.topic";
    public static final String ROUTING_CUSTOMER_CREATED = "customer.created";
    public static final String ROUTING_CUSTOMER_EMAIL_UPDATED = "customer.email.updated";
    public static final String ROUTING_ADDRESS_CREATED = "address.created";

    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";

    private static final String ADDRESS_ID = "2";
    private static final String ADDRESS_ADDRESS = "ADDRESS_ADDRESS";
    private static final String ADDRESS_NUMBER = "ADDRESS_NUMBER";
    private static final String ADDRESS_DETAILS = "ADDRESS_DETAILS";
    private static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
    private static final String ADDRESS_POSTAL_CODE_ID = "3";
    private static final String ADDRESS_CITY_ID = "4";
    private static final String ADDRESS_REGION_ID = "5";
    private static final String ADDRESS_COUNTRY_ID = "6";

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EventPublisherServiceImpl eventPublisherServiceImpl;

    @Test
    public void testCustomerCreatedEvent() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setName(CUSTOMER_NAME);
        customer.setLastName(CUSTOMER_LAST_NAME);
        customer.setPhoneNumber(CUSTOMER_PHONE);

        CustomerCreatedEvent event = eventPublisherServiceImpl.sendCustomerCreatedEvent(customer);

        verify(rabbitTemplate).convertAndSend(SERVICE_EXCHANGE, ROUTING_CUSTOMER_CREATED, event);

        assertThat("Event should have customer id", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("Event should have customer email", event.getEmail(), is(CUSTOMER_EMAIL));
        assertThat("Event should have customer name", event.getName(), is(CUSTOMER_NAME));
        assertThat("Event should have customer last name", event.getLastName(), is(CUSTOMER_LAST_NAME));
        assertThat("Event should have customer phone", event.getPhone(), is(CUSTOMER_PHONE));
        assertThat("Event should have event date", event.getEventDate(), any(Date.class));
    }

    @Test
    public void testAddressCreatedEvent() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Address address = new Address();
        address.setId(ADDRESS_ID);
        address.setAddress(ADDRESS_ADDRESS);
        address.setAddressNumber(ADDRESS_NUMBER);
        address.setAddressDetails(ADDRESS_DETAILS);
        address.setPostalCode(ADDRESS_POSTAL_CODE);
        address.setPostalCodeId(ADDRESS_POSTAL_CODE_ID);
        address.setCityId(ADDRESS_CITY_ID);
        address.setRegionId(ADDRESS_REGION_ID);
        address.setCountryId(ADDRESS_COUNTRY_ID);
        address.setCustomer(customer);

        AddressCreatedEvent event = eventPublisherServiceImpl.sendAddressCreatedEvent(address);

        verify(rabbitTemplate).convertAndSend(SERVICE_EXCHANGE, ROUTING_ADDRESS_CREATED, event);

        assertThat("Event should have address id", event.getAddressId(), is(ADDRESS_ID));
        assertThat("Event should have customer id", event.getCustomerId(), is(CUSTOMER_ID));
        assertThat("Event should have address", event.getAddress(), is(ADDRESS_ADDRESS));
        assertThat("Event should have address number", event.getAddressNumber(), is(ADDRESS_NUMBER));
        assertThat("Event should have address details", event.getAddressDetails(), is(ADDRESS_DETAILS));
        assertThat("Event should have postal code", event.getPostalCode(), is(ADDRESS_POSTAL_CODE));
        assertThat("Event should have postal code id", event.getPostalCodeId(), is(ADDRESS_POSTAL_CODE_ID));
        assertThat("Event should have city id", event.getCityId(), is(ADDRESS_CITY_ID));
        assertThat("Event should have region id", event.getRegionId(), is(ADDRESS_REGION_ID));
        assertThat("Event should have country id", event.getCountryId(), is(ADDRESS_COUNTRY_ID));
        assertThat("Event should have event date", event.getEventDate(), any(Date.class));
    }

    @Test
    public void testEmailUpdatedEvent() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        CustomerEmailUpdatedEvent event = eventPublisherServiceImpl.sendCustomerEmailUpdatedEvent(customer);

        verify(rabbitTemplate).convertAndSend(SERVICE_EXCHANGE, ROUTING_CUSTOMER_EMAIL_UPDATED, event);

        assertThat("Event should have customer id", event.getCustomerId(), is(CUSTOMER_ID));
    }

}
