package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.order.api.dto.OrderCreateRequest;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.OrderStatus;
import com.mrjeffapp.order.exception.InvalidOrderException;
import com.mrjeffapp.order.exception.InvalidOrderStatusException;
import com.mrjeffapp.order.repository.OrderRepository;
import com.mrjeffapp.order.service.EventPublisherService;
import com.mrjeffapp.order.service.OrderBuilder;
import com.mrjeffapp.order.service.OrderService;
import com.mrjeffapp.order.service.OrderStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mrjeffapp.order.config.Constants.ORDER_STATUS_CREATED;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderBuilder orderBuilder;

    private final OrderRepository orderRepository;

    private final OrderStatusService orderStatusService;

    private final EventPublisherService eventPublisherService;

    @Autowired
    public OrderServiceImpl(OrderBuilder orderBuilder, OrderRepository orderRepository, OrderStatusService orderStatusService, EventPublisherService eventPublisherService) {
        this.orderBuilder = orderBuilder;
        this.orderRepository = orderRepository;
        this.orderStatusService = orderStatusService;
        this.eventPublisherService = eventPublisherService;
    }

    @Override
    @Transactional
    public Order createOrder(OrderCreateRequest orderRequest) {
        LOG.debug("Creating order from request: {}", orderRequest);

        Order order = orderBuilder.buildOrder(orderRequest);
        order.setPostalCodeId("fe03b15d-4104-4a32-8ee4-c736a2dffe39");
        order.setCountryId("090efc78-7d02-4a46-909b-f7c3fc635f24");

        LOG.debug("Saving order to database");
        orderRepository.save(order);

//        LOG.debug("Tracking order status: orderId: {}", order.getId());
//        orderStatusService.trackOrderStatus(order);

        LOG.debug("Publishing event order created, orderId: {}", order.getId());
        eventPublisherService.sendOrderEventByOrderStatusCode(order);
        order.getVisits().forEach(visit -> {
            LOG.debug("Publishing event address created, visitId: {}", visit.getId());
            eventPublisherService.sendVisitCreatedEvent(visit);
      });

        return order;
    }

    @Override
    @Transactional
    public Order assignProviderToOrder(String providerId, String orderId) {
        LOG.debug("Assigning provider: {} to order: {}", providerId, orderId);

        Order order = orderRepository.findOne(orderId);

        if(order == null) {
            LOG.error("Trying to assign provider to an invalid order orderId: {}, providerId: {}", orderId, providerId);
            return null;
        }

        order.setProviderId(providerId);
        Order updatedOrder = orderRepository.save(order);

        return updatedOrder;
    }

    @Override
    @Transactional
    public Order updateOrderStatus(String orderId, String orderStatusCode, String userId) {
        LOG.debug("updateOrderStatus with values orderId:{}, orderStatusCode:{}, userId:{}", orderId, orderStatusCode, userId);

        if(ORDER_STATUS_CREATED.equals(orderStatusCode)) {
            throw new InvalidOrderStatusException("Order modify to CREATED status");
        }

        Order order = orderRepository.findOne(orderId);
        if(order == null) {
            String message = "Not found order with id: " + orderId;
            throw new InvalidOrderException(message);
        }

        OrderStatus orderStatus = orderStatusService.findOrderStatus(orderStatusCode);
        order.setOrderStatus(orderStatus);

        LOG.debug("Saving order to database with new status: {}", orderStatus.getCode());
        Order updatedOrder = orderRepository.save(order);

        LOG.info("Order status updated: orderId: {}, userId: {}", orderId, userId);

        LOG.debug("Tracking order status");
        orderStatusService.trackOrderStatus(updatedOrder);

        LOG.debug("Publishing event order status updated");
        eventPublisherService.sendOrderEventByOrderStatusCode(updatedOrder);

        return updatedOrder;
    }

}
