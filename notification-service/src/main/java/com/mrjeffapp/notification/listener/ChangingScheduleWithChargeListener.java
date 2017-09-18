package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.ChangingScheduleWithChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;

//@Component
//@RabbitListener(queues = QUEUE_CHANGING_SCHEDULE_WITH_CHARGE)
public class ChangingScheduleWithChargeListener {
    private static final Logger LOG = LoggerFactory.getLogger(ChangingScheduleWithChargeListener.class);

    public final ChangingScheduleWithChargeService changingScheduleWithChargeService;

    @Autowired
    public ChangingScheduleWithChargeListener(ChangingScheduleWithChargeService changingScheduleWithChargeService) {
        this.changingScheduleWithChargeService = changingScheduleWithChargeService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        changingScheduleWithChargeService.sendChangingScheduleWithChargeEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}


