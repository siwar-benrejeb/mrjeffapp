package com.mrjeffapp.order.api;

import com.mrjeffapp.order.api.dto.OrderCreateRequest;
import com.mrjeffapp.order.api.dto.OrderStatusChangeRequest;
import com.mrjeffapp.order.domain.Order;
import com.mrjeffapp.order.domain.projection.ConfirmationProjectionOrder;
import com.mrjeffapp.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    private final ProjectionFactory projectionFactory;

    @Autowired
    public OrderController(OrderService orderService, ProjectionFactory projectionFactory) {
        this.orderService = orderService;
        this.projectionFactory = projectionFactory;
    }

    @PostMapping("/orders")
    public ConfirmationProjectionOrder creation(@Valid @RequestBody OrderCreateRequest request) {
        LOG.debug("Creation order request: {}", request);

        Order order = orderService.createOrder(request);

        ConfirmationProjectionOrder confirmationProjectionOrder = projectionFactory.createProjection(ConfirmationProjectionOrder.class, order);
        return confirmationProjectionOrder;
    }

    @PatchMapping("/orders/{id}/orderStatus")
    public ConfirmationProjectionOrder statusChange(@PathVariable("id") String orderId, @Valid @RequestBody OrderStatusChangeRequest request) {
        LOG.debug("Order status change request: {}", request);

        Order order = orderService.updateOrderStatus(orderId, request.getOrderStatusCode(), request.getUserId());

        ConfirmationProjectionOrder confirmationProjectionOrder = projectionFactory.createProjection(ConfirmationProjectionOrder.class, order);
        return confirmationProjectionOrder;
    }

}
