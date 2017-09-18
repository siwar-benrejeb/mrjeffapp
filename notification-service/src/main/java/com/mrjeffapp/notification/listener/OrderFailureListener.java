package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.OrderFailureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.notification.config.Constants.Event.QUEUE_ORDER_FAILURE;

@Component
@RabbitListener(queues = QUEUE_ORDER_FAILURE)
public class OrderFailureListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderFailureListener.class);
    private final OrderFailureService orderFailureService;

    @Autowired
    public OrderFailureListener(OrderFailureService orderFailureService) {
        this.orderFailureService = orderFailureService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        orderFailureService.sendFailedOrderEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
