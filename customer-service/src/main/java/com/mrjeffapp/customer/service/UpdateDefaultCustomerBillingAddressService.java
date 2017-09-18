package com.mrjeffapp.customer.service;

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
public class UpdateDefaultCustomerBillingAddressService implements ApplicationService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateDefaultCustomerBillingAddressService.class);

    private AddressRepository addressRepository;

    @Autowired
    public UpdateDefaultCustomerBillingAddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public Address execute(UpdateDefaultCustomerBillingAddressDto dto) {

        LOG.debug("Update Default Customer Billing  Address: {}", dto);

        Optional<Address> address = addressRepository.findByCustomerIdAndIdAndActiveTrue(dto.getCustomerId(), dto.getAddressId());

        if(!address.isPresent()) {
            throw new InvalidAddressException("Address not exists for this customer");
        }

        addressRepository.setDisableDefaultBillingAddresses(dto.getCustomerId());

        address.get().setDefaultBilling(dto.getState());

        return addressRepository.save(address.get());

    }
}
