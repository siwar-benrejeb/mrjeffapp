package com.mrjeffapp.order.domain.projection;

import com.mrjeffapp.order.domain.OrderType;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { OrderType.class })
public interface FullProjectionOrderType {

    String getId();

    String getCode();

    String getName();

    String getDescription();

}
