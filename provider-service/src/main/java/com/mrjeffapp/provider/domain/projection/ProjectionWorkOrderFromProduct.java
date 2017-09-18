package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.WorkOrder;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = { WorkOrder.class })
public interface ProjectionWorkOrderFromProduct {

    String getId();

    String getOrderCode();

    String getOrderId();

    String getCustomerId();

}
