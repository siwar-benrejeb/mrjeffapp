package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CreateAddressRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.exception.InvalidCustomerException;
import com.mrjeffapp.customer.repository.AddressRepository;
import com.mrjeffapp.customer.repository.CustomerRepository;
import com.mrjeffapp.customer.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerBuilderImpl.class);

    private CustomerRepository customerRepository;

    private AddressRepository addressRepository;

    private AdministrativeClient administrativeClient;

    @Autowired
    public AddressServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository, AdministrativeClient administrativeClient) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.administrativeClient = administrativeClient;
    }

    @Override
    @Transactional
    public Address createCustomerAddress(String customerId, CreateAddressRequest customerAddressRequest) {
        Customer customer = findAndValidateCustomer(customerId);

        Address address = createAddressFromRequest(customerAddressRequest);
        address.setCustomer(customer);

        disablePreviousDefaultAddressValues(customerId, customerAddressRequest);

        Address createdAddress = addressRepository.save(address);

        return createdAddress;
    }

    private void disablePreviousDefaultAddressValues(String customerId, CreateAddressRequest customerAddressRequest) {
        if(customerAddressRequest.getDefaultPickup()) {
            LOG.debug("Disabling previous default pickup address for customer: {}", customerId);
            addressRepository.setDisableDefaultPickupAddresses(customerId);
        }

        if(customerAddressRequest.getDefaultDelivery()) {
            LOG.debug("Disabling previous default delivery address for customer: {}", customerId);
            addressRepository.setDisableDefaultDeliveryAddresses(customerId);
        }

        if(customerAddressRequest.getDefaultBilling()) {
            LOG.debug("Disabling previous default billing address for customer: {}", customerId);
            addressRepository.setDisableDefaultBillingAddresses(customerId);
        }

    }

    private Customer findAndValidateCustomer(String customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()) {
            String message = "Customer not found with id: " + customerId;
            throw new InvalidCustomerException(message);
        }

        return customerOptional.get();
    }

    public Address createAddressFromRequest(CreateAddressRequest addressRequest) {
        Address address = new Address();
        address.setAlias(addressRequest.getAlias());
        address.setPhoneNumber(addressRequest.getPhoneNumber());
        address.setAddress(addressRequest.getAddress());
        address.setAddressNumber(addressRequest.getAddressNumber());
        address.setAddressDetails(addressRequest.getAddressDetails());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setCountryCode(addressRequest.getCountryCode());
        address.setCityName(addressRequest.getCityName());
        address.setCountryName(addressRequest.getCountryName());

        address.setDefaultPickup(addressRequest.getDefaultPickup());
        address.setDefaultDelivery(addressRequest.getDefaultDelivery());
        address.setDefaultBilling(addressRequest.getDefaultBilling());
        address.setActive(true);

        LOG.debug("create address {}", address);
        return address;
    }

}
