package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.OrderCondominioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@RabbitListener(queues = QUEUE_ORDER_CONDOMINIO)
public class OrderCondominioListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderCondominioListener.class);
    private final OrderCondominioService orderCondominioService;

    @Autowired
    public OrderCondominioListener(OrderCondominioService orderCondominioService) {
        this.orderCondominioService = orderCondominioService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        orderCondominioService.sendOrderCondominioEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
