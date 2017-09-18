package com.mrjeffapp.order.service;

import com.mrjeffapp.order.api.dto.OrderCreateRequest;
import com.mrjeffapp.order.domain.Order;

public interface OrderService {

    Order createOrder(OrderCreateRequest order);

    Order assignProviderToOrder(String providerId, String orderId);

    Order updateOrderStatus(String orderId, String orderStatusCode, String userId);

}
