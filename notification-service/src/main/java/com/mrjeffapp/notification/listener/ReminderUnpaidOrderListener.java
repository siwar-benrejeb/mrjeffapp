package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.ReminderUnpaidOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@RabbitListener(queues = QUEUE_REMINDER_UNPAID_ORDER)
public class ReminderUnpaidOrderListener {
    private static final Logger LOG = LoggerFactory.getLogger(ReminderUnpaidOrderListener.class);
    private final ReminderUnpaidOrderService reminderUnpaidOrderService;

    @Autowired
    public ReminderUnpaidOrderListener(ReminderUnpaidOrderService reminderUnpaidOrderService) {
        this.reminderUnpaidOrderService = reminderUnpaidOrderService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        reminderUnpaidOrderService.sendUnpaidOrderMail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
