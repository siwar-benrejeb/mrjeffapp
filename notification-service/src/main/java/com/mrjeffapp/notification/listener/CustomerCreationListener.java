package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.CustomerCreatedEvent;
import com.mrjeffapp.notification.config.Constants;
import com.mrjeffapp.notification.service.CustomerCreatedEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = Constants.Event.OUEUE_CUSTOMER_CREATION)
public class CustomerCreationListener {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerCreationListener.class);
    private final CustomerCreatedEmailService customerCreatedEmailService;

    @Autowired
    public CustomerCreationListener(CustomerCreatedEmailService customerCreatedEmailService) {
        this.customerCreatedEmailService = customerCreatedEmailService;
    }

    @RabbitHandler
    public void receive(CustomerCreatedEvent customerCreatedEvent) {
        LOG.info("Received: customer created={}", customerCreatedEvent);
        customerCreatedEmailService.sendCustomerMail(customerCreatedEvent.getCustomerId());
        LOG.info("mail sent successfully");
    }
}
