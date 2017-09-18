package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.OrderCancelledService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.mrjeffapp.notification.config.Constants.Event.QUEUE_ORDER_CANCELLED;


@Component
@RabbitListener(queues = QUEUE_ORDER_CANCELLED)
public class OrderCancelledListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderCancelledListener.class);
    private final OrderCancelledService orderCancelledService;

    @Autowired
    public OrderCancelledListener(OrderCancelledService orderCancelledService) {
        this.orderCancelledService = orderCancelledService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        orderCancelledService.sendOrderCancelledUnpaidEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
