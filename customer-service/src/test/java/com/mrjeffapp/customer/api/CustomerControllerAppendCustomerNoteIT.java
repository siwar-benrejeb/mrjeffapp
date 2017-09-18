package com.mrjeffapp.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.customer.api.dto.AppendCustomerNoteRequest;
import com.mrjeffapp.customer.config.Constants;
import com.mrjeffapp.customer.domain.Customer;
import com.mrjeffapp.customer.domain.CustomerType;
import com.mrjeffapp.customer.service.AppendCustomerNoteDto;
import com.mrjeffapp.customer.service.AppendCustomerNoteService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerAppendCustomerNoteIT {

    @MockBean
    private AppendCustomerNoteService appendCustomerNoteService;



    @Autowired
    private WebApplicationContext webApplicationContext;

    private Customer customer;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        objectMapper = new ObjectMapper();

        customer = new Customer(
                "email",
                "password",
                "name",
                "lastname",
                "phoneNumber",
                new CustomerType(
                        "code",
                        "name",
                        "description",
                        true
                )
        );

    }

    @Test
    public void testAppendCustomerNote() throws Exception {
        AppendCustomerNoteRequest request = new AppendCustomerNoteRequest(
                "userId",
                "note"
        );

        when(appendCustomerNoteService.execute(new AppendCustomerNoteDto(
                "1",
                "note",
                "userId"
        ))).thenReturn(customer);

        mockMvc.perform(post("/customers/1/appendNote")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }
}
