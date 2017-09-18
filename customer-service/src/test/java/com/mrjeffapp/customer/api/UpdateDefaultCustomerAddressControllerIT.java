package com.mrjeffapp.customer.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.customer.api.dto.UpdateDefaultCustomerAddressRequest;
import com.mrjeffapp.customer.config.Constants;
import com.mrjeffapp.customer.domain.Address;
import com.mrjeffapp.customer.service.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpdateDefaultCustomerAddressControllerIT {



    @MockBean
    private UpdateDefaultCustomerPickUpAddressService updateDefaultCustomerPickUpAddressService;

    @MockBean
    private UpdateDefaultCustomerDeliveryAddressService updateDefaultCustomerDeliverAddressService;

    @MockBean
    private UpdateDefaultCustomerBillingAddressService updateDefaultCustomerBillingAddressService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private Address anAddress;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        objectMapper = new ObjectMapper();

        anAddress = new Address();
        anAddress.setPostalCode("12345");
        anAddress.setCityCode("VLC");
        anAddress.setCountryCode("ES");

    }

    @Test
    public void testUpdateDefaultPickUp() throws Exception {
        UpdateDefaultCustomerAddressRequest request = new UpdateDefaultCustomerAddressRequest(
                "addresId",
                true
        );

        when(updateDefaultCustomerPickUpAddressService.execute(new UpdateDefaultCustomerPickUpAddressDto(
            "addressId",
                true,
                "customerId"
        ))).thenReturn(anAddress);

        mockMvc.perform(patch("/customers/1/defaultpickupaddress")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateDefaultDelivery() throws Exception {
        UpdateDefaultCustomerAddressRequest request = new UpdateDefaultCustomerAddressRequest(
                "addressId",
                true
        );

        when(updateDefaultCustomerDeliverAddressService.execute(new UpdateDefaultCustomerDeliveryAddressDto(
                "addressId",
                true,
                "customerId"
        ))).thenReturn(anAddress);

        mockMvc.perform(patch("/customers/1/defaultdeliveryaddress")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateDefaultBilling() throws Exception {
        UpdateDefaultCustomerAddressRequest request = new UpdateDefaultCustomerAddressRequest(
                "addressId",
                true
        );

        when(updateDefaultCustomerBillingAddressService.execute(new UpdateDefaultCustomerBillingAddressDto(
                "addressId",
                true,
                "customerId"
        ))).thenReturn(anAddress);

        mockMvc.perform(patch("/customers/1/defaultbillingaddress")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}