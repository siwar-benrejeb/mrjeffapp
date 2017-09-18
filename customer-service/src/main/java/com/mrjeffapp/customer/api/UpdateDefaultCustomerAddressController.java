package com.mrjeffapp.customer.api;

import com.mrjeffapp.customer.api.dto.UpdateDefaultCustomerAddressRequest;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.projection.FullProjectionAddress;
import com.mrjeffapp.customer.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class UpdateDefaultCustomerAddressController {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateDefaultCustomerAddressController.class);

    private UpdateDefaultCustomerPickUpAddressService updateDefaultCustomerPickUpAddressService;
    private UpdateDefaultCustomerDeliveryAddressService updateDefaultCustomerDeliveryAddressService;
    private UpdateDefaultCustomerBillingAddressService updateDefaultCustomerBillingAddressService;

    private final ProjectionFactory projectionFactory;

    @Autowired
    public UpdateDefaultCustomerAddressController(
            UpdateDefaultCustomerPickUpAddressService updateDefaultCustomerPickUpAddressService,
            UpdateDefaultCustomerDeliveryAddressService updateDefaultCustomerDeliveryAddressService,
            UpdateDefaultCustomerBillingAddressService updateDefaultCustomerBillingAddressService,
            ProjectionFactory projectionFactory) {

        this.updateDefaultCustomerPickUpAddressService = updateDefaultCustomerPickUpAddressService;
        this.updateDefaultCustomerDeliveryAddressService = updateDefaultCustomerDeliveryAddressService;
        this.updateDefaultCustomerBillingAddressService = updateDefaultCustomerBillingAddressService;
        this.projectionFactory = projectionFactory;

    }

    @PatchMapping("/{id}/defaultpickupaddress")
    public ResponseEntity<FullProjectionAddress> updateDefaultPickUp(@PathVariable("id") String customerId, @Valid @RequestBody UpdateDefaultCustomerAddressRequest request) {
        LOG.debug("Update default customer pickup address with values: {}", request);

        Address address = updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
                request.getAddressId(),
                request.getState(),
                customerId
        ));

        FullProjectionAddress addressProjection = projectionFactory.createProjection(FullProjectionAddress.class, address);
        return ResponseEntity.ok(addressProjection);
    }

    @PatchMapping("/{id}/defaultdeliveryaddress")
    public ResponseEntity<FullProjectionAddress> updateDefaultDelivery(@PathVariable("id") String customerId, @Valid @RequestBody UpdateDefaultCustomerAddressRequest request) {
        LOG.debug("Update default customer delivery address with values: {}", request);

        Address address = updateDefaultCustomerDeliveryAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                request.getAddressId(),
                request.getState(),
                customerId
        ));

        FullProjectionAddress addressProjection = projectionFactory.createProjection(FullProjectionAddress.class, address);
        return ResponseEntity.ok(addressProjection);
    }

    @PatchMapping("/{id}/defaultbillingaddress")
    public ResponseEntity<FullProjectionAddress> updateDefaultBilling(@PathVariable("id") String customerId, @Valid @RequestBody UpdateDefaultCustomerAddressRequest request) {
        LOG.debug("Update default customer billing address with values: {}", request);

        Address address = updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                request.getAddressId(),
                request.getState(),
                customerId
        ));

        FullProjectionAddress addressProjection = projectionFactory.createProjection(FullProjectionAddress.class, address);
        return ResponseEntity.ok(addressProjection);
    }
}
