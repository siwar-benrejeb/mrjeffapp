package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.OrderCancelledUnpaidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;


//@Component
//@RabbitListener(queues = QUEUE_ORDER_CANCELLED_UNPAID)
public class OrderCancelledUnpaidListener {
    private static final Logger LOG = LoggerFactory.getLogger(OrderCancelledUnpaidListener.class);
    private final OrderCancelledUnpaidService orderCancelledUnpaidService;

    @Autowired
    public OrderCancelledUnpaidListener(OrderCancelledUnpaidService orderCancelledUnpaidService) {
        this.orderCancelledUnpaidService = orderCancelledUnpaidService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        orderCancelledUnpaidService.sendOrderCancelledUnpaidEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
