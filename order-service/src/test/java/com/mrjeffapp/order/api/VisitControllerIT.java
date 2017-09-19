package com.mrjeffapp.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.order.api.dto.VisitRelocateAddressRequest;
import com.mrjeffapp.order.api.dto.VisitRelocateRequest;
import com.mrjeffapp.order.config.Constants;
import com.mrjeffapp.order.domain.Visit;
import com.mrjeffapp.order.service.VisitService;
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

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(Constants.SPRING_PROFILE_TEST)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisitControllerIT {
    private static final String VISIT_ID = "1";
    private static final String CUSTOMER_ID = "2";
    private static final String TIME_SLOT_CODE = "10:00-11:00";
    private static final String ADDRESS_ID = "3";
    private static final String USER_ID = "4";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private VisitService visitService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysExpect(status().isOk())
                .build();

        objectMapper = new ObjectMapper();

        Visit visit = new Visit();
        visit.setId(VISIT_ID);

        given(visitService.relocateVisit(eq(CUSTOMER_ID), eq(VISIT_ID), any(Date.class), eq(TIME_SLOT_CODE))).willReturn(visit);
        given(visitService.relocateVisit(eq(CUSTOMER_ID), eq(VISIT_ID), any(Date.class), eq(TIME_SLOT_CODE), eq(ADDRESS_ID))).willReturn(visit);
    }

    @Test
    public void testRelocateVisit() throws Exception {
        VisitRelocateRequest request = new VisitRelocateRequest();
        request.setDate(new Date());
        request.setTimeSlotCode(TIME_SLOT_CODE);
        request.setCustomerId(CUSTOMER_ID);
        request.setUserId(USER_ID);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/visits/1/relocate")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(VISIT_ID));
    }

    @Test
    public void testRelocateVisitWithAddress() throws Exception {
        VisitRelocateAddressRequest request = new VisitRelocateAddressRequest();
        request.setDate(new Date());
        request.setTimeSlotCode(TIME_SLOT_CODE);
        request.setCustomerId(CUSTOMER_ID);
        request.setAddressId(ADDRESS_ID);
        request.setUserId(USER_ID);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/visits/1/relocate/address")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(VISIT_ID));
    }

}
