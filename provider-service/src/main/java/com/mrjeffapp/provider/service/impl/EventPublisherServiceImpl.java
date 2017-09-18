package com.mrjeffapp.provider.service.impl;

import com.mrjeffapp.event.ProductsAuthorizationsEvent;
import com.mrjeffapp.event.ProviderAssignmentEvent;
import com.mrjeffapp.provider.service.EventPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventPublisherServiceImpl implements EventPublisherService {
    public static final String SERVICE_EXCHANGE = "provider-service/amq.topic";
    public static final String PROVIDER_ROUTING_ASSIGN_PROVIDER = "provider.order.assign";
    public static final String PROVIDER_ROUTING_PRODUCT_AUTHORIZATION = "provider.authorization.product";

    private static final Logger LOG = LoggerFactory.getLogger(EventPublisherServiceImpl.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventPublisherServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public ProviderAssignmentEvent sendProviderOrderAssignmentEvent(String orderId, String providerId) {
        ProviderAssignmentEvent event = new ProviderAssignmentEvent();
        event.setOrderId(orderId);
        event.setProviderId(providerId);
        event.setEventDate(new Date());

        LOG.debug("Sending event to {} with routing key {}: {}", SERVICE_EXCHANGE, PROVIDER_ROUTING_ASSIGN_PROVIDER, event);
        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, PROVIDER_ROUTING_ASSIGN_PROVIDER, event);
        LOG.debug("Event sent to {} with routing key {}: {}", SERVICE_EXCHANGE, PROVIDER_ROUTING_ASSIGN_PROVIDER, event);

        return event;
    }

    @Override
    public void sendAddProductEvent(ProductsAuthorizationsEvent productsEvent) {
        LOG.debug("Sending event to {} with routing key {}: {}", SERVICE_EXCHANGE,  PROVIDER_ROUTING_PRODUCT_AUTHORIZATION, productsEvent);
        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, PROVIDER_ROUTING_PRODUCT_AUTHORIZATION, productsEvent);
        LOG.debug("Event sent to {} with routing key {}: {}", SERVICE_EXCHANGE, PROVIDER_ROUTING_PRODUCT_AUTHORIZATION, productsEvent);
    }

}
