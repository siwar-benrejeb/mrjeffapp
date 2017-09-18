package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.OrderPendingEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.notification.config.Constants.Event.QUEUE_ORDER_PENDING;


@Component
@RabbitListener(queues = QUEUE_ORDER_PENDING)
public class OrderPendingListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderPendingListener.class);
    private final OrderPendingEmailService orderPendingEmailService;

    @Autowired
    public OrderPendingListener(OrderPendingEmailService orderPendingEmailService) {
        this.orderPendingEmailService = orderPendingEmailService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        orderPendingEmailService.sendOrderPendingEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
