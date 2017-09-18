package com.mrjeffapp.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.customer.api.dto.CustomerCreateRequest;
import com.mrjeffapp.customer.api.dto.CustomerCreateAddressRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.config.Constants;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIT {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";

    private static final String ADDRESS_PICKUP_ID = "10";
    private static final String ADDRESS_DELIVERY_ID = "20";
    private static final String ADDRESS_BILLING_ID = "30";

    private static final String ADDRESS_ALIAS_1 = "ADDRESS_ALIAS_1";
    private static final String ADDRESS_ALIAS_2 = "ADDRESS_ALIAS_2";
    private static final String ADDRESS_ALIAS_3 = "ADDRESS_ALIAS_3";

    private static final String ADDRESS_ADDRESS = "ADDRESS_ADDRESS";
    private static final String ADDRESS_NUMBER = "ADDRESS_NUMBER";
    private static final String ADDRESS_DETAILS = "ADDRESS_DETAILS";
    private static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
    private static final String ADDRESS_CITY_CODE = "ADDRESS_CITY_CODE";
    private static final String ADDRESS_COUNTRY_CODE = "ADDRESS_COUNTRY_CODE";



    @MockBean
    private CustomerService customerService;

    @MockBean
    private AdministrativeClient administrativeClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CustomerCreateRequest customerRequest;

    private CustomerCreateAddressRequest pickupAddressRequest;

    private CustomerCreateAddressRequest deliveryAddressRequest;

    private CustomerCreateAddressRequest billingAddressRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        objectMapper = new ObjectMapper();

        customerRequest = new CustomerCreateRequest();
        customerRequest.setEmail(CUSTOMER_EMAIL);
        customerRequest.setName(CUSTOMER_NAME);
        customerRequest.setLastName(CUSTOMER_LAST_NAME);
        customerRequest.setPhoneNumber(CUSTOMER_PHONE);

        pickupAddressRequest = buildMockAddress(ADDRESS_ALIAS_1);
        deliveryAddressRequest = buildMockAddress(ADDRESS_ALIAS_2);
        billingAddressRequest = buildMockAddress(ADDRESS_ALIAS_3);

        given(customerService.createCustomer(any(CustomerCreateRequest.class))).will(answer -> {
            CustomerCreateRequest customerCreateRequest = answer.getArgumentAt(0, CustomerCreateRequest.class);

            Set<Address> addresses = new HashSet<>();
            if(customerCreateRequest.hasDeliveryAddress()) {
                Address deliveryAddress = new Address();
                deliveryAddress.setAlias(customerCreateRequest.getDeliveryAddress().getAlias());

                addresses.add(deliveryAddress);

            }

            if(customerCreateRequest.hasBillingAddress()) {
                Address billingAddress = new Address();
                billingAddress.setAlias(customerCreateRequest.getBillingAddress().getAlias());

                addresses.add(billingAddress);
            }

            Address pickupAddress = new Address();
            pickupAddress.setAlias(customerCreateRequest.getPickupAddress().getAlias());
            addresses.add(pickupAddress);

            Customer customer = new Customer();
            customer.setId(CUSTOMER_ID);
            customer.setAddresses(addresses);

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

    @Test
    public void testFailureWithoutPickupAddress() throws Exception {
        String json = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testCreateCustomerWithPickupAddress() throws Exception {
        customerRequest.setPickupAddress(pickupAddressRequest);

        String json = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(CUSTOMER_ID))
                .andExpect(jsonPath("addresses", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCustomerWithPickupDeliveryAddresses() throws Exception {
        customerRequest.setPickupAddress(pickupAddressRequest);
        customerRequest.setDeliveryAddress(deliveryAddressRequest);

        String json = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(CUSTOMER_ID))
                .andExpect(jsonPath("addresses", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCustomerWithPickupDeliveryBillingAddresses() throws Exception {
        customerRequest.setPickupAddress(pickupAddressRequest);
        customerRequest.setDeliveryAddress(deliveryAddressRequest);
        customerRequest.setBillingAddress(billingAddressRequest);

        String json = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers/create")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(CUSTOMER_ID))
                .andExpect(jsonPath("addresses", hasSize(3)))
                .andExpect(status().isOk());
    }

}
