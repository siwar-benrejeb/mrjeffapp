package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.CustomerCreatedEvent;
import com.mrjeffapp.notification.service.ResetPasswordEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;


//@Component
//@RabbitListener(queues = Constants.Event.QUEUE_RESET_PASSWORD)
public class ResetPasswordListener {
    private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordListener.class);
    private final ResetPasswordEmailService gettingPasswordEmailService;

    @Autowired
    public ResetPasswordListener(ResetPasswordEmailService gettingPasswordEmailService) {
        this.gettingPasswordEmailService = gettingPasswordEmailService;
    }

    @RabbitHandler
    public void receive(CustomerCreatedEvent customerCreatedEvent) {
        LOG.info("Received: customer ={}", customerCreatedEvent);
        gettingPasswordEmailService.sendPasswordMail(customerCreatedEvent.getCustomerId());
        LOG.info("mail sent successfully");
    }
}
