package com.mrjeffapp.order.service;

import com.mrjeffapp.order.api.dto.OrderCreateRequest;
import com.mrjeffapp.order.domain.Order;

public interface OrderBuilder {

    Order buildOrder(OrderCreateRequest orderRequest);

}
