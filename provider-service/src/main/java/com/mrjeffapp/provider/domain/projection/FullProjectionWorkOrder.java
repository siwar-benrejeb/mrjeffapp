package com.mrjeffapp.provider.domain.projection;

import com.mrjeffapp.provider.domain.WorkOrder;
import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "full", types = { WorkOrder.class })
public interface FullProjectionWorkOrder {

    String getId();

    String getOrderCode();

    String getOrderId();

    Date getPickupDate();

    Date getDeliveryDate();

    String getCustomerId();

    FullProjectionProvider getProvider();

    FullProjectionWorkOrderState getWorkOrderState();

    Collection<FullProjectionItemCount> getItemCounts();

    Collection<FullProjectionWorkOrderNote> getWorkOrderNotes();

    Collection<FullProjectionIncident> getIncidents();

    Collection<FullProjectionProduct> getProducts();

}
