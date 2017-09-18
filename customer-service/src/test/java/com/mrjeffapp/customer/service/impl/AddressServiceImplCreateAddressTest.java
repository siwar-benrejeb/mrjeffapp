package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CreateAddressRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.exception.InvalidCustomerException;
import com.mrjeffapp.customer.repository.AddressRepository;
import com.mrjeffapp.customer.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceImplCreateAddressTest {
    private static final String CUSTOMER_ID = "CUSTOMER_ID";

    private static final String ADDRESS_ID = "ADDRESS_ID";
    private static final String ADDRESS_ALIAS = "ADDRESS_ALIAS";
    private static final String ADDRESS_ADDRESS = "ADDRESS_ADDRESS";
    private static final String ADDRESS_NUMBER = "ADDRESS_NUMBER";
    private static final String ADDRESS_DETAILS = "ADDRESS_DETAILS";
    private static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
    private static final String ADDRESS_CITY_CODE = "ADDRESS_CITY_CODE";
    private static final String ADDRESS_COUNTRY_CODE = "ADDRESS_COUNTRY_CODE";
    private static final String ADDRESS_CITY_NAME = "ADDRESS_CITY_NAME";

    @Mock
    private AdministrativeClient administrativeClient;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AddressServiceImpl addressServiceImpl;

    private CreateAddressRequest createAddressRequest;

    private Customer customer;

    private ValidateAddressResponse validateAddressResponse;

    @Before
    public void setUp() {
        createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setAlias(ADDRESS_ALIAS);
        createAddressRequest.setAddress(ADDRESS_ADDRESS);
        createAddressRequest.setAddressNumber(ADDRESS_NUMBER);
        createAddressRequest.setAddressDetails(ADDRESS_DETAILS);
        createAddressRequest.setPostalCode(ADDRESS_POSTAL_CODE);
        createAddressRequest.setCityName(ADDRESS_CITY_NAME);
        createAddressRequest.setCountryName(ADDRESS_CITY_CODE);
        createAddressRequest.setCountryCode(ADDRESS_COUNTRY_CODE);
        createAddressRequest.setDefaultDelivery(false);
        createAddressRequest.setDefaultPickup(false);
        createAddressRequest.setDefaultBilling(false);

        customer = new Customer();
        customer.setId(CUSTOMER_ID);

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));

        validateAddressResponse = new ValidateAddressResponse();
        validateAddressResponse.setValid(true);

        when(addressRepository.save(any(Address.class))).thenAnswer(a -> {
            Address address = a.getArgumentAt(0, Address.class);
            address.setId(ADDRESS_ID);
            return address;
        });
    }

    @Test(expected = InvalidCustomerException.class)
    public void testFailureCustomerNotFound() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());

        addressServiceImpl.createCustomerAddress(CUSTOMER_ID, createAddressRequest);
    }

    @Test
    public void testCreateAddressNoDefaults() {
        Address address = addressServiceImpl.createCustomerAddress(CUSTOMER_ID, createAddressRequest);

        verify(addressRepository, never()).setDisableDefaultPickupAddresses(CUSTOMER_ID);
        verify(addressRepository, never()).setDisableDefaultDeliveryAddresses(CUSTOMER_ID);
        verify(addressRepository, never()).setDisableDefaultBillingAddresses(CUSTOMER_ID);

        verify(addressRepository).save(address);

        assertThat("Address id should be set", address.getId(), is(ADDRESS_ID));
        assertThat("Customer id should be correct", address.getCustomer().getId(), is(CUSTOMER_ID));
    }

    @Test
    public void testCreateAddressPickupDefault() {
        createAddressRequest.setDefaultPickup(true);
        createAddressRequest.setDefaultDelivery(false);
        createAddressRequest.setDefaultBilling(false);

        Address address = addressServiceImpl.createCustomerAddress(CUSTOMER_ID, createAddressRequest);

        verify(addressRepository).setDisableDefaultPickupAddresses(CUSTOMER_ID);
        verify(addressRepository, never()).setDisableDefaultDeliveryAddresses(CUSTOMER_ID);
        verify(addressRepository, never()).setDisableDefaultBillingAddresses(CUSTOMER_ID);

        verify(addressRepository).save(address);

        assertThat("Address id should be set", address.getId(), is(ADDRESS_ID));
        assertThat("Customer id should be correct", address.getCustomer().getId(), is(CUSTOMER_ID));
    }

    @Test
    public void testCreateAddressDeliveryDefault() {
        createAddressRequest.setDefaultPickup(false);
        createAddressRequest.setDefaultDelivery(true);
        createAddressRequest.setDefaultBilling(false);

        Address address = addressServiceImpl.createCustomerAddress(CUSTOMER_ID, createAddressRequest);

        verify(addressRepository, never()).setDisableDefaultPickupAddresses(CUSTOMER_ID);
        verify(addressRepository).setDisableDefaultDeliveryAddresses(CUSTOMER_ID);
        verify(addressRepository, never()).setDisableDefaultBillingAddresses(CUSTOMER_ID);

        verify(addressRepository).save(address);

        assertThat("Address id should be set", address.getId(), is(ADDRESS_ID));
        assertThat("Customer id should be correct", address.getCustomer().getId(), is(CUSTOMER_ID));
    }

    @Test
    public void testCreateAddressBillingDefault() {
        createAddressRequest.setDefaultPickup(false);
        createAddressRequest.setDefaultDelivery(false);
        createAddressRequest.setDefaultBilling(true);

        Address address = addressServiceImpl.createCustomerAddress(CUSTOMER_ID, createAddressRequest);

        verify(addressRepository, never()).setDisableDefaultPickupAddresses(CUSTOMER_ID);
        verify(addressRepository, never()).setDisableDefaultDeliveryAddresses(CUSTOMER_ID);
        verify(addressRepository).setDisableDefaultBillingAddresses(CUSTOMER_ID);

        verify(addressRepository).save(address);

        assertThat("Address id should be set", address.getId(), is(ADDRESS_ID));
        assertThat("Customer id should be correct", address.getCustomer().getId(), is(CUSTOMER_ID));
    }


}
