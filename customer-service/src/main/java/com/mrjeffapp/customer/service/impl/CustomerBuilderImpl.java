package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CustomerCreateAddressRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.exception.InvalidAddressException;
import com.mrjeffapp.customer.service.CustomerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerBuilderImpl implements CustomerBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerBuilderImpl.class);

    private AdministrativeClient administrativeClient;

    @Autowired
    public CustomerBuilderImpl(AdministrativeClient administrativeClient) {
        this.administrativeClient = administrativeClient;
    }

    public Customer buildCustomer(CustomerCreateRequest customerRequest) {
        LOG.debug("buildCustomer from request {}", customerRequest);

        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());

        LOG.debug("built customer {}", customer);
        return  customer;
    }

    public Address buildAddress(CustomerCreateRequest customerRequest, CustomerCreateAddressRequest addressRequest) {
        LOG.debug("buildAddress customer from request {}", customerRequest);
        LOG.debug("buildAddress address from request {}", addressRequest);

        String postalCode = addressRequest.getPostalCode();
        String cityCode = addressRequest.getCityCode();
        String countryCode = addressRequest.getCountryCode();

        ValidateAddressResponse validateAddressResponse = administrativeClient.validatePostalCode(postalCode, cityCode, countryCode);
        LOG.debug("validateAddressResponse: {}", validateAddressResponse);

        if(!validateAddressResponse.isValid()) {
            throw new InvalidAddressException(String.format("Invalid address with values postalCode:%s, cityCode: %s, countryCode: %s", postalCode, cityCode, countryCode));
        }

        Address address = new Address();
        address.setAlias(addressRequest.getAlias());
        address.setPhoneNumber(customerRequest.getPhoneNumber());
        address.setAddress(addressRequest.getAddress());
        address.setAddressNumber(addressRequest.getAddressNumber());
        address.setAddressDetails(addressRequest.getAddressDetails());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setPostalCodeId(validateAddressResponse.getPostalCodeId());
        address.setCityId(validateAddressResponse.getCityId());
        address.setCityCode(validateAddressResponse.getCityCode());
        address.setCityName(validateAddressResponse.getCityName());
        address.setRegionId(validateAddressResponse.getRegionId());
        address.setCountryId(validateAddressResponse.getCountryId());
        address.setCountryCode(validateAddressResponse.getCountryCode());
        address.setCountryName(validateAddressResponse.getCountryName());
        address.setDefaultPickup(false);
        address.setDefaultDelivery(false);
        address.setDefaultBilling(false);
        address.setActive(true);

        LOG.debug("built address {}", address);
        return address;
    }

}
