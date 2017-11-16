package com.mrjeffapp.order.domain.projection;

import com.mrjeffapp.order.domain.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "confirmation", types = { Order.class })
public interface ConfirmationProjectionOrder {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.orderStatus.code}")
    String getOrderStatusCode();

}
