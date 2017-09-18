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
public class UpdateDefaultCustomerPickUpAddressService implements ApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateDefaultCustomerPickUpAddressService.class);

    private AddressRepository addressRepository;
    private AdministrativeClient administrativeClient;

    @Autowired
    public UpdateDefaultCustomerPickUpAddressService(AddressRepository addressRepository, AdministrativeClient administrativeClient) {
        this.addressRepository = addressRepository;
        this.administrativeClient = administrativeClient;
    }

    @Transactional
    public Address execute(UpdateDefaultCustomerPickUpAddressDto dto) {
        LOG.debug("Update Default Customer PickUp Address: {}", dto);
        Optional<Address> optionalAddress = addressRepository.findByCustomerIdAndIdAndActiveTrue(dto.getCustomerId(), dto.getAddressId());

        if(!optionalAddress.isPresent()) {
            throw new InvalidAddressException("Address not exists for this customer");
        }

        Address address = optionalAddress.get();

        if(dto.getState()) {
            ValidateAddressResponse response = administrativeClient.validatePostalCode(address.getPostalCode(), address.getCityCode(), address.getCountryCode());
            if(!response.isValid()) {
                throw new InvalidAddressException("Postal code is invalid for pickup");
            }
        }

        addressRepository.setDisableDefaultPickupAddresses(dto.getCustomerId());

        address.setDefaultPickup(dto.getState());

        return addressRepository.save(address);
    }
}
