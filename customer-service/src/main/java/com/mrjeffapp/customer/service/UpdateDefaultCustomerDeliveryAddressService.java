package com.mrjeffapp.customer.service;

import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.client.admin.model.ValidateAddressResponse;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.exception.InvalidAddressException;
import com.mrjeffapp.customer.repository.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateDefaultCustomerDeliveryAddressService implements ApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateDefaultCustomerDeliveryAddressService.class);

    private AddressRepository addressRepository;
    private AdministrativeClient administrativeClient;

    @Autowired
    public UpdateDefaultCustomerDeliveryAddressService(AddressRepository addressRepository, AdministrativeClient administrativeClient) {
        this.addressRepository = addressRepository;
        this.administrativeClient = administrativeClient;
    }

    @Transactional
    public Address execute(UpdateDefaultCustomerDeliveryAddressDto dto) {

        LOG.debug("Update Default Customer Delivery Address: {}", dto);

        Optional<Address> optionalAddress = addressRepository.findByCustomerIdAndIdAndActiveTrue(dto.getCustomerId(), dto.getAddressId());

        if(!optionalAddress.isPresent()) {
            throw new InvalidAddressException("Address not exists for this customer");
        }

        Address address = optionalAddress.get();

        if(dto.getState()) {
            ValidateAddressResponse response = administrativeClient.validatePostalCode(address.getPostalCode(), address.getCityCode(), address.getCountryCode());
            if(!response.isValid()) {
                throw new InvalidAddressException("Postal code is invalid for delivery");
            }
        }

        addressRepository.setDisableDefaultDeliveryAddresses(dto.getCustomerId());

        address.setDefaultDelivery(dto.getState());

        return addressRepository.save(address);

    }

}
