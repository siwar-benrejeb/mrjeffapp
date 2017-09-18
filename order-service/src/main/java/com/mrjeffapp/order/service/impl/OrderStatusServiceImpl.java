package com.mrjeffapp.order.service.impl;

import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.OrderStatus;
import com.mrjeffapp.order.domain.OrderStatusTracking;
import com.mrjeffapp.order.exception.InvalidOrderStatusException;
import com.mrjeffapp.order.repository.OrderStatusRepository;
import com.mrjeffapp.order.repository.OrderStatusTrackingRepository;
import com.mrjeffapp.order.service.OrderStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderStatusServiceImpl.class);

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusTrackingRepository orderStatusTrackingRepository;

    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository, OrderStatusTrackingRepository orderStatusTrackingRepository) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusTrackingRepository = orderStatusTrackingRepository;
    }

    @Override
    @Transactional
    public void trackOrderStatus(Order order) {
        LOG.debug("Tracking order status from orderId: {}", order.getId());

        OrderStatusTracking orderStatusTracking = new OrderStatusTracking();
        orderStatusTracking.setOrder(order);
        orderStatusTracking.setOrderStatus(order.getOrderStatus());

        orderStatusTrackingRepository.save(orderStatusTracking);
        LOG.debug("Tracked order status with orderId: {}", order.getId());
    }

    @Override
    public OrderStatus findOrderStatus(String orderStatusCode) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findByCodeAndActiveTrue(orderStatusCode);

        OrderStatus order;
        if(orderStatusOptional.isPresent()) {
            order = orderStatusOptional.get();

        } else {
            String message = "Not found order status with code: " + orderStatusCode;
            throw new InvalidOrderStatusException(message);
        }

        return order;
    }

}
