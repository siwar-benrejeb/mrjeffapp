package com.mrjeffapp.notification.listener;

import com.mrjeffapp.event.ProductAuthorizationEvent;
import com.mrjeffapp.event.ProductsAuthorizationsEvent;
import com.mrjeffapp.notification.service.ProductAuthorizationEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.mrjeffapp.notification.listener.ProductAuthorizationListener.QUEUE_PRODUCT_AUTHORIZATION_SAAS;

@Component
@RabbitListener(queues = QUEUE_PRODUCT_AUTHORIZATION_SAAS)
public class ProductAuthorizationListener {
    public static final String QUEUE_PRODUCT_AUTHORIZATION_SAAS = "notification-service/product-authorization-saas.queue";

    private static final Logger LOG = LoggerFactory.getLogger(ProductAuthorizationListener.class);
    private final ProductAuthorizationEmailService productAuthorizationEmailService;

    @Autowired
    public ProductAuthorizationListener(ProductAuthorizationEmailService productAuthorizationEmailService) {
        this.productAuthorizationEmailService = productAuthorizationEmailService;
    }

    @RabbitHandler
    public void receive(ProductsAuthorizationsEvent productAuthorizationEvents) {
        LOG.info("Received: productAuthorizationEvents={}", productAuthorizationEvents);
        List<ProductAuthorizationEvent> productAuthorizationEventList=productAuthorizationEvents.getProductAuthorizationEventList();

        productAuthorizationEventList.forEach(productAuthorizationEvent -> productAuthorizationEmailService.sendProductEmail(productAuthorizationEvent.getWorkOrderId(),productAuthorizationEvent.getOrderId()));
            LOG.info("Processed workOrderEvent: workOrderEvent={}", productAuthorizationEvents);

        LOG.info("mail sent successfully");
    }
}
