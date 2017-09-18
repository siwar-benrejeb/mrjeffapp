package com.mrjeffapp.provider.listener;

import com.mrjeffapp.event.ProductAuthorizationEvent;
import com.mrjeffapp.provider.service.Product.ProductAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mrjeffapp.provider.listener.ProductAuthorizationListener.QUEUE_PRODUCT_AUTHORIZATION;

@Service
@RabbitListener(queues = QUEUE_PRODUCT_AUTHORIZATION)
public class ProductAuthorizationListener {
    public static final String QUEUE_PRODUCT_AUTHORIZATION = "provider-service/product-authorization.queue";

    private static final Logger LOG = LoggerFactory.getLogger(ProductAuthorizationListener.class);

    private ProductAuthorizationService productAuthorizationService;

    @Autowired
    public ProductAuthorizationListener(ProductAuthorizationService productAuthorizationService) {
        this.productAuthorizationService = productAuthorizationService;
    }

    @RabbitHandler
    public void receive(ProductAuthorizationEvent event) {
        LOG.debug("Received product authorization event: {} ", event);

        productAuthorizationService.productAuthorization(
                event.getId(),
                event.getProductId(),
                event.getQuantity(),
                event.getOrderId(),
                event.getUserId()
        );

        LOG.debug("Finished processing Product authorization Event");

    }
}
