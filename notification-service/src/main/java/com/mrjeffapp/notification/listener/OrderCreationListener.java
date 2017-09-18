package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.OrderCreatedEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.notification.config.Constants.Event.QUEUE_ORDER_CREATION;

@Component
@RabbitListener(queues = QUEUE_ORDER_CREATION)
public class OrderCreationListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderCreationListener.class);
    private final OrderCreatedEmailService orderCreationEmailService;

    @Autowired
    public OrderCreationListener(OrderCreatedEmailService orderCreationEmailService) {
        this.orderCreationEmailService = orderCreationEmailService;
    }

    @RabbitHandler
    public void receive(OrderEvent event) {
        LOG.info("Received: event={}", event);
        orderCreationEmailService.sendMail(event.getOrderId());
        LOG.info("Processed event: event={}", event);
    }

}
