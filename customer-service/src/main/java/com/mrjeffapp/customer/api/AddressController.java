package com.mrjeffapp.customer.api;

import com.mrjeffapp.customer.api.dto.CreateAddressRequest;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.projection.FullProjectionAddress;
import com.mrjeffapp.customer.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AddressController {
    private static final Logger LOG = LoggerFactory.getLogger(Address.class);

    private final AddressService addressService;

    private final ProjectionFactory projectionFactory;

    @Autowired
    public AddressController(AddressService addressService, ProjectionFactory projectionFactory) {
        this.addressService = addressService;
        this.projectionFactory = projectionFactory;
    }

    @PostMapping("/customers/{id}/addresses")
    public ResponseEntity<FullProjectionAddress> createCustomerAddresses(@PathVariable("id") String customerId, @Valid @RequestBody CreateAddressRequest addressRequest) {
        LOG.debug("Create a customer address: customer={}, address={}", customerId, addressRequest);

        Address address = addressService.createCustomerAddress(customerId, addressRequest);

        FullProjectionAddress addressProjection = projectionFactory.createProjection(FullProjectionAddress.class, address);
        return ResponseEntity.ok(addressProjection);
    }

}
