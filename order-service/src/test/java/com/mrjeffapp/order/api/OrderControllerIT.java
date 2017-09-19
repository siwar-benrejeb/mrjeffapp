package com.mrjeffapp.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrjeffapp.order.api.dto.OrderStatusChangeRequest;
import com.mrjeffapp.order.config.Constants;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.OrderStatus;
import com.mrjeffapp.order.service.OrderService;
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
public class OrderControllerIT {
    private static final String ORDER_ID_1 = "1";
    private static final String ORDER_ID_2 = "2";

    private static final String USER_ID = "3";
    private static final String STATUS_CODE_PICKUP = "PICKUP";
    private static final String STATUS_CODE_WASHING = "WASHING";

    @MockBean
    private OrderService orderService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysExpect(status().isOk())
                .build();

        objectMapper = new ObjectMapper();

        given(orderService.updateOrderStatus(any(String.class), any(String.class), eq(USER_ID))).willAnswer(answer -> {
            String orderId = answer.getArgumentAt(0, String.class);
            String orderStatusCode = answer.getArgumentAt(1, String.class);

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCode(orderStatusCode);

            Order order = new Order();
            order.setId(orderId);
            order.setOrderStatus(orderStatus);
            order.setOrderDate(new Date());

            return order;
        });
    }

    @Test
    public void testChangeOrderStatusPickup() throws Exception {
        OrderStatusChangeRequest request = new OrderStatusChangeRequest();
        request.setUserId(USER_ID);
        request.setOrderStatusCode(STATUS_CODE_PICKUP);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/orders/1/orderStatus")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(ORDER_ID_1))
                .andExpect(jsonPath("orderStatusCode").value(STATUS_CODE_PICKUP));
    }

    @Test
    public void testChangeOrderStatusWashing() throws Exception {
        OrderStatusChangeRequest request = new OrderStatusChangeRequest();
        request.setUserId(USER_ID);
        request.setOrderStatusCode(STATUS_CODE_WASHING);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(patch("/orders/2/orderStatus")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(jsonPath("id").value(ORDER_ID_2))
                .andExpect(jsonPath("orderStatusCode").value(STATUS_CODE_WASHING));
    }

}
