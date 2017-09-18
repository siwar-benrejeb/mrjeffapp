package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.Product;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;

@Projection(name = "full", types = { Product.class })
public interface FullProjectionProduct {

    String getId();

    String getProductId();

    Integer getQuantity();

    Collection<FullProjectionNotification> getNotifications();

    ProjectionWorkOrderFromProduct getWorkOrder();

}
