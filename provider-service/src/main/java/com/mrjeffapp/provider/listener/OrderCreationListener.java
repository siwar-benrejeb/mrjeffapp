package com.mrjeffapp.provider.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.provider.service.WorkOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.provider.listener.OrderCreationListener.QUEUE_ORDER_CREATION;

@Component
@RabbitListener(queues = QUEUE_ORDER_CREATION)
public class OrderCreationListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderCreationListener.class);
    public static final String QUEUE_ORDER_CREATION = "provider-service/order-creation.queue";

    private final WorkOrderService workOrderService;

    @Autowired
    public OrderCreationListener(WorkOrderService workOrderService) {
        this.workOrderService = workOrderService;
    }

    @RabbitHandler
    public void receive(OrderEvent event) {

        LOG.debug("Received order creation event: {} ", event);

        workOrderService.createWorkOrder(event.getOrderId(), event.getOrderCode(), event.getCustomerId(), event.getPostalCodeId(), event.getPickupDate(), event.getDeliveryDate());

        LOG.debug("Finished processing OrderEvent");
    }

}
