package com.mrjeffapp.order.domain.projection;

import com.mrjeffapp.order.domain.OrderStatus;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { OrderStatus.class })
public interface FullProjectionOrderStatus {

    String getId();

    String getCode();

    String getName();

    String getDescription();
}
