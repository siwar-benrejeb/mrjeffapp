package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.order.exception.InvalidOrderException;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.OrderStatus;
import com.mrjeffapp.order.repository.OrderRepository;
import com.mrjeffapp.order.service.EventPublisherService;
import com.mrjeffapp.order.service.OrderBuilder;
import com.mrjeffapp.order.service.OrderStatusService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.mrjeffapp.order.config.Constants.ORDER_STATUS_CREATED;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {
    private static final String ORDER_ID_1 = "1";
    private static final String ORDER_ID_2 = "2";

    private static final String USER_ID = "3";
    private static final String STATUS_CODE_NEXT = "WASHING";

    @Mock
    private OrderBuilder orderBuilder;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStatusService orderStatusService;

    @Mock
    private EventPublisherService eventPublisherService;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private Order order;

    private OrderStatus nextOrderStatus;

    @Before
    public void setUp() {
        nextOrderStatus = new OrderStatus();
        nextOrderStatus.setCode(STATUS_CODE_NEXT);

        order = new Order();
        order.setId(ORDER_ID_1);

        when(orderRepository.save(order)).thenAnswer(answer -> {
            Order order = answer.getArgumentAt(0, Order.class);
            return order;
        });

    }

    @Test(expected = InvalidOrderException.class)
    public void testUpdateOrderStatusOrderNotFound() {
        when(orderRepository.findOne(ORDER_ID_1)).thenReturn(null);

        orderServiceImpl.updateOrderStatus(ORDER_ID_1, STATUS_CODE_NEXT, USER_ID);
    }

    @Test(expected = InvalidOrderException.class)
    public void testInvalidUpdateOrderStatusCREATED() {
        nextOrderStatus.setCode(ORDER_STATUS_CREATED);

        orderServiceImpl.updateOrderStatus(ORDER_ID_1, STATUS_CODE_NEXT, USER_ID);
    }

    @Test
    public void testUpdateOrderStatus() {
        when(orderRepository.findOne(ORDER_ID_1)).thenReturn(order);
        when(orderStatusService.findOrderStatus(STATUS_CODE_NEXT)).thenReturn(nextOrderStatus);

        Order order = orderServiceImpl.updateOrderStatus(ORDER_ID_1, STATUS_CODE_NEXT, USER_ID);

        assertThat("Order status should be changed", order.getOrderStatus().getCode(), is(STATUS_CODE_NEXT));

        verify(orderRepository).save(order);
        verify(eventPublisherService).sendOrderEventByOrderStatusCode(order);
    }

}
