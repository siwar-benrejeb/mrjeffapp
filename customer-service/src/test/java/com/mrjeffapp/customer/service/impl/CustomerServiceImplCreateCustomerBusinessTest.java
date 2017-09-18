package com.mrjeffapp.customer.service.impl;

import com.mrjeffapp.customer.api.dto.CustomerBusinessCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateAddressRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.exception.CustomerAlreadyRegistered;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerType;
import com.mrjeffapp.customer.repository.CustomerRepository;
import com.mrjeffapp.customer.repository.CustomerTypeRepository;
import com.mrjeffapp.customer.service.CustomerBuilder;
import com.mrjeffapp.customer.service.EventPublisherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplCreateCustomerBusinessTest {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_TYPE_BUSINESS = "BUSINESS";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";
    private static final String CUSTOMER_PASSWORD = "-";
    private static final String CUSTOMER_BUSINESS_ID = "CUSTOMER_BUSINESS_ID";
    private static final String CUSTOMER_BUSINESS_NAME = "CUSTOMER_BUSINESS_NAME";

    private static final String ADDRESS_ALIAS_PICKUP = "ADDRESS_ALIAS_PICKUP";
    private static final String ADDRESS_ALIAS_DELIVERY = "ADDRESS_ALIAS_DELIVERY";
    private static final String ADDRESS_ALIAS_BILLING = "ADDRESS_ALIAS_BILLING";

    private static final String ADDRESS_ADDRESS = "ADDRESS_ADDRESS";
    private static final String ADDRESS_NUMBER = "ADDRESS_NUMBER";
    private static final String ADDRESS_DETAILS = "ADDRESS_DETAILS";
    private static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
    private static final String ADDRESS_CITY_CODE = "ADDRESS_CITY_CODE";
    private static final String ADDRESS_COUNTRY_CODE = "ADDRESS_COUNTRY_CODE";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerBuilder customerBuilder;

    @Mock
    private EventPublisherService eventPublisherService;

    @Mock
    private CustomerTypeRepository customerTypeRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;

    private CustomerBusinessCreateRequest customerRequest;

    private CustomerCreateAddressRequest pickupAddressRequest;

    private CustomerCreateAddressRequest deliveryAddressRequest;

    private CustomerCreateAddressRequest billingAddressRequest;

    private Customer customer;

    @Before
    public void setUp() {
        pickupAddressRequest = buildMockAddress(ADDRESS_ALIAS_PICKUP);
        deliveryAddressRequest = buildMockAddress(ADDRESS_ALIAS_DELIVERY);
        billingAddressRequest = buildMockAddress(ADDRESS_ALIAS_BILLING);

        customerRequest = new CustomerBusinessCreateRequest();
        customerRequest.setEmail(CUSTOMER_EMAIL);
        customerRequest.setName(CUSTOMER_NAME);
        customerRequest.setLastName(CUSTOMER_LAST_NAME);
        customerRequest.setPhoneNumber(CUSTOMER_PHONE);
        customerRequest.setBusinessId(CUSTOMER_BUSINESS_ID);
        customerRequest.setBusinessName(CUSTOMER_BUSINESS_NAME);
        customerRequest.setPickupAddress(pickupAddressRequest);

        CustomerType customerTypePrivate = new CustomerType();
        customerTypePrivate.setCode(CUSTOMER_TYPE_BUSINESS);
        Optional<CustomerType> customerTypeOptional = Optional.of(customerTypePrivate);
        when(customerTypeRepository.findByCodeAndActiveTrue(CUSTOMER_TYPE_BUSINESS)).thenReturn(customerTypeOptional);

        when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.empty());

        when(customerBuilder.buildCustomer(customerRequest)).then(answer -> {
            CustomerCreateRequest customerCreateRequest = answer.getArgumentAt(0, CustomerCreateRequest.class);

            Customer customer = new Customer();
            customer.setEmail(customerCreateRequest.getEmail());

            return customer;
        });

        when(customerBuilder.buildAddress(eq(customerRequest), any(CustomerCreateAddressRequest.class))).then(answer -> {
            CustomerCreateAddressRequest customerCreateAddressRequest = answer.getArgumentAt(1, CustomerCreateAddressRequest.class);

            Address address = new Address();
            address.setAlias(customerCreateAddressRequest.getAlias());
            address.setCustomer(customer);

            return address;
        });

        when(customerRepository.save(any(Customer.class))).thenAnswer(answer -> {
            Customer customer = answer.getArgumentAt(0, Customer.class);
            customer.setId(CUSTOMER_ID);
            return customer;
        });

    }

    private CustomerCreateAddressRequest buildMockAddress(String addressAlias) {
        CustomerCreateAddressRequest address = new CustomerCreateAddressRequest();
        address.setAlias(addressAlias);
        address.setAddress(ADDRESS_ADDRESS);
        address.setAddressNumber(ADDRESS_NUMBER);
        address.setAddressDetails(ADDRESS_DETAILS);
        address.setPostalCode(ADDRESS_POSTAL_CODE);
        address.setCityCode(ADDRESS_CITY_CODE);
        address.setCountryCode(ADDRESS_COUNTRY_CODE);

        return address;
    }

    @Test(expected = CustomerAlreadyRegistered.class)
    public void testCreateCustomerAlreadyRegistered() {
        when(customerRepository.findByEmail(CUSTOMER_EMAIL)).thenReturn(Optional.of(new Customer()));

        customerServiceImpl.createCustomerBusiness(customerRequest);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = customerServiceImpl.createCustomerBusiness(customerRequest);

        assertThat("Customer type should be business", customer.getCustomerType().getCode(), is(CUSTOMER_TYPE_BUSINESS));
        assertThat("Customer should be not active", customer.getEnabled(), is(false));
        assertThat("Customer should require change its password", customer.getResetPassword(), is(true));
        assertThat("Customer password should be invalid", customer.getPassword(), is(CUSTOMER_PASSWORD));
        assertThat("Customer should have pickup address", customer.getAddresses().size(), is(1));
        assertThat("Customer should have business id", customer.getCustomerBusinessDetails().getBusinessId(), is(CUSTOMER_BUSINESS_ID));
        assertThat("Customer should have business name", customer.getCustomerBusinessDetails().getBusinessName(), is(CUSTOMER_BUSINESS_NAME));
    }

    @Test
    public void testCreateCustomerPickupAddress() {
        Customer customer = customerServiceImpl.createCustomerBusiness(customerRequest);

        Address pickupAddress = customer.getAddresses().stream()
                                                            .filter(address -> ADDRESS_ALIAS_PICKUP.equals(address.getAlias()))
                                                            .findFirst()
                                                            .get();

        assertThat("Customer should have pickup address", customer.getAddresses().size(), is(1));
        assertThat("Customer type should be business", customer.getCustomerType().getCode(), is(CUSTOMER_TYPE_BUSINESS));

        assertThat("Pickup address is default pickup", pickupAddress.getDefaultPickup(), is(true));
        assertThat("Pickup address is default delivery", pickupAddress.getDefaultDelivery(), is(true));
        assertThat("Pickup address is default billing", pickupAddress.getDefaultBilling(), is(true));
    }

    @Test
    public void testCreateCustomerPickupDeliveryAddress() {
        customerRequest.setDeliveryAddress(deliveryAddressRequest);

        Customer customer = customerServiceImpl.createCustomerBusiness(customerRequest);

        Address pickupAddress = customer.getAddresses().stream()
                .filter(address -> ADDRESS_ALIAS_PICKUP.equals(address.getAlias()))
                .findFirst()
                .get();

        Address deliveryAddress = customer.getAddresses().stream()
                .filter(address -> ADDRESS_ALIAS_DELIVERY.equals(address.getAlias()))
                .findFirst()
                .get();

        assertThat("Customer should have pickup address", customer.getAddresses().size(), is(2));
        assertThat("Customer type should be business", customer.getCustomerType().getCode(), is(CUSTOMER_TYPE_BUSINESS));

        assertThat("Pickup address is default pickup", pickupAddress.getDefaultPickup(), is(true));
        assertThat("Pickup address is not default delivery", pickupAddress.getDefaultDelivery(), is(false));
        assertThat("Pickup address is default billing", pickupAddress.getDefaultBilling(), is(true));

        assertThat("Delivery address is not default pickup", deliveryAddress.getDefaultPickup(), is(false));
        assertThat("Delivery address is default delivery", deliveryAddress.getDefaultDelivery(), is(true));
        assertThat("Delivery address is not default billing", deliveryAddress.getDefaultBilling(), is(false));
    }

    @Test
    public void testCreateCustomerPickupDeliveryBillingAddress() {
        customerRequest.setDeliveryAddress(deliveryAddressRequest);
        customerRequest.setBillingAddress(billingAddressRequest);

        Customer customer = customerServiceImpl.createCustomerBusiness(customerRequest);

        Address pickupAddress = customer.getAddresses().stream()
                .filter(address -> ADDRESS_ALIAS_PICKUP.equals(address.getAlias()))
                .findFirst()
                .get();

        Address deliveryAddress = customer.getAddresses().stream()
                .filter(address -> ADDRESS_ALIAS_DELIVERY.equals(address.getAlias()))
                .findFirst()
                .get();

        Address billingAddress = customer.getAddresses().stream()
                .filter(address -> ADDRESS_ALIAS_BILLING.equals(address.getAlias()))
                .findFirst()
                .get();

        assertThat("Customer should have pickup address", customer.getAddresses().size(), is(3));
        assertThat("Customer type should be business", customer.getCustomerType().getCode(), is(CUSTOMER_TYPE_BUSINESS));

        assertThat("Pickup address is default pickup", pickupAddress.getDefaultPickup(), is(true));
        assertThat("Pickup address is not default delivery", pickupAddress.getDefaultDelivery(), is(false));
        assertThat("Pickup address is not default billing", pickupAddress.getDefaultBilling(), is(false));

        assertThat("Delivery address is not default pickup", deliveryAddress.getDefaultPickup(), is(false));
        assertThat("Delivery address is default delivery", deliveryAddress.getDefaultDelivery(), is(true));
        assertThat("Delivery address is not default billing", deliveryAddress.getDefaultBilling(), is(false));

        assertThat("Billing address is not default pickup", billingAddress.getDefaultPickup(), is(false));
        assertThat("Billing address is not default delivery", billingAddress.getDefaultDelivery(), is(false));
        assertThat("Billing address not default billing", billingAddress.getDefaultBilling(), is(true));
    }

}
