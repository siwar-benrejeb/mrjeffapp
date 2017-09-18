package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.event.VisitEvent;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.Visit;
import com.mrjeffapp.order.service.EventPublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.mrjeffapp.order.config.Constants.Event.*;
import static com.mrjeffapp.order.config.Constants.VISIT_TYPE_CODE_DELIVERY;
import static com.mrjeffapp.order.config.Constants.VISIT_TYPE_CODE_PICKUP;

@Service
public class EventPublisherServiceImpl implements EventPublisherService {

    private static final Logger LOG = LoggerFactory.getLogger(EventPublisherServiceImpl.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public EventPublisherServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private OrderEvent buildOrderEvent(Order order) {
        OrderEvent event = new OrderEvent();
        event.setOrderId(order.getId());
        event.setCustomerId(order.getCustomerId());
        event.setCouponId(order.getCouponId());
        event.setOrderDate(order.getOrderDate());
        event.setPostalCodeId(order.getPostalCodeId());
        event.setOrderStatusCode(order.getOrderStatus().getCode());
        event.setBillingAddressId(order.getBillingAddressId());

        Optional<Visit> pickupVisitOptional = order.getVisits().stream()
                .filter(v -> VISIT_TYPE_CODE_PICKUP.equals(v.getVisitTypeCode()))
                .findFirst();

        String pickupAddressId = null;
        Date pickupAddressDate = null;
        if(pickupVisitOptional.isPresent()) {
            Visit pickupVisit = pickupVisitOptional.get();
            pickupAddressId = pickupVisit.getAddressId();
            pickupAddressDate = pickupVisit.getDate();
        }

        event.setPickupAddressId(pickupAddressId);
        event.setPickupDate(pickupAddressDate);

        Optional<Visit> deliveryVisitOptional = order.getVisits().stream()
                .filter(v -> VISIT_TYPE_CODE_DELIVERY.equals(v.getVisitTypeCode()))
                .findFirst();

        String deliveryAddressId = null;
        Date deliveryAddressDate = null;
        if(deliveryVisitOptional.isPresent()) {
            Visit deliveryVisit = deliveryVisitOptional.get();
            deliveryAddressId = deliveryVisit.getAddressId();
            deliveryAddressDate = deliveryVisit.getDate();
        }

        event.setDeliveryAddressId(deliveryAddressId);
        event.setDeliveryDate(deliveryAddressDate);

        event.setEventDate(new Date());

        return event;
    }

    @Override
    public OrderEvent sendOrderEventByOrderStatusCode(Order order) {
        OrderEvent event = buildOrderEvent(order);

        LOG.debug("Sending order event: {}", event);

        String orderStatusRoutingKey = ROUTING_ORDER_STATE_BASE + event.getOrderStatusCode().toLowerCase();
        LOG.debug("Sending order event with routing key: {}", orderStatusRoutingKey);

        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, orderStatusRoutingKey, event);

        LOG.info("Order event sent: {}", event);

        return event;
    }

    private VisitEvent buildVisitEvent(Visit visit) {
        Order order = visit.getOrder();

        VisitEvent visitEvent = new VisitEvent();
        visitEvent.setVisitId(visit.getId());
        visitEvent.setOrderId(order.getId());
        visitEvent.setAddressId(visit.getAddressId());
        visitEvent.setCityId(visit.getCityId());
        visitEvent.setCustomerId(order.getCustomerId());
        visitEvent.setDate(visit.getDate());
        visitEvent.setVisitTypeCode(visit.getVisitTypeCode());
        visitEvent.setPostalCodeId(visit.getPostalCodeId());
        visitEvent.setTimeTableTimeSlotId(visit.getTimeTableTimeSlotId());
        visitEvent.setEventDate(new Date());

        return visitEvent;
    }

    @Override
    public VisitEvent sendVisitCreatedEvent(Visit visit) {
        VisitEvent event = buildVisitEvent(visit);

        LOG.debug("Sending visit event: {}", event);

        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, ROUTING_VISIT_STATE_CREATED, event);

        LOG.info("Visit event sent: {}", event);

        return event;
    }

    @Override
    public VisitEvent sendVisitRelocatedEvent(Visit visit) {
        VisitEvent event = buildVisitEvent(visit);

        LOG.debug("Sending visit event: {}", event);

        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, ROUTING_VISIT_STATE_RELOCATED, event);

        LOG.info("Visit event sent: {}", event);

        return event;
    }

}
