package com.mrjeffapp.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.customer.api.dto.CustomerBusinessDetailsRequest;
import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.config.Constants;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerIUpdateBusinessDetailsIT {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_BUSINESS_ID = "CUSTOMER_BUSINESS_ID";
    private static final String CUSTOMER_BUSINESS_NAME = "CUSTOMER_BUSINESS_NAME";



    @MockBean
    private CustomerService customerService;

    @MockBean
    private AdministrativeClient administrativeClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CustomerBusinessDetailsRequest customerBusinessDetailsRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        objectMapper = new ObjectMapper();

        Customer customer = new Customer();
        given(customerService.updateCustomerBusinessDetails(CUSTOMER_ID, CUSTOMER_BUSINESS_ID, CUSTOMER_BUSINESS_NAME)).willReturn(customer);
    }

    @Test
    public void testFailureWithoutPickupAddress() throws Exception {
        CustomerBusinessDetailsRequest customerBusinessDetailsRequest = new CustomerBusinessDetailsRequest();
        customerBusinessDetailsRequest.setBusinessId(CUSTOMER_BUSINESS_ID);
        customerBusinessDetailsRequest.setBusinessName(CUSTOMER_BUSINESS_NAME);

        String json = objectMapper.writeValueAsString(customerBusinessDetailsRequest);

        mockMvc.perform(patch("/customers/1/customerBusinessDetails")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

    }

}
