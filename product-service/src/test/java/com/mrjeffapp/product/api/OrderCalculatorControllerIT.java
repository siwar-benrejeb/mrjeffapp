package com.mrjeffapp.product.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.product.api.dto.OrderCalculatorRequest;
import com.mrjeffapp.product.api.dto.ProductRequest;
import com.mrjeffapp.product.client.CouponServiceClient;
import com.mrjeffapp.product.domain.PriceCalculation;
import com.mrjeffapp.product.service.ProductPriceCalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderCalculatorController.class)
public class OrderCalculatorControllerIT {
    private static final String PRODUCT1_CODE = "CODE1";
    private static final int PRODUCT1_QUANTITY = 5;
    private static final String COUPON_ID = "2";
    private static final String COUPON_CODE = "COUPON";
    private static final Double COUPON_AMOUNT = 5D;
    private static final double TOTAL_PRICE = 100D;
    private static final double DISCOUNT = 0D;
    private static final String DISCOUNT_CATEGORY = "FIXED";
    private static final double TOTAL_PRICE_WITHOUT_DISCOUNT = 100D;
    private static final String CUSTOMER_ID = "1";
    private static final String ORDER_ID = "1";

    @MockBean
    private ProductPriceCalculatorService productPriceCalculatorService;

    @MockBean
    private CouponServiceClient couponServiceClient;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testOrderTotalPriceCalculation() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCode(PRODUCT1_CODE);
        productRequest.setQuantity(PRODUCT1_QUANTITY);
        List<ProductRequest> productRequestSet = Arrays.asList(productRequest);

        OrderCalculatorRequest request = new OrderCalculatorRequest();
        request.setCustomerId(CUSTOMER_ID);
        request.setProducts(productRequestSet);

        String json = objectMapper.writeValueAsString(request);

        PriceCalculation priceCalculation = new PriceCalculation();
        priceCalculation.setTotalPrice(TOTAL_PRICE);
        priceCalculation.setTotalPriceWithoutDiscount(TOTAL_PRICE_WITHOUT_DISCOUNT);
        priceCalculation.setDiscount(DISCOUNT);
        priceCalculation.setDiscountCategory(DISCOUNT_CATEGORY);

        given(productPriceCalculatorService.calculatePrice(any(List.class), eq(CUSTOMER_ID))).willReturn(priceCalculation);

        mvc.perform(post("/order/total")
                        .contentType(MediaType.APPLICATION_JSON).content(json))
                        .andExpect(jsonPath("products[0].code").value(PRODUCT1_CODE))
                        .andExpect(jsonPath("products[0].quantity").value(PRODUCT1_QUANTITY))
                        .andExpect(jsonPath("totalPrice").value(TOTAL_PRICE))
                        .andExpect(jsonPath("discount").value(DISCOUNT))
                        .andExpect(jsonPath("discountCategory").value(DISCOUNT_CATEGORY))
                        .andExpect(jsonPath("totalPriceWithoutDiscount").value(TOTAL_PRICE_WITHOUT_DISCOUNT))
                        .andExpect(jsonPath("customerId").value(CUSTOMER_ID))
                        .andExpect(jsonPath("coupon").doesNotExist())
                        .andExpect(jsonPath("couponId").doesNotExist())
                        .andExpect(jsonPath("couponAmount").doesNotExist())
                        .andExpect(jsonPath("couponValid").doesNotExist())
                        .andExpect(status().isOk());

    }

    @Test
    public void testOrderTotalPriceCalculationWithCoupon() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setCode(PRODUCT1_CODE);
        productRequest.setQuantity(PRODUCT1_QUANTITY);
        List<ProductRequest> productRequestSet = Arrays.asList(productRequest);

        OrderCalculatorRequest request = new OrderCalculatorRequest();
        request.setCoupon(COUPON_CODE);
        request.setCustomerId(CUSTOMER_ID);
        request.setProducts(productRequestSet);

        String json = objectMapper.writeValueAsString(request);

        PriceCalculation priceCalculation = new PriceCalculation();
        priceCalculation.setTotalPrice(TOTAL_PRICE);
        priceCalculation.setTotalPriceWithoutDiscount(TOTAL_PRICE_WITHOUT_DISCOUNT);
        priceCalculation.setDiscount(DISCOUNT);
        priceCalculation.setDiscountCategory(DISCOUNT_CATEGORY);
        priceCalculation.setCouponId(COUPON_ID);
        priceCalculation.setCoupon(COUPON_CODE);
        priceCalculation.setCouponAmount(COUPON_AMOUNT);
        priceCalculation.setCouponValid(true);

        given(productPriceCalculatorService.calculateOrderPriceWithCoupon(any(List.class), eq(CUSTOMER_ID), eq(COUPON_CODE))).willReturn(priceCalculation);

        mvc.perform(post("/order/total")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("products[0].code").value(PRODUCT1_CODE))
                .andExpect(jsonPath("products[0].quantity").value(PRODUCT1_QUANTITY))
                .andExpect(jsonPath("totalPrice").value(TOTAL_PRICE))
                .andExpect(jsonPath("discount").value(DISCOUNT))
                .andExpect(jsonPath("discountCategory").value(DISCOUNT_CATEGORY))
                .andExpect(jsonPath("totalPriceWithoutDiscount").value(TOTAL_PRICE_WITHOUT_DISCOUNT))
                .andExpect(jsonPath("customerId").value(CUSTOMER_ID))
                .andExpect(jsonPath("couponId").value(COUPON_ID))
                .andExpect(jsonPath("coupon").value(COUPON_CODE))
                .andExpect(jsonPath("couponAmount").value(COUPON_AMOUNT))
                .andExpect(jsonPath("couponValid").value(true))
                .andExpect(status().isOk());
    }

}
