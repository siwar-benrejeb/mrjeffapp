package com.mrjeffapp.order.domain.projection;

import com.mrjeffapp.order.config.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
public class ProjectionsIT {
	
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
	public void testOrderStatusFullProjection() throws Exception {
		mockMvc.perform(get("/api/orderStatuses/1?projection=full"))
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("code").exists())
			.andExpect(jsonPath("name").exists())
			.andExpect(jsonPath("description").exists());
	}

	@Test
	public void testChannelFullProjection() throws Exception {
		mockMvc.perform(get("/api/channels/1?projection=full"))
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("code").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists());
	}

	@Test
	public void testOrderTypeFullProjection() throws Exception {
		mockMvc.perform(get("/api/orderTypes/1?projection=full"))
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("code").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists());
	}

    @Test
    public void testOrderFullProjection() throws Exception {
        mockMvc.perform(get("/api/orders/1?projection=full"))
                .andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("postalCodeId").exists())
                .andExpect(jsonPath("orderDate").exists())
                .andExpect(jsonPath("headquarterId").exists())
                .andExpect(jsonPath("providerId").exists())
                .andExpect(jsonPath("couponId").exists())
                .andExpect(jsonPath("note").exists())
                .andExpect(jsonPath("totalPrice").exists())
                .andExpect(jsonPath("customerId").exists())
                .andExpect(jsonPath("visits[0].id").exists())
                .andExpect(jsonPath("productsOrder[0].id").exists())
                .andExpect(jsonPath("orderStatus.id").exists())
                .andExpect(jsonPath("channel.id").exists());
    }

    @Test
    public void testProductOrderFullProjection() throws Exception {
        mockMvc.perform(get("/api/productOrders/1?projection=full"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("productId").exists())
        		.andExpect(jsonPath("quantity").exists());
    }

    @Test
    public void testVisitFullProjection() throws Exception {
        mockMvc.perform(get("/api/visits/1?projection=full"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("date").exists())
                .andExpect(jsonPath("visitTypeCode").exists())
                .andExpect(jsonPath("timeSlotStart").exists())
                .andExpect(jsonPath("timeSlotEnd").exists())
                .andExpect(jsonPath("postalCodeId").exists())
				.andExpect(jsonPath("cityId").exists())
				.andExpect(jsonPath("countryId").exists())
                .andExpect(jsonPath("addressId").exists());
    }

    @Test
    public void testSearchDailyOrderNotDataFound() throws Exception {
        mockMvc.perform(get("/api/orders/search/findDailyOrderByStatus?headquarterId=1&orderStatusId=1&date=2000-01-01&projection=full"))
                .andExpect(jsonPath("page.totalElements").value("0"));
    }

    @Test
    public void testSearchDailyOrder() throws Exception {
        mockMvc.perform(get("/api/orders/search/findDailyOrderByStatus?headquarterId=1&orderStatusId=1&date=2016-12-12&projection=full"))
                .andExpect(jsonPath("page.totalElements").value("1"));
    }
	
}
