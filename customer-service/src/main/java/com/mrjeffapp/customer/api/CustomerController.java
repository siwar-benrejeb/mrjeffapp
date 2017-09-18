package com.mrjeffapp.customer.api;

import com.mrjeffapp.customer.api.dto.*;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.projection.CreatedProjectionCustomer;
import com.mrjeffapp.customer.domain.projection.FullProjectionCustomer;
import com.mrjeffapp.customer.service.AppendCustomerNoteDto;
import com.mrjeffapp.customer.service.AppendCustomerNoteService;
import com.mrjeffapp.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    private final AppendCustomerNoteService appendCustomerNoteService;

    private final ProjectionFactory projectionFactory;

    @Autowired
    public CustomerController(CustomerService customerService,
                              ProjectionFactory projectionFactory,
                              AppendCustomerNoteService appendCustomerNoteService
                              ) {
        this.customerService = customerService;
        this.projectionFactory = projectionFactory;
        this.appendCustomerNoteService = appendCustomerNoteService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CreatedProjectionCustomer> signUpCustomer(@Valid @RequestBody CustomerSignUpRequest request) {
        LOG.debug("Sign up a customer with values: {}", request);

       Customer customer = customerService.signUpCustomer(request);

        CreatedProjectionCustomer customerProjection = projectionFactory.createProjection(CreatedProjectionCustomer.class, customer);
        return ResponseEntity.ok(customerProjection);
    }

    @PostMapping("/create")
    public ResponseEntity<FullProjectionCustomer> createCustomerWithAddresses(@Valid @RequestBody CustomerCreateRequest request) {
        LOG.debug("Create a private customer from the backend with values: {}", request);

        Customer customer = customerService.createCustomer(request);

        FullProjectionCustomer customerProjection = projectionFactory.createProjection(FullProjectionCustomer.class, customer);
        return ResponseEntity.ok(customerProjection);
    }

    @PatchMapping("/{id}/customerBusinessDetails")
    public ResponseEntity<FullProjectionCustomer> updateCustomerBusinessDetails(@PathVariable("id") String customerId, @Valid @RequestBody CustomerBusinessDetailsRequest request) {
        LOG.debug("Create a business customer from the backend with values: {}", request);

        Customer customer = customerService.updateCustomerBusinessDetails(customerId, request.getBusinessId(), request.getBusinessName());

        FullProjectionCustomer customerProjection = projectionFactory.createProjection(FullProjectionCustomer.class, customer);
        return ResponseEntity.ok(customerProjection);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FullProjectionCustomer> updateCustomer(@PathVariable("id") String customerId, @Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        LOG.debug("Update customer with with values: {}", customerUpdateRequest);

        Customer customer = customerService.updateCustomer(customerId, customerUpdateRequest);

        FullProjectionCustomer customerProjection = projectionFactory.createProjection(FullProjectionCustomer.class, customer);
        return ResponseEntity.ok(customerProjection);
    }

    @PostMapping("/{id}/appendNote")
    public ResponseEntity<FullProjectionCustomer> appendCustomernNote(@PathVariable("id") String customerId, @Valid @RequestBody AppendCustomerNoteRequest request) {
        LOG.debug("Append customer note with values: {}", request);

        Customer customer = appendCustomerNoteService.execute(new AppendCustomerNoteDto(
                customerId,
                request.getNote(),
                request.getUserId()
        ));

        FullProjectionCustomer customerProjection = projectionFactory.createProjection(FullProjectionCustomer.class, customer);
        return ResponseEntity.ok(customerProjection);
    }

}
