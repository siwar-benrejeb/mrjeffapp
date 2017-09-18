package com.mrjeffapp.order.service;

import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.OrderStatus;

public interface OrderStatusService {

    void trackOrderStatus(Order order);

    OrderStatus findOrderStatus(String orderStatusCode);

}
