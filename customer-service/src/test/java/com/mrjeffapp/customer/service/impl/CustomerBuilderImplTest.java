package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateAddressRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import com.mrjeffapp.customer.exception.InvalidAddressException;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerBuilderImplTest {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";
    private static final String CUSTOMER_PASSWORD = "-";

    private static final String ADDRESS_ALIAS = "ADDRESS_ALIAS";
    private static final String ADDRESS_ADDRESS = "ADDRESS_ADDRESS";
    private static final String ADDRESS_NUMBER = "ADDRESS_NUMBER";
    private static final String ADDRESS_DETAILS = "ADDRESS_DETAILS";
    private static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
    private static final String ADDRESS_CITY_CODE = "ADDRESS_CITY_CODE";
    private static final String ADDRESS_CITY_NAME = "ADDRESS_CITY_NAME";
    private static final String ADDRESS_COUNTRY_CODE = "ADDRESS_COUNTRY_CODE";
    private static final String ADDRESS_COUNTRY_NAME = "ADDRESS_COUNTRY_NAME";
    private static final String ADDRESS_POSTAL_CODE_ID = "3";
    private static final String ADDRESS_CITY_ID = "4";
    private static final String ADDRESS_REGION_ID = "5";
    private static final String ADDRESS_COUNTRY_ID = "6";

    @Mock
    private AdministrativeClient administrativeClient;

    @InjectMocks
    private CustomerBuilderImpl customerBuilderImpl;

    private CustomerCreateRequest customerRequest;

    private CustomerCreateAddressRequest addressRequest;

    @Before
    public void setUp() {
        addressRequest = buildMockAddress(ADDRESS_ALIAS);

        customerRequest = new CustomerCreateRequest();
        customerRequest.setEmail(CUSTOMER_EMAIL);
        customerRequest.setName(CUSTOMER_NAME);
        customerRequest.setLastName(CUSTOMER_LAST_NAME);
        customerRequest.setPhoneNumber(CUSTOMER_PHONE);
    }

    private CustomerCreateAddressRequest buildMockAddress(String addressAlias) {
        CustomerCreateAddressRequest address = new CustomerCreateAddressRequest();
        address.setAlias(addressAlias);
        address.setAddress(ADDRESS_ADDRESS);
        address.setAddressNumber(ADDRESS_NUMBER);
        address.setAddressDetails(ADDRESS_DETAILS);
        address.setPostalCode(ADDRESS_POSTAL_CODE);
        address.setCityCode(ADDRESS_CITY_CODE);
        address.setCountryCode(ADDRESS_COUNTRY_CODE);

        return address;
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = customerBuilderImpl.buildCustomer(customerRequest);

        assertThat("Customer email should be set", customer.getEmail(), is(CUSTOMER_EMAIL));
        assertThat("Customer name should be set", customer.getName(), is(CUSTOMER_NAME));
        assertThat("Customer last name should be set", customer.getLastName(), is(CUSTOMER_LAST_NAME));
        assertThat("Customer phone should be set", customer.getPhoneNumber(), is(CUSTOMER_PHONE));
    }

    @Test(expected = InvalidAddressException.class)
    public void testNotValidAddress() {
        ValidateAddressResponse validateAddressResponse = new ValidateAddressResponse();
        validateAddressResponse.setValid(false);

        when(administrativeClient.validatePostalCode(ADDRESS_POSTAL_CODE, ADDRESS_CITY_CODE, ADDRESS_COUNTRY_CODE)).thenReturn(validateAddressResponse);

        customerBuilderImpl.buildAddress(customerRequest, addressRequest);
    }

    @Test
    public void testCreateAddress() {
        ValidateAddressResponse validateAddressResponse = new ValidateAddressResponse();
        validateAddressResponse.setPostalCodeId(ADDRESS_POSTAL_CODE_ID);
        validateAddressResponse.setCityId(ADDRESS_CITY_ID);
        validateAddressResponse.setCityCode(ADDRESS_CITY_CODE);
        validateAddressResponse.setCityName(ADDRESS_CITY_NAME);
        validateAddressResponse.setRegionId(ADDRESS_REGION_ID);
        validateAddressResponse.setCountryId(ADDRESS_COUNTRY_ID);
        validateAddressResponse.setCountryCode(ADDRESS_COUNTRY_CODE);
        validateAddressResponse.setCountryName(ADDRESS_COUNTRY_NAME);
        validateAddressResponse.setValid(true);

        when(administrativeClient.validatePostalCode(ADDRESS_POSTAL_CODE, ADDRESS_CITY_CODE, ADDRESS_COUNTRY_CODE)).thenReturn(validateAddressResponse);

        Address address = customerBuilderImpl.buildAddress(customerRequest, addressRequest);

        assertThat("Address should be active", address.getActive(), is(true));
        assertThat("Address should have alias", address.getAlias(), is(ADDRESS_ALIAS));
        assertThat("Address should have phone", address.getPhoneNumber(), is(CUSTOMER_PHONE));
        assertThat("Address should have address", address.getAddress(), is(ADDRESS_ADDRESS));
        assertThat("Address should have address number", address.getAddressNumber(), is(ADDRESS_NUMBER));
        assertThat("Address should have address details", address.getAddressDetails(), is(ADDRESS_DETAILS));
        assertThat("Address should have postal code", address.getPostalCode(), is(ADDRESS_POSTAL_CODE));

        assertThat("Address should have postal code id", address.getPostalCodeId(), is(ADDRESS_POSTAL_CODE_ID));
        assertThat("Address should have city id", address.getCityId(), is(ADDRESS_CITY_ID));
        assertThat("Address should have city code", address.getCityCode(), is(ADDRESS_CITY_CODE));
        assertThat("Address should have city name", address.getCityName(), is(ADDRESS_CITY_NAME));
        assertThat("Address should have region id", address.getRegionId(), is(ADDRESS_REGION_ID));
        assertThat("Address should have country id", address.getCountryId(), is(ADDRESS_COUNTRY_ID));
        assertThat("Address should have country code", address.getCountryCode(), is(ADDRESS_COUNTRY_CODE));
        assertThat("Address should have country name", address.getCountryName(), is(ADDRESS_COUNTRY_NAME));
    }

}
