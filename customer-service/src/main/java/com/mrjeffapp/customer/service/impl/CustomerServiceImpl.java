package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CustomerBusinessCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerSignUpRequest;
import com.mrjeffapp.customer.api.dto.CustomerUpdateRequest;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerBusinessDetails;
import com.mrjeffapp.customer.domain.CustomerType;
import com.mrjeffapp.customer.exception.CustomerAlreadyRegistered;
import com.mrjeffapp.customer.exception.InvalidCustomerEmailException;
import com.mrjeffapp.customer.exception.InvalidCustomerException;
import com.mrjeffapp.customer.repository.CustomerRepository;
import com.mrjeffapp.customer.repository.CustomerTypeRepository;
import com.mrjeffapp.customer.service.CustomerBuilder;
import com.mrjeffapp.customer.service.CustomerService;
import com.mrjeffapp.customer.service.EventPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.mrjeffapp.customer.config.Constants.CUSTOMER_TYPE_BUSINESS_CODE;
import static com.mrjeffapp.customer.config.Constants.CUSTOMER_TYPE_PRIVATE_CODE;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private static final String EMPTY_PASSWORD = "-";

    private final CustomerBuilder customerBuilder;

    private final CustomerRepository customerRepository;

    private final CustomerTypeRepository customerTypeRepository;

    private final EventPublisherService eventPublisherService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerBuilder customerBuilder, CustomerRepository customerRepository, CustomerTypeRepository customerTypeRepository, EventPublisherService eventPublisherService, PasswordEncoder passwordEncoder) {
        this.customerBuilder = customerBuilder;
        this.customerRepository = customerRepository;
        this.customerTypeRepository = customerTypeRepository;
        this.eventPublisherService = eventPublisherService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Customer signUpCustomer(CustomerSignUpRequest request) {
        checkUserAlreadyRegistered(request.getEmail());

        Optional<CustomerType> customerTypeOptional = customerTypeRepository.findByCodeAndActiveTrue(CUSTOMER_TYPE_PRIVATE_CODE);
        CustomerType customerType = customerTypeOptional.get();

        String email = request.getEmail();
        String plainPassword = request.getPassword();
        String name = request.getName();
        String lastName = request.getLastName();
        String phoneNumber = request.getPhoneNumber();
        String password = passwordEncoder.encode(plainPassword);


        Customer customer = new Customer(email, password, name, lastName, phoneNumber, customerType);
        customer.setLanguageCode("ES");
        LOG.info("Sign up customer with email: {}", email);

        Customer createdCustomer = saveAndPublishEvent(customer);

        return createdCustomer;
    }

    @Override
    public Customer createCustomer(CustomerCreateRequest request) {
        Customer customer = createCustomerByType(request, CUSTOMER_TYPE_PRIVATE_CODE);

        Customer createdCustomer = saveAndPublishEvent(customer);

        return createdCustomer;
    }

    @Override
    public Customer createCustomerBusiness(CustomerBusinessCreateRequest request) {
        Customer customer = createCustomerByType(request, CUSTOMER_TYPE_BUSINESS_CODE);

        CustomerBusinessDetails customerBusinessDetails = new CustomerBusinessDetails();
        customerBusinessDetails.setBusinessId(request.getBusinessId());
        customerBusinessDetails.setBusinessName(request.getBusinessName());
        customerBusinessDetails.setCustomer(customer);

        LOG.debug("Creating customer B2B details with values: customerBusinessDetails={}", customerBusinessDetails);

        customer.setCustomerBusinessDetails(customerBusinessDetails);

        Customer createdCustomer = saveAndPublishEvent(customer);

        return createdCustomer;
    }

    private Customer createCustomerByType(CustomerCreateRequest request, String customerTypeCode) {
        checkUserAlreadyRegistered(request.getEmail());
        LOG.debug("Creating customer with type={}", customerTypeCode);

        Set<Address> addresses = buildCustomerAddresses(request);

        Customer customer = customerBuilder.buildCustomer(request);
        customer.setEnabled(false);
        customer.setResetPassword(true);
        customer.setPassword(EMPTY_PASSWORD);
        customer.setAddresses(addresses);


        addresses.forEach(address -> address.setCustomer(customer));

        Optional<CustomerType> customerTypeOptional = customerTypeRepository.findByCodeAndActiveTrue(customerTypeCode);
        CustomerType customerType = customerTypeOptional.get();

        customer.setCustomerType(customerType);

        return customer;
    }

    private Customer saveAndPublishEvent(Customer customer) {
        Customer createdCustomer = saveCustomer(customer);

        publishCreationEvents(createdCustomer);
        return createdCustomer;
    }

    @Transactional
    public Customer saveCustomer(Customer customer) {
        Customer createdCustomer = customerRepository.save(customer);

        return createdCustomer;
    }

    private void checkUserAlreadyRegistered(String email) {
        LOG.debug("Checking customer already registered with email: {}", email);

        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if(customerOptional.isPresent()) {
            String message = "Customer already registered with email: " + email;
            throw new CustomerAlreadyRegistered(message);
        }
    }

    private Set<Address> buildCustomerAddresses(CustomerCreateRequest request) {
        Set<Address> addresses = new HashSet<>();

        Address pickupAddress = customerBuilder.buildAddress(request, request.getPickupAddress());
        pickupAddress.setDefaultPickup(true);
        pickupAddress.setDefaultDelivery(false);
        pickupAddress.setDefaultBilling(false);

        addresses.add(pickupAddress);

        LOG.debug("customer has delivery address: {}", request.hasDeliveryAddress());
        if(request.hasDeliveryAddress()) {
            Address deliveryAddress = customerBuilder.buildAddress(request, request.getDeliveryAddress());
            deliveryAddress.setDefaultPickup(false);
            deliveryAddress.setDefaultDelivery(true);
            deliveryAddress.setDefaultBilling(false);

            addresses.add(deliveryAddress);

        } else {
            LOG.debug("pickup address is default delivery address");
            pickupAddress.setDefaultDelivery(true);
        }

        LOG.debug("customer has billing address: {}", request.hasBillingAddress());
        if(request.hasBillingAddress()) {
            Address billingAddress = customerBuilder.buildAddress(request, request.getBillingAddress());
            billingAddress.setDefaultPickup(false);
            billingAddress.setDefaultDelivery(false);
            billingAddress.setDefaultBilling(true);

            addresses.add(billingAddress);

        } else {
            LOG.debug("pickup address is default billing address");
            pickupAddress.setDefaultBilling(true);
        }

        return addresses;
    }

    private void publishCreationEvents(Customer createdCustomer) {
        LOG.debug("Sending customer event for customer: customerId={}", createdCustomer.getId());
        eventPublisherService.sendCustomerCreatedEvent(createdCustomer);
    }

    @Override
    @Transactional
    public Customer updateCustomerBusinessDetails(String customerId, String customerBusinessId, String customerBusinessName) {
        LOG.debug("Updating customerBusiness details customerId={}, customerBusinessId={}, customerBusinessName={}", customerId, customerBusinessId, customerBusinessName);

        Customer customer = findCustomerById(customerId);

        CustomerBusinessDetails customerBusinessDetails = (customer.getCustomerBusinessDetails() != null) ? customer.getCustomerBusinessDetails() : new CustomerBusinessDetails();
        customerBusinessDetails.setBusinessId(customerBusinessId);
        customerBusinessDetails.setBusinessName(customerBusinessName);
        customerBusinessDetails.setCustomer(customer);

        customer.setCustomerBusinessDetails(customerBusinessDetails);

        Optional<CustomerType> customerTypeOptional = customerTypeRepository.findByCodeAndActiveTrue(CUSTOMER_TYPE_BUSINESS_CODE);
        CustomerType customerType = customerTypeOptional.get();
        customer.setCustomerType(customerType);

        Customer updateCustomer = customerRepository.save(customer);
        return updateCustomer;
    }

    private Customer findCustomerById(String customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()) {
            throw new InvalidCustomerException("Customer not found with customerId=" + customerId);
        }
        return customerOptional.get();
    }

    @Override
    public Customer updateCustomer(String customerId, CustomerUpdateRequest customerUpdateRequest) {
        LOG.debug("update customer={}, values={}", customerId, customerUpdateRequest);

        Customer customer = findCustomerById(customerId);
        String currentEmail = customer.getEmail();
        String updatedEmail = customerUpdateRequest.getEmail();

        boolean emailChanged = !currentEmail.equals(updatedEmail);

        if(emailChanged) {
            LOG.debug("Email changed: userId={}, old={}, new={}", customerId, currentEmail, updatedEmail);
            verifyEmailAlreadyInUse(updatedEmail);
        }

        customer.setName(customerUpdateRequest.getName());
        customer.setLastName(customerUpdateRequest.getLastName());
        customer.setEmail(customerUpdateRequest.getEmail());
        customer.setPhoneNumber(customerUpdateRequest.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(customer);

        if(emailChanged) {
            eventPublisherService.sendCustomerEmailUpdatedEvent(updatedCustomer);
        }

        return updatedCustomer;
    }

    private void verifyEmailAlreadyInUse(String updatedEmail) {
        Optional customerOptional = customerRepository.findByEmail(updatedEmail);
        if(customerOptional.isPresent()) {
            throw new InvalidCustomerEmailException("Email already in use: " + updatedEmail);
        }
    }

}
