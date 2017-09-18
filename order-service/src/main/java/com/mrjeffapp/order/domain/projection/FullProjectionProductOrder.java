package com.mrjeffapp.order.domain.projection;

import com.mrjeffapp.order.domain.ProductOrder;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "full", types = { ProductOrder.class })
public interface FullProjectionProductOrder {

    String getId();

    String getProductId();

    Integer getQuantity();

}
