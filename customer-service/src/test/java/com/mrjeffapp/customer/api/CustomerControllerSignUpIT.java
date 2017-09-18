package com.mrjeffapp.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.customer.api.dto.CustomerSignUpRequest;
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
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerSignUpIT {
    private static final String CUSTOMER_ID = "1";
    private static final String CUSTOMER_EMAIL = "CUSTOMER_EMAIL@mrjeffapp.com";
    private static final String CUSTOMER_NAME= "CUSTOMER_NAME";
    private static final String CUSTOMER_LAST_NAME= "CUSTOMER_LAST_NAME";
    private static final String CUSTOMER_PHONE = "CUSTOMER_PHONE";
    private static final String CUSTOMER_PASSWORD = "CUSTOMER_PASSWORD";
    private static final String CUSTOMER_PASSWORD_WEAK = "1";



    @MockBean
    private CustomerService customerService;

    @MockBean
    private AdministrativeClient administrativeClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private CustomerSignUpRequest customerRequest;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        objectMapper = new ObjectMapper();

        customerRequest = new CustomerSignUpRequest();
        customerRequest.setEmail(CUSTOMER_EMAIL);
        customerRequest.setName(CUSTOMER_NAME);
        customerRequest.setLastName(CUSTOMER_LAST_NAME);
        customerRequest.setPassword(CUSTOMER_PASSWORD);
        customerRequest.setPhoneNumber(CUSTOMER_PHONE);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        given(customerService.signUpCustomer(any(CustomerSignUpRequest.class))).willReturn(customer);
    }


    @Test
    public void testFailureWeakPassword() throws Exception {
        customerRequest.setPassword("123");

        String json = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers/signup")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void testSignUpCustomer() throws Exception {
        String json = objectMapper.writeValueAsString(customerRequest);

        mockMvc.perform(post("/customers/signup")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(CUSTOMER_PASSWORD_WEAK))
                .andExpect(status().isOk());
    }

}
