package com.mrjeffapp.customer.projection;

import com.mrjeffapp.customer.client.admin.AdministrativeClient;
import com.mrjeffapp.customer.config.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql("ProjectionsIT.sql")
public class ProjectionIT {



    @MockBean
    private AdministrativeClient administrativeClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    public void testCustomerFullProjection() throws Exception {
        mockMvc.perform(get("/api/customers/1?projection=full"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("lastName").exists())
                .andExpect(jsonPath("email").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("addresses[0].id").exists())
                .andExpect(jsonPath("addresses[1].id").exists());
    }

    @Test
    public void testAddressFullProjection() throws Exception {
        mockMvc.perform(get("/api/addresses/1?projection=full"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("fullName").exists())
                .andExpect(jsonPath("alias").exists())
                .andExpect(jsonPath("address").exists())
                .andExpect(jsonPath("addressNumber").exists())
                .andExpect(jsonPath("addressDetails").exists())
                .andExpect(jsonPath("cityId").exists())
                .andExpect(jsonPath("cityCode").exists())
                .andExpect(jsonPath("cityName").exists())
                .andExpect(jsonPath("countryId").exists())
                .andExpect(jsonPath("countryCode").exists())
                .andExpect(jsonPath("countryName").exists())
                .andExpect(jsonPath("regionId").exists())
                .andExpect(jsonPath("postalCodeId").exists())
                .andExpect(jsonPath("postalCode").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andExpect(jsonPath("defaultBilling").exists())
                .andExpect(jsonPath("defaultPickup").exists())
                .andExpect(jsonPath("defaultDelivery").exists());
    }

}
