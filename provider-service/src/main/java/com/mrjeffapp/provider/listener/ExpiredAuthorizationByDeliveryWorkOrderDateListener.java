package com.mrjeffapp.provider.listener;

import com.mrjeffapp.event.ExpiredAuthorizationEvent;
import com.mrjeffapp.provider.service.Authorization.ToExpiredAuthorizationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpiredAuthorizationByDeliveryWorkOrderDateListener {

    private static final Logger LOG = LoggerFactory.getLogger(ExpiredAuthorizationByDeliveryWorkOrderDateListener.class);

    private ToExpiredAuthorizationServiceImpl toExpiredAuthorizationServiceImpl;

    @Autowired
    public ExpiredAuthorizationByDeliveryWorkOrderDateListener(ToExpiredAuthorizationServiceImpl toExpiredAuthorizationServiceImpl) {
        this.toExpiredAuthorizationServiceImpl = toExpiredAuthorizationServiceImpl;
    }

    @RabbitHandler
    public void receive(ExpiredAuthorizationEvent event) {
        LOG.debug("Received product authorization event: {} ", event);

        toExpiredAuthorizationServiceImpl.toExpiredAuthorizationByDeliveryDate(event.getTimeSlotStart(), event.getCountryCode(), "root");

        LOG.debug("Finished processing Product authorization Event");

    }
}
