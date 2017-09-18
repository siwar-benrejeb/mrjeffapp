package com.mrjeffapp.order.listener;

import com.mrjeffapp.event.ProviderAssignmentEvent;
import com.mrjeffapp.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.order.config.Constants.Event.QUEUE_PROVIDER_ASSIGNMENT;

@Component
@RabbitListener(queues = QUEUE_PROVIDER_ASSIGNMENT)
public class ProviderAssignmentListener {
    private static final Logger LOG = LoggerFactory.getLogger(ProviderAssignmentListener.class);

    private final OrderService orderService;

    @Autowired
    public ProviderAssignmentListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitHandler
    public void receive(ProviderAssignmentEvent event) {
        LOG.debug("Received event: {}", event);

        orderService.assignProviderToOrder(event.getProviderId(), event.getOrderId());

        LOG.debug("Provider assigned to order successfully");
    }

}
