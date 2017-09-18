package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.OrderEvent;
import com.mrjeffapp.notification.service.ChangingSchedueleFreeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;


//@Component
//@RabbitListener(queues = QUEUE_CHANGING_SCHEDUELE_FREE)
public class ChangingScheduleFreeListener {
    private static final Logger LOG = LoggerFactory.getLogger(ChangingScheduleFreeListener.class);
    private final ChangingSchedueleFreeService changingSchedueleFreeService;

    @Autowired
    public ChangingScheduleFreeListener(ChangingSchedueleFreeService changingSchedueleFreeService) {
        this.changingSchedueleFreeService = changingSchedueleFreeService;
    }

    @RabbitHandler
    public void receive(OrderEvent orderEvent) {
        LOG.info("Received: orderEvent ={}", orderEvent);
        changingSchedueleFreeService.sendChangingScheduleFreeEmail(orderEvent.getOrderId());
        LOG.info("mail sent successfully");
    }
}
