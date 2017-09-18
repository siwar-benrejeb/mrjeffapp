package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.ReminderUnpaidOrder30MinutesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@RabbitListener(queues = QUEUE_REMINDER_UNPAID_ORDER_30MINUTES)
public class RemminderUnpaidOder30MinutesListener {
    private static final Logger LOG = LoggerFactory.getLogger(RemminderUnpaidOder30MinutesListener.class);
    private final ReminderUnpaidOrder30MinutesService reminderUnpaidOrderService;

    @Autowired
    public RemminderUnpaidOder30MinutesListener(ReminderUnpaidOrder30MinutesService reminderUnpaidOrderService) {
        this.reminderUnpaidOrderService = reminderUnpaidOrderService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        reminderUnpaidOrderService.sendReminderUnpaidOrdderEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
