package com.mrjeffapp.product.domain.migration;

import com.mrjeffapp.product.config.Constants;
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
	public void testProjectionFullProductTypes() throws Exception {
		mockMvc.perform(get("/api/productTypes/1?projection=full"))
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("code").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists());
	}

	@Test
	public void testProjectionFullItem() throws Exception {
		mockMvc.perform(get("/api/items/1?projection=full"))
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("code").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists());
	}

	@Test
	public void testProjectionFullProducts() throws Exception {
		mockMvc.perform(get("/api/products/1?projection=full"))
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("code").exists())
				.andExpect(jsonPath("name").exists())
				.andExpect(jsonPath("description").exists())
				.andExpect(jsonPath("itemQuotaTracking").exists())
				.andExpect(jsonPath("productType.id").exists())
				.andExpect(jsonPath("productItems[0].id").exists())
				.andExpect(jsonPath("productItems[0].item.id").exists())
				.andExpect(jsonPath("vatRate.id").exists())
                .andExpect(jsonPath("countryId").exists())
				.andExpect(jsonPath("countryCode").exists());
	}

	@Test
	public void testProjectionFullRelatedProducts() throws Exception {
		mockMvc.perform(get("/api/products/1/related?projection=full"))
				.andExpect(jsonPath("_embedded.products[0].id").exists())
				.andExpect(jsonPath("_embedded.products[0].code").exists())
				.andExpect(jsonPath("_embedded.products[0].name").exists())
				.andExpect(jsonPath("_embedded.products[0].description").exists())
				.andExpect(jsonPath("_embedded.products[0].productType.id").exists())
				.andExpect(jsonPath("_embedded.products[0].productItems[0].id").exists())
				.andExpect(jsonPath("_embedded.products[0].productItems[0].item.id").exists())
				.andExpect(jsonPath("_embedded.products[0].vatRate.id").exists())
				.andExpect(jsonPath("_embedded.products[0].countryId").exists());
	}


	@Test
    public void testProjectionFullVATRates() throws Exception {
        mockMvc.perform(get("/api/vATRates/1?projection=full"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("percentage").exists());
    }

	@Test
	public void testProjectionFullProductItems() throws Exception {
		mockMvc.perform(get("/api/productItems/1?projection=full"))
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("quantity").exists());
	}

    @Test
    public void testProjectionDetailProductType() throws Exception {
        mockMvc.perform(get("/api/productTypes/1?projection=detailProductType"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("products[0].id").exists());
    }

    @Test
    public void testProjectionDetailProduct() throws Exception {
        mockMvc.perform(get("/api/products/1?projection=detailProductType"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("code").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists())
                .andExpect(jsonPath("price").exists())
        		.andExpect(jsonPath("priceWithoutVat").exists());
    }
}
