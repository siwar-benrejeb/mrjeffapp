package com.mrjeffapp.order.domain.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mrjeffapp.order.domain.Order;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.Set;

@Projection(name = "full", types = { Order.class })
public interface FullProjectionOrder {

    String getId();

    String getCode();

    String getCustomerId();

    String getPostalCodeId();

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    Date getOrderDate();

    FullProjectionOrderStatus getOrderStatus();

    FullProjectionChannel getChannel();

    String getHeadquarterId();

    String getProviderId();

    String getCouponId();

    String getBillingAddressId();

    String getNote();

    Double getTotalPrice();

    String getPaymentMethodCode();

    Set<FullProjectionProductOrder> getProductsOrder();

    Set<FullProjectionVisit> getVisits();

}
