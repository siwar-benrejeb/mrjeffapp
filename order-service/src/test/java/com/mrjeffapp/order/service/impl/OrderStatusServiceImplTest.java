package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.order.exception.InvalidOrderStatusException;
import com.mrjeffapp.order.domain.OrderStatus;
import com.mrjeffapp.order.repository.OrderStatusRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderStatusServiceImplTest {
    private static final String ORDER_STATUS_CODE = "ORDER_STATUS_CODE";

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusServiceImpl orderStatusServiceImpl;

    @Test(expected = InvalidOrderStatusException.class)
    public void testNotFoundOrderStatus() {
        when(orderStatusRepository.findByCodeAndActiveTrue(ORDER_STATUS_CODE)).thenReturn(Optional.empty());

        orderStatusServiceImpl.findOrderStatus(ORDER_STATUS_CODE);
    }

    @Test
    public void testFindOrderStatusActiveByCode() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCode(ORDER_STATUS_CODE);

        when(orderStatusRepository.findByCodeAndActiveTrue(ORDER_STATUS_CODE)).thenReturn(Optional.of(orderStatus));

        OrderStatus orderStatusFound = orderStatusServiceImpl.findOrderStatus(ORDER_STATUS_CODE);

        assertThat("OrderStatus should be found", orderStatus.getCode(), is(ORDER_STATUS_CODE));
    }

}
