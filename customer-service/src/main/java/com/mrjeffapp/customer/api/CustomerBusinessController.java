package com.mrjeffapp.customer.api;

import com.mrjeffapp.customer.api.dto.CustomerBusinessCreateRequest;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.projection.FullProjectionCustomer;
import com.mrjeffapp.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers/business")
public class CustomerBusinessController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerBusinessController.class);

    private final CustomerService customerService;

    private final ProjectionFactory projectionFactory;

    @Autowired
    public CustomerBusinessController(CustomerService customerService, ProjectionFactory projectionFactory) {
        this.customerService = customerService;
        this.projectionFactory = projectionFactory;
    }

    @PostMapping("/create")
    public ResponseEntity<FullProjectionCustomer> createCustomerWithAddresses(@Valid @RequestBody CustomerBusinessCreateRequest request) {
        LOG.debug("Create a business customer from the backend with values: {}", request);

        Customer customer = customerService.createCustomerBusiness(request);

        FullProjectionCustomer customerProjection = projectionFactory.createProjection(FullProjectionCustomer.class, customer);
        return ResponseEntity.ok(customerProjection);
    }

}
