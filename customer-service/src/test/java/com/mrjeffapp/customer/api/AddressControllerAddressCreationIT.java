package com.mrjeffapp.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.customer.api.dto.CreateAddressRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.config.Constants;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.service.AddressService;
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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerAddressCreationIT {
    private static final String CUSTOMER_ID = "1";

    private static final String ADDRESS_ID = "ADDRESS_ID";
    private static final String ADDRESS_ALIAS = "ADDRESS_ALIAS";
    private static final String ADDRESS_ADDRESS = "ADDRESS_ADDRESS";
    private static final String ADDRESS_NUMBER = "ADDRESS_NUMBER";
    private static final String ADDRESS_DETAILS = "ADDRESS_DETAILS";
    private static final String ADDRESS_POSTAL_CODE = "ADDRESS_POSTAL_CODE";
    private static final String ADDRESS_CITY_NAME = "ADDRESS_CITY_NAME";
    private static final String ADDRESS_COUNTRY_CODE = "ADDRESS_COUNTRY_CODE";
    private static final String ADDRESS_COUNTRY_NAME= "ADDRESS_COUNTRY_NAME";



    @MockBean
    private AddressService addressService;

    @MockBean
    private AdministrativeClient administrativeClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CreateAddressRequest createAddressRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        objectMapper = new ObjectMapper();

        createAddressRequest = new CreateAddressRequest();
        createAddressRequest.setAlias(ADDRESS_ALIAS);
        createAddressRequest.setAddress(ADDRESS_ADDRESS);
        createAddressRequest.setAddressNumber(ADDRESS_NUMBER);
        createAddressRequest.setAddressDetails(ADDRESS_DETAILS);
        createAddressRequest.setPostalCode(ADDRESS_POSTAL_CODE);
        createAddressRequest.setCityName(ADDRESS_CITY_NAME);
        createAddressRequest.setCountryCode(ADDRESS_COUNTRY_CODE);
        createAddressRequest.setCountryName(ADDRESS_COUNTRY_NAME);
        createAddressRequest.setDefaultDelivery(true);
        createAddressRequest.setDefaultPickup(true);
        createAddressRequest.setDefaultBilling(true);

        when(addressService.createCustomerAddress(eq(CUSTOMER_ID), any(CreateAddressRequest.class))).thenAnswer(a -> {
            Address address = new Address();
            address.setId(ADDRESS_ID);
            return address;
        });
    }

    @Test
    public void testCreateCustomerAddress() throws Exception {
        String json = objectMapper.writeValueAsString(createAddressRequest);

        mockMvc.perform(post("/customers/1/addresses")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(ADDRESS_ID))
                .andExpect(status().isOk());
    }

}
